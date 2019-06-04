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
        function login($dbo, $username, $password) {
            $db = new UserDB($dbo);

            $stmt = $db->login($username, $password, $udid);
            $db->closeConnection();
            return $stmt;
        }

        // Event functions
        function getEvents($dbo, $limit, $userid) {
            $db = new EventDB($dbo);

            $stmt = $db->selectEventsWithLimit($limit, $userid);
            $db->closeConnection();
            return $stmt;
        }

        // Event functions
        function followUser($dbo, $userToFollow, $userID) {
            $db = new FollowDB($dbo);

            $stmt = $db->followUser($userToFollow, $userID);
            $db->closeConnection();
            return $stmt;
        }
    }
?>
