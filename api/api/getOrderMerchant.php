<?php
include("conn.php");
include("function.php");

if(isset($_POST["merchant_id"])){
    echo getOrderMerchant($connection, $_POST["merchant_id"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    $response["code"] = "400";
    http_response_code(400);
    echo json_encode($response);
}

