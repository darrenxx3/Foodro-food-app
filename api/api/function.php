<?php

function getAllUser($connection)
{
    $result = mysqli_query($connection, "SELECT * FROM Users");
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($result)) {
            $user = array();
            $user["user_id"] = $r["user_id"];
            $user["firstname"] = $r["firstname"];
            $user["lastname"] = $r["lastname"];
            $user["email"] = $r["email"];
            $user["role_id"] = $r["role_id"];
            array_push($response["data"], $user);
        }
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "No data available";
        http_response_code(404);
        return json_encode($response);
    }
}

function getAllFood($connection)
{
    $result = mysqli_query($connection, "SELECT * FROM Food");
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($result)) {
            $food = array();
            $food["food_id"] = $r["food_id"];
            $food["food_name"] = $r["food_name"];
            $food["food_image"] = $r["food_image"];
            array_push($response["data"], $food);
        }
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "No data available";
        http_response_code(404);
        return json_encode($response);
    }
}

function getUserById($connection, $user_id,)
{
    $result = mysqli_query($connection, "SELECT user_id,firstname,lastname,email,role_id FROM Users WHERE user_id=$user_id");
    $response = array();
    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        $r = mysqli_fetch_assoc($result);
        $user = array();
        $user["user_id"] = $r["user_id"];
        $user["firstname"] = $r["firstname"];
        $user["lastname"] = $r["lastname"];
        $user["email"] = $r["email"];
        $user["role_id"] = $r["role_id"];
        array_push($response["data"], $user);
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "No data available";
        http_response_code(404);
        return json_encode($response);
    }
}

function getFoodByMerchant($connection, $merchant_id)
{
    $result = mysqli_query($connection, "SELECT * FROM Food WHERE merchant_id='${merchant_id}'");
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($result)) {
            $food = array();
            $food["food_id"] = $r["food_id"];
            $food["food_name"] = $r["food_name"];
            $food["food_image"] = $r["food_image"];
            array_push($response["data"], $food);
        }
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "No data available";
        http_response_code(404);
        return json_encode($response);
    }
}

function login($connection, $email, $password)
{

    $q = $connection->prepare("SELECT password FROM Users WHERE email=?");
    // $result = mysqli_query($connection, "SELECT password FROM Users WHERE email='${email}' or email='${email}'");
    $q->bind_param("s", $email);
    $q->execute();

    $result = $q->get_result();
    $hash = $result->fetch_row();
    if ($hash) {
        $verify = password_verify($password, $hash[0]);
        if ($verify) {
            $result = mysqli_query($connection, "SELECT user_id FROM Users WHERE email='${email}'");
            $r = mysqli_fetch_assoc($result);

            return getUserById($connection, $r["user_id"]);
        } else {
            return "Wrong email or password";
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "Wrong email or password";
        http_response_code(401);
        return json_encode($response);
    }
}

function register($connection, $role, $firstname, $lastname, $password, $email)
{
    $password = password_hash($password, PASSWORD_DEFAULT);

    $q = $connection->prepare("INSERT INTO Users VALUES (NULL, ?, ?, ?, ?, ?)");
    $q->bind_param("ssssi", $firstname, $lastname, $password, $email, $role);
    $q->execute();

    return getUserById($connection, $q->insert_id);
}
