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

        function addEvent($title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT) {
            $sql = "INSERT INTO events VALUES (null, :title, :description, :img, :latd, :lotd, :attendees, :eventStartDT, :eventEndDT, CURRENT_TIMESTAMP)";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':title', $title);
            $stmt->bindValue(':description', $description);
            $stmt->bindValue(':img', $img);
            $stmt->bindValue(':latd', $latd);
            $stmt->bindValue(':lotd', $lotd);
            $stmt->bindValue(':attendees', $attendees);
            $stmt->bindValue(':eventStartDT', $eventStartDT);
            $stmt->bindValue(':eventEndDT', $eventEndDT);

            $result = $stmt->execute();

            if ($result === true) {
                return array('Code' => 200);
            }
        }

        function updateEvent($title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT) {
            $sql = "UPDATE events 
                    SET description = :description, image=:img, latitude=:latd, longitude=:lotd, attendees=:attendees,
                     event_start_datetime=:eventStartDT, event_end_datetime=:eventEndDT
                    WHERE title = :title";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':title', $title);
            $stmt->bindValue(':description', $description);
            $stmt->bindValue(':img', $img);
            $stmt->bindValue(':latd', $latd);
            $stmt->bindValue(':lotd', $lotd);
            $stmt->bindValue(':attendees', $attendees);
            $stmt->bindValue(':eventStartDT', $eventStartDT);
            $stmt->bindValue(':eventEndDT', $eventEndDT);

            $result = $stmt->execute();

            if ($result === true) {
                return array('Code' => 200);
            }
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
