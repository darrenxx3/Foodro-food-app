<?php
include("conn.php");
include("function.php");

if (isset($_POST["email"]) && isset($_POST["password"])) {
    echo login($connection,  $_POST["email"], $_POST["password"]);
}
