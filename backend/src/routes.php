<?php

use Slim\App;
use Slim\Http\Request;
use Slim\Http\Response;

require __DIR__ . '/../lib/api.class.php';

return function (App $app) {
    $container = $app->getContainer();

    $app->get('/version', function ($request, $response) {
        $api = new EventMapAPI();
        $result = $api->version();
        return $response->withJson($result);
    });

    $app->post('/user', function (Request $request, Response $response) {
        $data = $request->getParsedBody();
        $username = $data['username'];
        $password = $data['password'];
        $email = $data['email'];

        $api = new EventMapAPI();
        $result = $api->registerUser($this->db, $username, $password, $email);
        return $response->withJSON($result);
    });

    $app->put('/user/password', function (Request $request, Response $response) {
	      $data = $request->getParsedBody();
        $username = $data['username'];
        $oldPassword = $data['oldPassword'];
        $newPassword = $data['newPassword'];

        $api = new EventMapAPI();
        $result = $api->changePassword($this->db, $username, $oldPassword, $newPassword);
        return $response->withJSON($result);
    });

    $app->post('/login', function (Request $request, Response $response) {
	$data = $request->getParsedBody();
        $username = $data['username'];
        $password = $data['password'];
        $settings = $this->get('settings');
        $jwt = $settings['jwt']['secret'];
        $api = new EventMapAPI();
        $result = $api->login($this->db, $jwt, $username, $password);
        return $response->withJson($result);
    });

    $app->get('/findUserByUsername/{username}/{userid}', function (Request $request, Response $response, $args) {
        $username = $args['username'];
        $userid = $args['userid'];

        $api = new EventMapAPI();
        $result = $api->findUserByUsername($this->db, $username, $userid);
        return $response->withJson($result);
    });

    $app->get('/findUserByUserID/{userid}', function (Request $request, Response $response, $args) {
        $userid = $args['userid'];

        $api = new EventMapAPI();
        $result = $api->getUserByUserID($this->db, $userid);
        return $response->withJson($result);
    });

    $app->post('/addEvent', function (Request $request, Response $response) {
	$data = $request->getParsedBody();
        $title = $data['title'];
        $description = $data['description'];
        $img = $data['img'];
        $latd = $data['latd'];
        $lotd = $data['lotd'];
        $attendees = $data['attendees'];
        $eventStartDT = $data['eventStartDT'];
        $eventEndDT = $data['eventEndDT'];
        $owner = $data['owner'];

        $api = new EventMapAPI();
        $result = $api->addEvent($this->db, $title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT, $owner);
        return $response->withJSON($result);
    });

    $app->put('/event', function (Request $request, Response $response) {
	$data = $request->getParsedBody();
        $event_ID = $data['event_ID'];
        $title = $data['title'];
        $desc = $data['desc'];
        $img = $data['img'];
        $latd = $data['latd'];
        $lotd = $data['lotd'];
        $attendees = $data['attendees'];
        $eventStartDT = $data['eventStartDT'];
        $eventEndDT = $data['eventEndDT'];

        $api = new EventMapAPI();
        $result = $api->updateEvent($this->db, $event_ID, $title, $desc, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT);
        return $response->withJSON($result);
    });

    $app->get('/EventsByUserID/{user_id}', function ($request, $response, $args) {
        $user_id = $args['user_id'];

        $api = new EventMapAPI();
        $result = $api->getEventsByUserID($this->db, $user_id);
        return $response->withJson($result);
    });

    $app->get('/EventsByEventID/{event_id}', function ($request, $response, $args) {
        $event_id = $args['event_id'];

        $api = new EventMapAPI();
        $result = $api->getEventsByEventID($this->db, $event_id);
        return $response->withJson($result);
    });

    $app->get('/EventsByUsername/{username}', function ($request, $response, $args) {
        $username = $args['username'];

        $api = new EventMapAPI();
        $result = $api->getEventsByUsername($this->db, $username);
        return $response->withJson($result);
    });

    $app->get('/Events/{limit}', function ($request, $response, $args) {
        $limit = $args['limit'];

        $api = new EventMapAPI();
        $result = $api->getEvents($this->db, $limit);
        return $response->withJson($result);
    });

    $app->get('/FollowerEvents/{user_id}', function (Request $request, Response $response, $args) {
        $user_id = $args['user_id'];

        $api = new EventMapAPI();
        $result = $api->getFollowerEvents($this->db, $user_id);
        return $response->withJSON($result);
    });

    $app->delete('/event', function (Request $request, Response $response) {
	$data = $request->getParsedBody();
        $event_ID = $data['event_ID'];

        $api = new EventMapAPI();
        $result = $api->removeEvent($this->db, $event_ID);
        return $response->withJSON($result);
    });

    $app->post('/follow', function (Request $request, Response $response) {
	$data = $request->getParsedBody();
        $user_id = $data['user_id'];
        $follower_id = $data['follower_id'];

        $api = new EventMapAPI();
        $result = $api->followUser($this->db, $user_id, $follower_id);
        return $response->withJson($result);
    });

    $app->delete('/unfollowUser', function (Request $request, Response $response) {
	$data = $request->getParsedBody();
        $user_id = $data['user_id'];
        $follower_id = $data['follower_id'];

        $api = new EventMapAPI();
        $result = $api->unfollowUser($this->db, $user_id, $follower_id);
        return $response->withJson($result);
    });

    $app->delete('/unfollowUser/{user_id}/{follower_id}', function (Request $request, Response $response) {
        $data = $request->getParsedBody();
            $user_id = $args['user_id'];
            $follower_id = $args['follower_id'];

            $api = new EventMapAPI();
            $result = $api->unfollowUser($this->db, $user_id, $follower_id);
            return $response->withJson($result);
        });

    $app->get('/getFollowers/{user_id}', function (Request $request, Response $response, $args) {
        $user_id = $args['user_id'];

        $api = new EventMapAPI();
        $result = $api->getFollowers($this->db, $user_id);
        return $response->withJSON($result);
    });

    $app->get('/getFollowed/{user_id}', function (Request $request, Response $response, $args) {
        $user_id = $args['user_id'];

        $api = new EventMapAPI();
        $result = $api->getFollowed($this->db, $user_id);
        return $response->withJSON($result);
    });

};
