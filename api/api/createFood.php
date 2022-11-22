<?php
include("conn.php");
include("function.php");

if(isset($_POST["food_name"]) && isset($_POST["food_price"]) && isset($_POST["food_image"]) && isset($_POST["merchant_id"]))
{
    echo createFood($connection, $_POST["food_name"], (int)$_POST["food_price"], $_POST["food_image"], (int)$_POST["merchant_id"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    $response["code"] = "400";
    http_response_code(400);
    echo json_encode($response);
}
