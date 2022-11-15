<?php
include("conn.php");
include("function.php");

if(isset($_POST["role"]) && isset($_POST["password"]) && isset($_POST["firstname"]) && isset($_POST["lastname"]) && isset($_POST["email"])){
    $firstname =  $_POST["firstname"];
    $lastname =  $_POST["lastname"];
    $password =  $_POST["password"];
    $email =  $_POST["email"];
    
    echo register($connection, $_POST["role"], $_POST["firstname"], $_POST["lastname"], $_POST["password"], $_POST["email"]);


}
