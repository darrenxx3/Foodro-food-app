<?php
include("conn.php");
include("function.php");

if(isset($_GET["user_id"])){
    $r = getUserById($connection, $_GET["user_id"]);
    echo json_decode($r)->data[0]->lastname;
}

