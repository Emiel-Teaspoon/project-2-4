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

    $app->get('/registerUser/{username}/{password}/{email}', function (Request $request, Response $response, $args) {
        $username = $args['username'];
        $password = $args['password'];

        $email = $args['email'];
        $api = new EventMapAPI();
        $result = $api->registerUser($this->db, $username, $password, $email);
        return $response->withJSON($result);
    });

    $app->get('/changePassword/{username}/{oldPassword}/{newPassword}', function (Request $request, Response $response, $args) {
        $username = $args['username'];
        $oldPassword = $args['oldPassword'];
        $newPassword = $args['newPassword'];
        
        $api = new EventMapAPI();
        $result = $api->changePassword($this->db, $username, $oldPassword, $newPassword);
        return $response->withJSON($result);
    });
    
    $app->get('/login/{username}/{password}', function (Request $request, Response $response, $args) {
        $username = $args['username'];
        $password = $args['password'];

        $api = new EventMapAPI();
        $result = $api->login($this->db, $username, $password);
        return $response->withJson($result);
    });

    $app->get('/addEvent/{title}/{description}/{img}/{latd}/{lotd}/{attendees}/{eventStartDT}/{eventEndDT}', function (Request $request, Response $response, $args) {
        $title = $args['title'];
        $description = $args['description'];
        $img = $args['img'];
        $latd = $args['latd'];
        $lotd = $args['lotd'];
        $attendees = $args['attendees'];
        $eventStartDT = $args['eventStartDT'];
        $eventEndDT = $args['eventEndDT'];

        $api = new EventMapAPI();
        $result = $api->addEvent($this->db, $title, $description, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT);
        return $response->withJSON($result);
    });

    $app->get('/updateEvent/{event_ID}/{title}/{desc}/{img}/{latd}/{lotd}/{attendees}/{eventStartDT}/{eventEndDT}', function (Request $request, Response $response, $args) {
        $event_ID = $args['event_ID'];
        $title = $args['title'];
        $desc = $args['desc'];
        $img = $args['img'];
        $latd = $args['latd'];
        $lotd = $args['lotd'];
        $attendees = $args['attendees'];
        $eventStartDT = $args['eventStartDT'];
        $eventEndDT = $args['eventEndDT'];

        $api = new EventMapAPI();
        $result = $api->updateEvent($this->db, $event_ID, $title, $desc, $img, $latd, $lotd, $attendees, $eventStartDT, $eventEndDT);
        return $response->withJSON($result);
    });

    $app->get('/getEventsByUser/{limit}/{user_id}', function ($request, $response, $args) {
        $limit = $args['limit'];
        $user_id = $args['user_id'];

        $api = new EventMapAPI();
        $result = $api->getEventsByUser($this->db, $limit, $user_id);
        return $response->withJson($result);
    });

    $app->get('/getEvents/{limit}', function ($request, $response, $args) {
        $limit = $args['limit'];

        $api = new EventMapAPI();
        $result = $api->getEvents($this->db, $limit);
        return $response->withJson($result);
    });

    $app->get('/getFollowerEvents/{user_limit}/{event_limit}/{user_id}', function (Request $request, Response $response, $args) {
        $user_limit = $args['user_limit'];
        $event_limit = $args['event_limit'];
        $user_id = $args['user_id'];

        $api = new EventMapAPI();
        $result = $api->getFollowerEvents($this->db, $user_limit, $event_limit, $user_id);
        return $response->withJSON($result);
    });

    $app->get('/removeEvent/{event_ID}', function (Request $request, Response $response, $args) {
        $event_ID = $args['event_ID'];

        $api = new EventMapAPI();
        $result = $api->removeEvent($this->db, $event_ID);
        return $response->withJSON($result);
    });

    $app->get('/followUser/{user_id}/{follower_id}', function (Request $request, Response $response, $args) {
        $user_id = $args['user_id'];
        $follower_id = $args['follower_id'];
        
        $api = new EventMapAPI();
        $result = $api->followUser($this->db, $user_id, $follower_id);
        return $response->withJson($result);
    });

    $app->get('/unfollowUser/{user_id}/{follower_id}', function (Request $request, Response $response, $args) {
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

};
