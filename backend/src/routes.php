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
    
    $app->get('/login/{username}/{password}', function (Request $request, Response $response, $args) {
        $username = $args['username'];
        $password = $args['password'];
        $api = new EventMapAPI();
        $result = $api->login($this->db, $username, $password);
        return $response->withJson($result);
     });

     $app->get('/getEvents/{limit}/{userid}', function ($request, $response) {
        $limit = $args['limit'];
        $userid = $args['userid'];
        $api = new EventMapAPI();
        $result = $api->getEvents($this->db, $limit, $userid);
        return $response->withJson($result);
    });

    $app->get('/followUser/{usertofollow}/{userid}', function (Request $request, Response $response, $args) {
        $usertofollow = $args['usertofollow'];
        $userid = $args['userid'];
        $api = new EventMapAPI();
        $result = $api->followUser($this->db, $usertofollow, $userid);
        return $response->withJson($result);
     });
};
