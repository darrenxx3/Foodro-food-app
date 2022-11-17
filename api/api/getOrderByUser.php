<?php
include("conn.php");
include("function.php");

if(isset($_POST["user_id"])){
    echo getOrderByUser($connection, $_POST["user_id"]);
}

