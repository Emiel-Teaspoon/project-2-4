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

        function updateEvent($event_ID, $title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT) {
            $sql = "UPDATE events 
                    SET title = :title, description = :description, image=:img, latitude=:latd, longitude=:lotd, 
                    attendees=:attendees, event_start_datetime=:eventStartDT, event_end_datetime=:eventEndDT
                    WHERE event_ID = :event_ID";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':event_ID', $event_ID);
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
        
        function getEventsByUser($limit, $user_ID)
        {
            $sql = "SELECT event_id, title, description, image, latitude, longitude, attendees, event_start_datetime, event_end_datetime, event_owner, creation_datetime
                    FROM events WHERE event_owner = :user_ID LIMIT :limit";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':user_ID', $user_ID, PDO::PARAM_INT);
            $stmt->bindValue(':limit', $limit, PDO::PARAM_INT);            

            $res = $stmt->execute();
            
            while ($result = $stmt->fetch(PDO::FETCH_OBJ)) {
                $results[] = array(
                    'event_ID' => $result->event_id,
                    'title' => $result->title,
                    'description' => $result->description,
                    'image' => $result->image,
                    'latitude' => $result->latitude,
                    'longitude' => $result->longitude,
                    'attendees' => $result->attendees,
                    'eventStartDT' => $result->event_start_datetime,
                    'eventEndDT' => $result->event_end_datetime,
                    'event_owner' => $result->event_owner,
                    'creation_datetime' => $result->creation_datetime
                );
            }


            if($res) {
                return array('Code' => 200, 'Message' => 'Success', 'result' => $results);
            }
            return array('Code' => 403, 'Message' => 'Error');
        }

        function removeEvent($event_ID) {
            $sql = "DELETE FROM events WHERE event_id = :event_ID";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':event_ID', $event_ID);

            $result = $stmt->execute();

            if ($result === true) {
                return array('Code' => 200);
            }
        }

    }
?>
