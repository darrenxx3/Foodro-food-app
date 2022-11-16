
<?php
include("conn.php");
include("function.php");

if(isset($_GET["food_id"])){
    echo getFoodByMerchant($connection, $food_id);
}

