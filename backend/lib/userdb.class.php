<?php
    header("Content-Type: application/json");

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
        
        function login($username, $password)
        {
            $sql = "SELECT user.user_password, user.user_id, user.api_key FROM user WHERE user_username = :username";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':username', $username);

            $stmt->execute();
            $result = $stmt->fetch(PDO::FETCH_OBJ);

            $encryptedPassword = $result->user_password;

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
                return array('Code' => 200, 'Message' => 'Success', 'Username' => $username, 'UserID' => $result->user_id, 'APIKey' => $result->api_key);
            }
            return array('Code' => 401, 'Message' => 'Wrong password/username');
        }

        function registerUser($username, $password, $email) {
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
                // $this->sendVerification($email, $username, $hash);
                return array('Code' => 200, 'Message' => 'Success', 'Username' => $username, 'Email' => $email, 'APIKey' => $api_key);
            }
            
        }

        function changePassword($username, $oldPassword, $newPassword) {
            $sql = "UPDATE users SET password = :newPassword WHERE username = :username AND password = :oldPassword";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindvalue(':newPassword', $newPassword);
            $stmt->bindvalue(':username', $username);
            $stmt->bindvalue(':oldPassword', $oldPassword);

            $result = $stmt->execute();

            if ($result === true) {
                return array('Code' => 200);
            }
        }

        function findUserByUsername($username) {
            $sql = "SELECT email from users WHERE username = :username";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindvalue(':username', $username);

            $Result = $stmt->execute();

            while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                $results[] = array(
                    'email' => $fetch->email
                );
            }

            if ($Result === true) {
                return array('Code' => 200, 'result' => $results);
            } else {
                return array('Code'=> 403);
            }

        }
    }
?>
