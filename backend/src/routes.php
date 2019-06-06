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

     $app->get('/getEvents/{limit}/{userid}', function ($request, $response, $args) {
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

    $app->post('/registerUser/{username}/{password}/{email}', function (Request $request, Response $response, $args) {
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
};
