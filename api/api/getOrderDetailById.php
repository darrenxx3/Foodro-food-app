<?php
include("conn.php");
include("function.php");


if(isset($_POST["order_id"])){
    echo getOrderDetailById($connection, $_POST["order_id"]);
}



