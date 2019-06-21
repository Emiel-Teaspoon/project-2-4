<?php
    header("Content-Type: application/json");

    class FollowDB
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

        function followUser($user_id, $follower_id) {
            $sql = "INSERT INTO followers VALUES (:user_id, :follower_id)";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':user_id', $user_id);
            $stmt->bindValue(':follower_id', $follower_id);

            $result = $stmt->execute();
            
            if($result) {
                return array('Code' => 200, 'Message' => 'Success');
            }
            return array('Code' => 403, 'Message' => 'Error');
        }

        function unfollowUser($user_id, $follower_id) {
            $sql = "DELETE FROM followers WHERE user = :user_id AND follower = :follower_id";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':user_id', $user_id);
            $stmt->bindValue(':follower_id', $follower_id);

            $result = $stmt->execute();
            
            if($result) {
                return array('Code' => 200, 'Message' => 'Success');
            }
            return array('Code' => 403, 'Message' => 'Error');
        }

        function getFollowers($user_id) {
            $sql = "SELECT follower FROM followers WHERE user = :user_id";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':user_id', $user_id);

            $result = $stmt->execute();

            while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                $followers[] = array (
                    'follower' => $fetch->follower
                );
            }

            foreach ($followers as $follower) {
                foreach ($follower as $key => $value) {
                    $sql = "SELECT username, email FROM users WHERE user_id = :follower;";
                    $stmt = $this->conn->prepare($sql);

                    $stmt->bindValue('follower', $value);

                    $result = $stmt->execute();

                    while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                        $results[] = array(
                            'user_id' => $fetch->user_id,
                            'username' => $fetch->username,
                            'email' => $fetch->email
                        );
                    }
                }
            }
            return array ('Code' => 200, 'result' => $results); 
        }

        function getFollowed($user_id) {
            $sql = "SELECT user FROM followers WHERE follower = :user_id";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':user_id', $user_id);

            $result = $stmt->execute();

            while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                $users[] = array (
                    'user' => $fetch->user
                );
            }

            foreach ($users as $user) {
                foreach ($user as $key => $value) {
                    $sql = "SELECT username, email FROM users WHERE user_id = :user;";
                    $stmt = $this->conn->prepare($sql);

                    $stmt->bindValue('user', $value);

                    $result = $stmt->execute();

                    while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                        $results[] = array(
                            'username' => $fetch->username,
                            'email' => $fetch->email
                        );
                    }
                }
            }
            return array ('Code' => 200, 'result' => $results); 
        }
    }
?>
