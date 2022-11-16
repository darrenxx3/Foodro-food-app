<?php
include("conn.php");
include("function.php");


if(isset($_POST["payment_id"])){
    echo getPaymentById($connection, $_POST["payment_id"]);
}



