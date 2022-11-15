<?php

$dbusername = "adminMobapp";
$dbpassword = "admin";
$dbname = "mobapp";
$dbport = 3306;

$connection = new mysqli("127.0.0.1", $dbusername, $dbpassword, $dbname, $dbport);
// $connection = mysqli_connect("localhost", $dbusername, $dbpassword, $dbname, $dbport);
// https://dzkrrbb.medium.com/rest-api-with-php-get-post-put-delete-8365fe092618
?>