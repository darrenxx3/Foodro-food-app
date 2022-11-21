<?php
include("conn.php");
include("function.php");

if(isset($_POST["user_id"]) && isset($_POST["category_id"]) && isset($_POST["food_id"]) && isset($_POST["quantity"]))
{
    echo createOrder($connection, $_POST["user_id"], $_POST["category_id"], $_POST["food_id"], $_POST["quantity"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    http_response_code(400);
    echo json_encode($response);
}
