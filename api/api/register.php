<?php
include("conn.php");
include("function.php");

if(isset($_POST["role"]) && isset($_POST["password"]) && isset($_POST["firstname"]) && isset($_POST["lastname"]) && isset($_POST["email"]) && isset($_POST["image"])){ 
    echo register($connection, intval($_POST["role"]), $_POST["firstname"], $_POST["lastname"], $_POST["password"], $_POST["email"], $_POST["image"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    $response["code"] = "400";
    http_response_code(400);
    echo json_encode($response);
}
