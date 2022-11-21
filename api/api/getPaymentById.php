<?php
include("conn.php");
include("function.php");


if(isset($_POST["payment_id"])){
    echo getPaymentById($connection, $_POST["payment_id"]);
} else {
    $response["success"] = 0;
    $response["message"] = "Wrong method or parameter";
    http_response_code(400);
    echo json_encode($response);
}



