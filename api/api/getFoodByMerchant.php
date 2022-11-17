<?php
include("conn.php");
include("function.php");


if(isset($_GET["merchant_id"])){
    echo getFoodByMerchant($connection, $_GET["merchant_id"]);
}



