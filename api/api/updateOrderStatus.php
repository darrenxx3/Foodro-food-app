<?php
include("conn.php");
include("function.php");

if (isset($_POST["order_id"]) && isset($_POST["food_id"]) && isset($_POST["status"])) {
    echo updateOrderStatus($connection, $_POST["order_id"], $_POST["food_id"],  intval($_POST["status"]));
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    $response["code"] = "400";
    http_response_code(400);
    echo json_encode($response);
}
