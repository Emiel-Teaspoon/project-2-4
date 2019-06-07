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

        function followUser($user_id, $follower_id)
        {
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

        function unfollowUser($user_id, $follower_id)
        {
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
    }
?>
