<?php
include("conn.php");
include("function.php");

if(isset($_POST["merchant_id"])){
    echo getOrderMerchant($connection, $_POST["merchant_id"]);
}

