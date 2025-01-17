<?php

include("connection.php");

$uid = $_POST['uid'];
$orderId = $_POST['orderId'];
$description = $_POST['description'];

$date = date('d-m-Y');


$sql ="INSERT INTO resto_complainttable (user_id,order_id,complaint,date) VALUES ('$uid','$orderId','$description','$date')";

if(mysqli_query($con,$sql)){
	

	echo "success";
}
else{
	
	echo"failed";
}


?>