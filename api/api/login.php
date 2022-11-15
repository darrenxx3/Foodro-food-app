<?php
include("conn.php");
include("function.php");

if (isset($_POST["email"]) && isset($_POST["password"])) {

    $email =  $_POST["email"];
    $password =  $_POST["password"];
    echo login($connection, $email, $password);
}
