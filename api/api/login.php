<?php
include("conn.php");
include("function.php");

if (isset($_POST["email"]) && isset($_POST["password"])) {
    echo login($connection,  $_POST["email"], $_POST["password"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    http_response_code(400);
    echo json_encode($response);
}
