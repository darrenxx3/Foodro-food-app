<?php
include("conn.php");

$result=mysqli_query($connection, "SELECT * FROM Food");
$response = array();

if(mysqli_num_rows
($result)>0){
    $response["data"] = array();
    while($r=mysqli_fetch_array($result)){
        $food = array();
        $food["food_id"] = $r["food_id"];
        $food["food_name"] = $r["food_name"];
        $food["food_image"] = $r["food_image"];
        array_push($response["data"], $food);
    }
    $response["success"] = 1;
    $response["message"] = "OK";
    http_response_code(200);
    echo json_encode($response);
}
else{
    $response["success"] = 0;
    $response["message"] = "No data available";
    http_response_code(404);
    echo json_encode($response);
}



?>
