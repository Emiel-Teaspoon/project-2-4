<?php

use Slim\App;

return function (App $app) {
  $app->add(new \Tuupola\Middleware\JwtAuthentication([
  "path" => "/", /* or ["/api", "/admin"] */
  "ignore" => ["/login","/user","/Events"],
  "attribute" => "decoded_token_data",
  "secret" => "supersecretkeyyoushouldnotcommittogithub",
  "algorithm" => ["HS256"],
  "error" => function ($response, $arguments) {
      $data["status"] = "error";
      $data["message"] = $arguments["message"];
      return $response
          ->withHeader("Content-Type", "application/json")
          ->write(json_encode($data, JSON_UNESCAPED_SLASHES | JSON_PRETTY_PRINT));
  }
]));
};
