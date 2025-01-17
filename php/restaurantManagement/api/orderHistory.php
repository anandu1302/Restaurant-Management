<?php

include("connection.php");

$orderId = $_REQUEST['orderId'];

$sql = "SELECT * FROM resto_ordertable WHERE order_id='$orderId'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result)){

		$sql2 = "SELECT * FROM resto_foodtable WHERE id='$row[food_id]'";
		$result2 = mysqli_query($con,$sql2);
		$row2 = mysqli_fetch_array($result2);

		$data["data"][] = array('id'=>$row['id'],'price'=>$row2['price'],'fname'=>$row2['name'],'quantity'=>$row['quantity']);
	}
	echo json_encode($data);
}
else{
	echo "failed";
}

?>