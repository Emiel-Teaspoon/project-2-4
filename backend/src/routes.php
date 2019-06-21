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

    $app->post('/user/{username}/{password}/{email}', function (Request $request, Response $response, $args) {
        $username = $args['username'];
        $password = $args['password'];

        $email = $args['email'];
        $api = new EventMapAPI();
        $result = $api->registerUser($this->db, $username, $password, $email);
        return $response->withJSON($result);
    });

    $app->put('/password/{username}/{oldPassword}/{newPassword}', function (Request $request, Response $response, $args) {
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

    $app->get('/findUserByUsername/{username}', function (Request $request, Response $response, $args) {
        $username = $args['username'];

        $api = new EventMapAPI();
        $result = $api->findUserByUsername($this->db, $username);
        return $response->withJson($result);
    });

    $app->post('/addEvent/{title}/{description}/{img}/{latd}/{lotd}/{attendees}/{eventStartDT}/{eventEndDT}', function (Request $request, Response $response, $args) {
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

    $app->put('/event/{event_ID}/{title}/{desc}/{img}/{latd}/{lotd}/{attendees}/{eventStartDT}/{eventEndDT}', function (Request $request, Response $response, $args) {
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

    $app->get('/EventsByUserID/{user_id}', function ($request, $response, $args) {
        $user_id = $args['user_id'];

        $api = new EventMapAPI();
        $result = $api->getEventsByUserID($this->db, $user_id);
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

    $app->delete('/event/{event_ID}', function (Request $request, Response $response, $args) {
        $event_ID = $args['event_ID'];

        $api = new EventMapAPI();
        $result = $api->removeEvent($this->db, $event_ID);
        return $response->withJSON($result);
    });

    $app->post('/followUser/{user_id}/{follower_id}', function (Request $request, Response $response, $args) {
        $user_id = $args['user_id'];
        $follower_id = $args['follower_id'];
        
        $api = new EventMapAPI();
        $result = $api->followUser($this->db, $user_id, $follower_id);
        return $response->withJson($result);
    });

    $app->delete('/unfollowUser/{user_id}/{follower_id}', function (Request $request, Response $response, $args) {
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
