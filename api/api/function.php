<?php
/*
//////////////////////////////   SELECTs   //////////////////////////////
*/

function getAllMerchant($connection)
{
    $result = mysqli_query($connection, "SELECT * FROM Users WHERE role_id = 2");
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        while ($r = mysqli_fetch_array($result)) {
            $user = array();
            $user["user_id"] = $r["user_id"];
            $user["firstname"] = $r["firstname"];
            $user["lastname"] = $r["lastname"];
            $user["email"] = $r["email"];
            $user["image"] = $r["image"];
            $user["role_id"] = $r["role_id"];
            $user["active"] = $r["active"];
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


function getUserById($connection, $user_id)
{
    $result = mysqli_query($connection, "SELECT user_id,firstname,lastname,email,image,role_id,active FROM Users WHERE user_id=$user_id");
    $response = array();
    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        $r = mysqli_fetch_assoc($result);
        $user = array();
        $user["user_id"] = $r["user_id"];
        $user["firstname"] = $r["firstname"];
        $user["lastname"] = $r["lastname"];
        $user["email"] = $r["email"];
        $user["image"] = $r["image"];
        $user["role_id"] = $r["role_id"];
        $user["active"] = $r["active"];

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
    $result = mysqli_query($connection, "SELECT Food.*, user_id, firstname, lastname, email, image, active FROM Food 
    INNER JOIN Users ON Food.merchant_id = Users.user_id WHERE merchant_id = $merchant_id");
    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $data = array();
        while ($r = mysqli_fetch_array($result)) {
            $food = [
                "food_id" => $r["food_id"],
                "food_name" => $r["food_name"],
                "food_price" => $r["food_price"],
                "food_image" => $r["food_image"],
                "merchant_id" => $r["merchant_id"],
                "listed" => $r["listed"],
            ];
            $user = [
                "user_id" => $r["user_id"],
                "firstname" => $r["firstname"],
                "lastname" => $r["lastname"],
                "email" => $r["email"],
                "active" => $r["active"],
                "image" => $r["image"]
            ];
            array_push($data, $food);
        }

        $response["data"] = [
            "food" => $data,
            "merchant" => $user,
        ];

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
        $food["listed"] = $r["listed"];
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


function getOrderByCustomer($connection, $user_id)
{
    $result = mysqli_query($connection, "SELECT Orders.order_id, Food.merchant_id, Food.food_id, Food.food_name, 
    Food.food_price, Food.listed, Food.food_image, OrderDetail.quantity, OrderDetail.total, OrderDetail.status_id,  
    Orders.user_id, Orders.orderDate, Users.firstname, Users.lastname, Users.email, Users.image, Users.active FROM Orders
    INNER JOIN OrderDetail ON Orders.order_id = OrderDetail.order_id
    INNER JOIN Food ON OrderDetail.food_id = Food.food_id 
    INNER JOIN Users ON Food.merchant_id = Users.user_id WHERE Orders.user_id = $user_id
    ORDER BY Orders.order_id ASC");

    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        $id = array();
        $order = array();
        while ($r = mysqli_fetch_array($result)) {
            if (!in_array($r["order_id"], $id)) {
                array_push($id, $r["order_id"]);
                if (count($id) > 1) {
                    array_push($response["data"], $order);
                }
                $order = array();
                $order["order_id"] = $r["order_id"];
                $order["orderDate"] = $r["orderDate"];
                $order["merchant"] = [
                    "user_id" => $r["user_id"],
                    "firstname" => $r["firstname"],
                    "lastname" => $r["lastname"],
                    "email" => $r["email"],
                    "active" => $r["active"],
                    "image" => $r["image"]
                ];
                $order["orderDetail"] = array();
            }
            $orderDetail = array();
            $orderDetail["food"] = [
                "merchant_id"  => $r["merchant_id"],
                "food_id" => $r["food_id"],
                "food_name" => $r["food_name"],
                "food_price" => $r["food_price"],
                "food_image" => $r["food_image"],
                "listed" => $r["listed"],

            ];
            $orderDetail["quantity"] = $r["quantity"];
            $orderDetail["total"] = $r["total"];
            $orderDetail["status_id"] = $r["status_id"];

            array_push($order["orderDetail"], $orderDetail);
            // array_push($response["data"], $order);


            // array_push($response["data"], $temp);

        }
        array_push($response["data"], $order);

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
    $result = mysqli_query($connection, "SELECT Orders.order_id, Food.merchant_id, Food.food_id, Food.food_name, 
    Food.food_price, Food.listed, Food.food_image, OrderDetail.quantity, OrderDetail.total, OrderDetail.status_id,  
    Orders.user_id, Orders.orderDate, Users.firstname, Users.lastname, Users.email, Users.active FROM Orders
    INNER JOIN Users ON Orders.user_id = Users.user_id
    INNER JOIN OrderDetail ON Orders.order_id = OrderDetail.order_id
    INNER JOIN Food ON OrderDetail.food_id = Food.food_id WHERE Food.merchant_id = $merchant_id
    ORDER BY Orders.order_id ASC");

    $response = array();

    if (mysqli_num_rows($result) > 0) {
        $response["data"] = array();
        $id = array();
        $order = array();
        while ($r = mysqli_fetch_array($result)) {
            if (!in_array($r["order_id"], $id)) {
                array_push($id, $r["order_id"]);
                if (count($id) > 1) {
                    array_push($response["data"], $order);
                }
                $order = array();
                $order["order_id"] = $r["order_id"];
                $order["orderDate"] = $r["orderDate"];
                $order["user"] = [
                    "user_id" => $r["user_id"],
                    "firstname" => $r["firstname"],
                    "lastname" => $r["lastname"],
                    "email" => $r["email"],
                    "active" => $r["active"],
                ];
                $order["orderDetail"] = array();
            }
            $orderDetail = array();
            $orderDetail["food"] = [
                "merchant_id"  => $r["merchant_id"],
                "food_id" => $r["food_id"],
                "food_name" => $r["food_name"],
                "food_price" => $r["food_price"],
                "food_image" => $r["food_image"],
                "listed" => $r["listed"],

            ];
            $orderDetail["quantity"] = $r["quantity"];
            $orderDetail["total"] = $r["total"];
            $orderDetail["status_id"] = $r["status_id"];

            array_push($order["orderDetail"], $orderDetail);
            // array_push($response["data"], $order);


            // array_push($response["data"], $temp);

        }
        array_push($response["data"], $order);

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

/*
//////////////////////////////   INSERT   //////////////////////////////
*/

function register($connection, $role, $firstname, $lastname, $password, $email, $image)
{
    $result = mysqli_query($connection, "SELECT MAX(user_id) FROM Users");

    if ($res = mysqli_fetch_array($result)) {
        $id = $res["MAX(user_id)"] + 1;
    } else {
        $id = 1;
    }
    $serverdir = processImage($image, "/userimage", $id);
    $password = password_hash($password, PASSWORD_DEFAULT);
    try {
        $q = $connection->prepare("INSERT INTO Users VALUES (NULL, ?, ?, ?, ?, ?, ?, TRUE)");
        $q->bind_param("sssssi", $firstname, $lastname, $password, $email, $serverdir, $role);
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

function createOrder($connection, $userid, $food, $quantity, $proof)
{
    $result = mysqli_query($connection, "SELECT MAX(order_id) FROM Orders");
    if ($res = mysqli_fetch_array($result)) {
        $id = $res["MAX(order_id)"] + 1;
    } else {
        $id = 1;
    }
    $serverdir = processImage($proof, "/proof", $id);

    try {
        mysqli_query($connection, "INSERT INTO Orders VALUES (NULL, ${userid}, NOW())");
        $totalPrice = 0;
        for ($i = 0; $i < count($food); $i++) {
            $price = (int)json_decode(getFoodById($connection, (int)$food[$i]))->data[0]->food_price;
            mysqli_query($connection, "INSERT INTO OrderDetail VALUES (LAST_INSERT_ID(), 1, $food[$i], $quantity[$i], $price*$quantity[$i])");
            $totalPrice = $price * $quantity[$i];
        }
        mysqli_query($connection, "INSERT INTO Payment VALUES (LAST_INSERT_ID(), ${totalPrice}, '${serverdir}')");
        return getOrderByCustomer($connection, $userid);
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
    $result = mysqli_query($connection, "SELECT MAX(food_id) FROM Food");
    if ($res = mysqli_fetch_array($result)) {
        $id = $res["MAX(food_id)"] + 1;
    } else {
        $id = 1;
    }
    $serverdir = processImage($food_image, "/foodimage", $id);
    try {
        $q = $connection->prepare("INSERT INTO Food VALUES (NULL, ?, ?, ?, ?, TRUE)");
        $q->bind_param("sisi", $food_name, $food_price, $serverdir, $merchant_id);
        $q->execute();
        // mysqli_query($connection, "INSERT INTO Food VALUES (NULL, '$food_name', $food_price, '$food_image', $merchant_id)");

        return getFoodById($connection, $q->insert_id);
    } catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = $e->getMessage();
        $response["code"] = $e->getCode();
        http_response_code($e->getCode());
        return json_encode($response);
    }
}

/*
//////////////////////////////   UPDATE   //////////////////////////////
*/

function updateOrderStatus($connection, $order_id, $food_id, $newStatus)
{
    try {
        mysqli_query($connection, "UPDATE OrderDetail SET status_id = $newStatus 
        WHERE order_id = $order_id AND food_id = $food_id AND status_id = ($newStatus-1)");
        if (mysqli_affected_rows($connection)) {
            $response["success"] = 1;
            $response["message"] = "success";
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
    $serverdir = processImage($food_image, "/foodimage", $food_id);
    try {
        $q = $connection->prepare("UPDATE Food SET
        food_name = ?,
        food_price = ?,
        food_image = ?
        WHERE food_id = ?");
        $q->bind_param("sisi", $food_name, $food_price, $serverdir, $food_id);
        $q->execute();
        // mysqli_query($connection, "INSERT INTO Food VALUES (NULL, '$food_name', $food_price, '$food_image', $merchant_id)");
        if ($q->affected_rows > 0) {
            return getFoodById($connection, intval($food_id));
        } else {
            throw new Exception("No data updated", 520);
        }
    } catch (Exception $e) {
        $response["success"] = 0;
        $response["message"] = $e->getMessage();
        $response["code"] = $e->getCode();
        // http_response_code($e->getCode());
        return json_encode($response);
    }
}

/*
//////////////////////////////   DELETE   //////////////////////////////
*/

function deleteFood($connection, $food_id)
{
    try {
        mysqli_query($connection, "UPDATE food SET listed = 0 WHERE food_id = $food_id AND listed = 1");
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

function processImage($image, $path, $name)
{
    if (empty(trim($image)) || $image == "\"\"" || $image == ''  || $image == '\'\'') {
        $image = file_get_contents("whiteBase64.txt");
    }

    $dir = $_SERVER["DOCUMENT_ROOT"] . $path;
    if (!file_exists($dir)) {
        mkdir($dir, 0777, true);
    }
    // list($type, $image) = explode(';', $image);
    // list(, $image) = explode(',', $image);
    $img = str_replace(' ', '+', $image);
    $filename = $name . ".jpg";
    $dir = $dir . "/" . $filename;
    $serverdir = $path . "/" . $filename;
    file_put_contents($dir,  base64_decode($img));
    return $serverdir;
}
