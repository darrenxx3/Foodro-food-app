<?php
include("conn.php");
include("function.php");
$user_id = $_POST["user_id"];


echo getUserById($connection, $user_id);
