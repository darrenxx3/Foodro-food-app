<?php
include("conn.php");
include("functionl.php");
$merchant_id = $_GET["merchID"];


echo getFoodByMerchant($connection, $merchant_id);

