<?php

include("connection.php");

$itemId = $_POST['itemId'];

$sql = "DELETE FROM resto_cart_tbl where id='$itemId'";

if (mysqli_query($con,$sql)) {

	echo "success";
}else{
	
	echo "failed";
}



?>