<?php
    require __DIR__ . '/../lib/userdb.class.php';
    require __DIR__ . '/../lib/eventdb.class.php';
    require __DIR__ . '/../lib/followdb.class.php';

    class EventMapAPI
    {

        var $version = array('major' => '0', 'minor' => '1');

        function version() {
            return $this->version;
        }

        // User functions
        function registerUser($dbo, $username, $password, $email) {
            $db = new UserDB($dbo);

            $stmt = $db->registerUser($username, $password, $email);
            $db->closeConnection();
            return $stmt;
        }

        function login($dbo, $username, $password) {
            $db = new UserDB($dbo);

            $stmt = $db->login($username, $password);
            $db->closeConnection();
            return $stmt;
        }

        function changePassword($dbo, $username, $oldPassword, $newPassword) {
            $db = new UserDB($dbo);

            $stmt = $db->changePassword($username, $oldPassword, $newPassword);
            $db->closeConnection();
            return $stmt;
        }

        function findUserByUsername($dbo, $username) {
            $db = new UserDB($dbo);

            $stmt = $db->findUserByUsername($username);
            $db->closeConnection();
            return $stmt;
        }

        // Event functions
        function addEvent($dbo, $title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT) {
            $db = new EventDB($dbo);

            $stmt = $db->addEvent($title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT);
            $db->closeConnection();
            return $stmt;
        }

        function updateEvent($dbo, $event_id, $title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT) {
            $db = new EventDB($dbo);

            $stmt = $db->updateEvent($event_id, $title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT);
            $db->closeConnection();
            return $stmt;
        }
        
        function getEventsByUserID($dbo, $user_id) {
            $db = new EventDB($dbo);

            $stmt = $db->getEventsByUserID($user_id);
            $db->closeConnection();
            return $stmt;
        }

        function getEventsByUsername($dbo, $Username) {
            $db = new EventDB($dbo);

            $stmt = $db->getEventsByUsername($Username);
            $db->closeConnection();
            return $stmt;
        }

        function getEvents($dbo, $limit) {
            $db = new EventDB($dbo);

            $stmt = $db->getEvents($limit);
            $db->closeConnection();
            return $stmt;
        }

        function getFollowerEvents($dbo, $user_id) {
            $db = new EventDB($dbo);

            $stmt = $db->getFollowerEvents($user_id);
            $db->closeConnection();
            return $stmt;
        }

        function removeEvent($dbo, $event_ID) {
            $db = new EventDB($dbo);

            $stmt = $db->removeEvent($event_ID);
            $db->closeConnection();
            return $stmt;
        }

        // Follow functions
        function followUser($dbo, $user_id, $follower_id) {
            $db = new FollowDB($dbo);

            $stmt = $db->followUser($user_id, $follower_id);
            $db->closeConnection();
            return $stmt;
        }

        function unfollowUser($dbo, $user_id, $follower_id) {
            $db = new FollowDB($dbo);

            $stmt = $db->unfollowUser($user_id, $follower_id);
            $db->closeConnection();
            return $stmt;
        }

        function getFollowers($dbo, $user_id) {
            $db = new FollowDB($dbo);

            $stmt = $db->getFollowers($user_id);
            $db->closeConnection();
            return $stmt;
        }

        function getFollowed($dbo, $user_id) {
            $db = new FollowDB($dbo);

            $stmt = $db->getFollowed($user_id);
            $db->closeConnection();
            return $stmt;
        }
    }
?>
