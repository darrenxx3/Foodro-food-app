<?php
include("conn.php");
include("function.php");


if(isset($_GET["merchant_id"])){
    echo getFoodByMerchant($connection, $_GET["merchant_id"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    $response["code"] = "400";
    http_response_code(400);
    echo json_encode($response);
}



