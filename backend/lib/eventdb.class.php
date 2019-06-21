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
        
        function getEventsByUserID($user_id) {
            $sql = "SELECT event_id, title, description, image, latitude, longitude, attendees, event_start_datetime, event_end_datetime, event_owner, creation_datetime
                    FROM events WHERE event_owner = :user_id";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':user_id', $user_id, PDO::PARAM_INT);           

            $result = $stmt->execute();
            
            while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                $results[] = array(
                    'event_ID' => $fetch->event_id,
                    'title' => $fetch->title,
                    'description' => $fetch->description,
                    'image' => $fetch->image,
                    'latitude' => $fetch->latitude,
                    'longitude' => $fetch->longitude,
                    'attendees' => $fetch->attendees,
                    'eventStartDT' => $fetch->event_start_datetime,
                    'eventEndDT' => $fetch->event_end_datetime,
                    'event_owner' => $fetch->event_owner,
                    'creation_datetime' => $fetch->creation_datetime
                );
            }

            if($result) {
                return array('Code' => 200, 'Message' => 'Success', 'result' => $results);
            }
            return array('Code' => 403, 'Message' => 'Error');
        }

        function getEventsByUsername($username) {
            $sql = "select * from events where event_owner = (select user_id from users where username = :username)";
            $stmt = $this->conn->prepare($sql);

            $stmt->bindValue(':username', $username);           

            $result = $stmt->execute();
            
            while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                $results[] = array(
                    'event_ID' => $fetch->event_id,
                    'title' => $fetch->title,
                    'description' => $fetch->description,
                    'image' => $fetch->image,
                    'latitude' => $fetch->latitude,
                    'longitude' => $fetch->longitude,
                    'attendees' => $fetch->attendees,
                    'eventStartDT' => $fetch->event_start_datetime,
                    'eventEndDT' => $fetch->event_end_datetime,
                    'event_owner' => $fetch->event_owner,
                    'creation_datetime' => $fetch->creation_datetime
                );
            }

            if($result) {
                return array('Code' => 200, 'Message' => 'Success', 'result' => $results);
            }
            return array('Code' => 403, 'Message' => 'Error');
        }

        function getEvents($limit) {
            $sql = "SELECT event_id, title, description, image, latitude, longitude, attendees, event_start_datetime, event_end_datetime, event_owner, creation_datetime
                    FROM events LIMIT $limit";
            $stmt = $this->conn->prepare($sql);          

            $result = $stmt->execute();
            
            while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                $results[] = array(
                    'event_ID' => $fetch->event_id,
                    'title' => $fetch->title,
                    'description' => $fetch->description,
                    'image' => $fetch->image,
                    'latitude' => $fetch->latitude,
                    'longitude' => $fetch->longitude,
                    'attendees' => $fetch->attendees,
                    'eventStartDT' => $fetch->event_start_datetime,
                    'eventEndDT' => $fetch->event_end_datetime,
                    'event_owner' => $fetch->event_owner,
                    'creation_datetime' => $fetch->creation_datetime
                );
            }

            if($result) {
                return array('Code' => 200, 'Message' => 'Success', 'result' => $results);
            }
            return array('Code' => 403, 'Message' => 'Error');
        }

        function getFollowerEvents($user_id) {
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
                    $sql = "SELECT event_id, title, description, image, latitude, longitude, attendees, event_start_datetime, event_end_datetime, event_owner, creation_datetime
                    FROM events WHERE event_owner = :follower;";
                    $stmt = $this->conn->prepare($sql);

                    $stmt->bindValue('follower', $value);

                    $result = $stmt->execute();

                    while ($fetch = $stmt->fetch(PDO::FETCH_OBJ)) {
                        $results[] = array(
                            'event_ID' => $fetch->event_id,
                            'title' => $fetch->title,
                            'description' => $fetch->description,
                            'image' => $fetch->image,
                            'latitude' => $fetch->latitude,
                            'longitude' => $fetch->longitude,
                            'attendees' => $fetch->attendees,
                            'eventStartDT' => $fetch->event_start_datetime,
                            'eventEndDT' => $fetch->event_end_datetime,
                            'event_owner' => $fetch->event_owner,
                            'creation_datetime' => $fetch->creation_datetime
                        );
                    }

                }
            }
            return array ('Code' => 200, 'result' => $results); 
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
