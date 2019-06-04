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

        function followUser($userToFollow, $userID)
        {
            $sql = "INSERT INTO followers (user, follower) VALUES (:userToFollow, :userID)";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':userToFollow', $userToFollow);
            $stmt->bindValue(':userID', $userID);

            $res = $stmt->execute();
            
            if($res) {
                return array('Code' => 200, 'Message' => 'Success');
            }
            return array('Code' => 403, 'Message' => 'Error');
        }
    }
?>
