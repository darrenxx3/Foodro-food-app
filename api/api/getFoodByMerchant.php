<?php
include("conn.php");
include("function.php");


if(isset($_GET["merchID"])){
    echo getFoodByMerchant($connection, $merchant_id);
}



