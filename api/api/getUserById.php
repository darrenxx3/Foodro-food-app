<?php
include("conn.php");
include("function.php");

if(isset($_GET["user_id"])){
    $r = getUserById($connection, $_GET["user_id"]);
    echo json_decode($r)->data[0]->lastname;
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    $response["code"] = "400";
    http_response_code(400);
    echo json_encode($response);
}

