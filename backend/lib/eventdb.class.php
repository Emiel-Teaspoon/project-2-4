<?php
    header("Content-Type: application/json");

    class EventDB
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
        
        function getEvents($limit, $userID)
        {
            $sql = "Select statement";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':userToFollow', $userToFollow);
            $stmt->bindValue(':userID', $userID);

            $res = $stmt->execute();
            $events = array();
            
            if($res) {
                return array('Code' => 200, 'Message' => 'Success', 'result' => $events);
            }
            return array('Code' => 403, 'Message' => 'Error');
        }

    }
?>
