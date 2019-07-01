<?php
    header("Content-Type: application/json");
    use \Firebase\JWT\JWT;

    class UserDB
    {
        private $conn;

        function __construct($dbo)
        {
           $this->setConnection($dbo);
        }

        public function setConnection($dbo)
        {
            $this->conn = $dbo;
        }

        public function getConnection()
        {
            return $this->conn;
        }

        function closeConnection()
        {
            $this->setConnection(null);
        }

        function login($jwt, $username, $password)
        {
            $sql = "SELECT users.password, users.user_id, users.api_key FROM users WHERE users.username = :username";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':username', $username);

            $stmt->execute();
            $result = $stmt->fetch(PDO::FETCH_OBJ);

            $encryptedPassword = $result->password;

            $key = 'Some key for encoding';
            $string = $encryptedPassword;

            $data = base64_decode($string);
            $iv = substr($data, 0, mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_CBC));

            $decrypted = rtrim(
                mcrypt_decrypt(
                    MCRYPT_RIJNDAEL_128,
                    hash('sha256', $key, true),
                    substr($data, mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_CBC)),
                    MCRYPT_MODE_CBC,
                    $iv
                ),
                "\0"
            );

            $match = $decrypted === $password;
            if($match) {
              $token = JWT::encode(['Username' => $username, 'UserID' => $result->user_id, 'APIKey' => $result->api_key], $jwt, "HS256");
              return array('token' => $token,'Code' => 200, 'Message' => 'Success','Username' => $username, 'UserID' => $result->user_id, 'APIKey' => $result->api_key);
            }
            return array('Code' => 401, 'Message' => 'Wrong password/username');
        }

        function registerUser($username, $password, $email) {
            try {
                $sql = "INSERT INTO users (username, password, email, api_key) VALUES (:username, :password, :email, :api_key)";
                $stmt = $this->conn->prepare($sql);

                $key = 'Some key for encoding';
                $string = $password;

                $iv = mcrypt_create_iv(
                    mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_CBC),
                    MCRYPT_DEV_URANDOM
                );

                $encrypted = base64_encode(
                    $iv .
                    mcrypt_encrypt(
                        MCRYPT_RIJNDAEL_128,
                        hash('sha256', $key, true),
                        $string,
                        MCRYPT_MODE_CBC,
                        $iv
                    )
                );

                $api_key = base64_encode(md5(uniqid(rand(), true)));

                $stmt->bindvalue(':username', $username);
                $stmt->bindvalue(':password', $encrypted);
                $stmt->bindvalue(':email', $email);
                $stmt->bindvalue(':api_key', $api_key);

                $result = $stmt->execute();

                if($result === true) {
                    return array('Code' => 200, 'Message' => 'Success', 'UserID' => $this->conn->lastInsertId(), 'Username' => $username, 'APIKey' => $api_key);
                }
            }
            catch (PDOException $Exception) {
                if((int)$Exception->getCode() === 23000) {
                    return array('Code' => $Exception->getCode(), 'Message' => 'Username already exists');
                }
                else {
                    return array('Code' => 500, 'Message' => 'An unknown error occured');
                }
            }
        }

        function changePassword($username, $oldPassword, $newPassword) {
            $sql = "UPDATE users SET password = :newPassword WHERE username = :username AND password = :oldPassword";
            $stmt = $this->conn->prepare($sql);

            $key = 'Some key for encoding';
            $iv = mcrypt_create_iv(
                mcrypt_get_iv_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_CBC),
                MCRYPT_DEV_URANDOM
            );

            $encrypted = base64_encode(
                $iv .
                mcrypt_encrypt(
                    MCRYPT_RIJNDAEL_128,
                    hash('sha256', $key, true),
                    $newPassword,
                    MCRYPT_MODE_CBC,
                    $iv
                )
            );

            $stmt->bindvalue(':newPassword', $encrypted);
            $stmt->bindvalue(':username', $username);
            $stmt->bindvalue(':oldPassword', $oldPassword);

            $result = $stmt->execute();

            if ($result === true) {
                return array('Code' => 200);
            }
        }

        function findUserByUsername($username, $userid) {
            $sql = "SELECT DISTINCT users.user_id, users.email, users.username, followers.user, followers.follower from users 
                LEFT JOIN followers ON users.user_id = followers.follower
                WHERE users.username LIKE :username
                GROUP BY users.user_id";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindvalue(':username', '%'.$username.'%');

            $Result = $stmt->execute();

            while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                if(!in_array($fetch->user_id, $results['user_id'], true)){
                    $results[] = array(
                        'user_id' => $fetch->user_id,
                        'email' => $fetch->email,
                        'username' => $fetch->username,
                        'isFollowing' => ($fetch->user === $userid && $fetch->follower === $fetch->user_id),
                    );
                }
            }

            if ($Result === true) {
                return array('Code' => 200, 'result' => $results);
            } else {
                return array('Code'=> 403);
            }

        }
    }
?>
