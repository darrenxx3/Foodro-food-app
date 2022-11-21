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
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
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
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
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
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
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
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
}

function getFoodById($connection, $food_id)
{
    $result = mysqli_query($connection, "SELECT * FROM Food WHERE food_id=$food_id");
    $response = array();
    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        $r = mysqli_fetch_assoc($result);
        $food = array();
        $food["food_id"] = $r["food_id"];
        $food["food_name"] = $r["food_name"];
        $food["food_price"] = $r["food_price"];
        $food["food_image"] = $r["food_image"];
        $food["merchant_id"] = $r["merchant_id"];
        array_push($response["data"], $food);
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
}

function getOrderDetailById($connection, $order_id)
{
    $result = mysqli_query($connection, "SELECT * FROM OrderDetail WHERE order_id='${order_id}'");
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($result)) {
            $detail = array();
            $detail["order_id"] = $r["order_id"];
            $detail["food_id"] = $r["food_id"];
            $detail["quantity"] = $r["quantity"];
            $detail["total"] = $r["total"];
            array_push($response["data"], $detail);
        }
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
}

function getPaymentById($connection, $payment_id)
{
    $result = mysqli_query($connection, "SELECT * FROM Payment WHERE payment_id='${payment_id}'");
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        $r = mysqli_fetch_assoc($result);
        $payment = array();
        $payment["payment_id"] = $r["payment_id"];
        $payment["category_id"] = $r["category_id"];
        $payment["totalPayment"] = $r["totalPayment"];
        $payment["total"] = $r["total"];
        array_push($response["data"], $payment);
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
}

function getOrderByUser($connection, $user_id)
{
    $result = mysqli_query($connection, "SELECT Food.merchant_id, Food.food_id, Food.food_name, 
    Food.food_price, OrderDetail.quantity, OrderDetail.total, OrderDetail.status_id,  
    Orders.user_id, Orders.orderDate FROM Orders
    INNER JOIN OrderDetail ON Orders.order_id = OrderDetail.order_id
    INNER JOIN Food ON OrderDetail.food_id = Food.food_id WHERE Orders.user_id = $user_id");

    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($result)) {
            $order = array();
            $order["merchant_id"] = $r["merchant_id"];
            $order["food_id"] = $r["food_id"];
            $order["food_name"] = $r["food_name"];
            $order["food_price"] = $r["food_price"];
            $order["quantity"] = $r["quantity"];
            $order["total"] = $r["total"];
            $order["status_id"] = $r["status_id"];
            $order["user_id"] = $r["user_id"];
            $order["orderDate"] = $r["orderDate"];
            array_push($response["data"], $order);
        }
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
}

// To get all order received by a merchant
function getOrderMerchant($connection, $merchant_id)
{
    $result = mysqli_query($connection, "SELECT Food.merchant_id, Food.food_id, Food.food_name, 
    Food.food_price, OrderDetail.quantity, OrderDetail.total, OrderDetail.status_id,  
    Orders.user_id, Orders.orderDate FROM Orders
    INNER JOIN OrderDetail ON Orders.order_id = OrderDetail.order_id
    INNER JOIN Food ON OrderDetail.food_id = Food.food_id WHERE Food.merchant_id = $merchant_id");

    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($result)) {
            $order = array();
            $order["merchant_id"] = $r["merchant_id"];
            $order["food_id"] = $r["food_id"];
            $order["food_name"] = $r["food_name"];
            $order["food_price"] = $r["food_price"];
            $order["quantity"] = $r["quantity"];
            $order["total"] = $r["total"];
            $order["status_id"] = $r["status_id"];
            $order["user_id"] = $r["user_id"];
            $order["orderDate"] = $r["orderDate"];
            array_push($response["data"], $order);
        }
        $response["success"] = 1;
        $response["message"] = "OK";
        http_response_code(200);
        return json_encode($response);
    }
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    return json_encode($response);
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
        }
    }
    $response["success"] = 0;
    $response["message"] = "Wrong email or password";
    $response["code"] = "401";
    http_response_code(401);
    return json_encode($response);
}

