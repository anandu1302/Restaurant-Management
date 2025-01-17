<?php

include("connection.php");

$uid = $_REQUEST['uid'];

$sql = "SELECT * FROM resto_ordertable WHERE user_id='$uid'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0) {
    while($row = mysqli_fetch_assoc($result)) {

        $food_ids = $row['food_ids'];

        
        $food_sql = "SELECT id, resto_id,name, food_image FROM 
                        resto_foodtable WHERE id IN ($food_ids)";

        $food_result = mysqli_query($con, $food_sql);

        $food_details = [];
        if(mysqli_num_rows($food_result) > 0) {
            while($food_row = mysqli_fetch_assoc($food_result)) {

                $sql2 = "SELECT * FROM resto_resto_tbl WHERE id ='$food_row[resto_id]'";

                $result2 = mysqli_query($con, $sql2);
                $row2 = mysqli_fetch_assoc($result2);

                $food_details[] = array('name'=>$food_row['name'],'rest_name'=>$row2['name'],'rest_place' => $row2['place'],'rest_image'=>$row2['image']);
            }
        }

        // Add food details to the current row data
        $row['food_details'] = $food_details;

        $data["data"][] = $row;

    }
    echo json_encode($data);
} else {
    echo "failed";
} 


?>