<?php
include("conn.php");
include("function.php");

if(isset($_POST["user_id"]) && isset($_POST["category_id"]) && isset($_POST["food_id"]) && isset($_POST["quantity"]) && isset($_POST["proof"]))
{
    echo createOrder($connection, $_POST["user_id"], $_POST["category_id"], $_POST["food_id"], $_POST["quantity"], $_POST["proof"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    $response["code"] = "400";
    http_response_code(400);
    echo json_encode($response);
}