function register($connection, $role, $firstname, $lastname, $password, $email)
{
    $password = password_hash($password, PASSWORD_DEFAULT);
    try {
        $q = $connection->prepare("INSERT INTO Users VALUES (NULL, ?, ?, ?, ?, ?)");
        $q->bind_param("ssssi", $firstname, $lastname, $password, $email, $role);
        $q->execute();
        return getUserById($connection, $q->insert_id);
    } catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = $e->getMessage();
        $response["code"] = $e->getCode();
        http_response_code($e->getCode());
        return json_encode($response);
    }
}

function createOrder($connection, $userid, $category, $food, $quantity, $proof)
{
    try {
        mysqli_query($connection, "INSERT INTO Orders VALUES (NULL, ${userid}, 1, NOW())");
        $totalPrice = 0;
        for ($i = 0; $i < count($food); $i++) {
            $price = (int)json_decode(getFoodById($connection, (int)$food[$i]))->data[0]->food_price;
            mysqli_query($connection, "INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 1, $food[$i], $quantity[$i], $price*$quantity[$i])");
            $totalPrice = $price * $quantity[$i];
        }
        mysqli_query($connection, "INSERT INTO Payment VALUES (LAST_INSERT_ID(), ${category}, ${totalPrice}, ${proof})");
        $response["success"] = 1;
        $response["message"] = "Success";
        $response["code"] = "200";
        http_response_code(200);
        return json_encode($response);
    } catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = $e->getMessage();
        $response["code"] = $e->getCode();
        http_response_code($e->getCode());
        return json_encode($response);
    }
}

function createFood($connection, $food_name, $food_price, $food_image, $merchant_id)
{
    try {
        $q = $connection->prepare("INSERT INTO Food VALUES (NULL, ?, ?, ?, ?)");
        $q->bind_param("sisi", $food_name, $food_price, $food_image, $merchant_id);
        $q->execute();
        // mysqli_query($connection, "INSERT INTO Food VALUES (NULL, '$food_name', $food_price, '$food_image', $merchant_id)");
        $response["success"] = 1;
        $response["message"] = "Success";
        $response["code"] = "200";
        http_response_code(200);
        return json_encode($response);
    } catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = $e->getMessage();
        $response["code"] = $e->getCode();
        http_response_code($e->getCode());
        return json_encode($response);
    }
}

/*
UPDATE
*/

function updateOrderStatus($connection, $order_id, $food_id, $newStatus)
{
    try {
        mysqli_query($connection, "UPDATE OrderDetail SET status_id = $newStatus 
        WHERE order_id = $order_id AND food_id = $food_id AND status_id = ($newStatus-1)");
        if (mysqli_affected_rows($connection)) {
            $response["success"] = 1;
            $response["message"] = "OK";
            $response["code"] = "200";
            http_response_code(200);
            return json_encode($response);
        } else {
            throw new Exception("No data updated", 404);
        }
    } catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = $e->getMessage();
        $response["code"] = $e->getCode();
        http_response_code($e->getCode());
        return json_encode($response);
    }
}

function updateFood($connection, $food_id, $food_name, $food_price, $food_image)
{
    try {
        $q = $connection->prepare("UPDATE Food SET
        food_name = ?,
        food_price = ?,
        food_image = ?
        WHERE food_id = ?");
        $q->bind_param("sisi", $food_name, $food_price, $food_image, $food_id);
        $q->execute();
        // mysqli_query($connection, "INSERT INTO Food VALUES (NULL, '$food_name', $food_price, '$food_image', $merchant_id)");
        if($q->affected_rows > 0){
            $response["success"] = 1;
            $response["message"] = "Success";
            $response["code"] = "200";
            http_response_code(200);
            return json_encode($response);
        }else{
            throw new Exception("No data updated", 520);
        }
    } catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = $e->getMessage();
        $response["code"] = $e->getCode();
        http_response_code($e->getCode());
        return json_encode($response);
    }
}