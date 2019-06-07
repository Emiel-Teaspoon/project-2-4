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

     $app->get('/getEvents/{limit}/{userid}', function ($request, $response, $args) {
        $limit = $args['limit'];
        $userid = $args['userid'];
        $api = new EventMapAPI();
        $result = $api->getEvents($this->db, $limit, $userid);
        return $response->withJson($result);
    });

    $app->get('/removeEvent/{event_ID}', function (Request $request, Response $response, $args) {
        $event_ID = $args['event_ID'];

        $api = new EventMapAPI();
        $result = $api->removeEvent($this->db, $event_ID);
        return $response->withJSON($result);
    });

    $app->get('/followUser/{usertofollow}/{userid}', function (Request $request, Response $response, $args) {
        $usertofollow = $args['usertofollow'];
        $userid = $args['userid'];
        $api = new EventMapAPI();
        $result = $api->followUser($this->db, $usertofollow, $userid);
        return $response->withJson($result);
     });


};
