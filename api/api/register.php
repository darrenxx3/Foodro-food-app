<?php
include("conn.php");
include("function.php");

if(isset($_POST["role"]) && isset($_POST["password"]) && isset($_POST["firstname"]) && isset($_POST["lastname"]) && isset($_POST["email"])){ 
    echo register($connection, $_POST["role"], $_POST["firstname"], $_POST["lastname"], $_POST["password"], $_POST["email"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    http_response_code(400);
    echo json_encode($response);
}
