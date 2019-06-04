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

    }
?>
