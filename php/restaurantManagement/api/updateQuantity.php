<?php

include("connection.php");

$id = $_POST['id'];
$quantity = $_POST['quantity'];

$sql = "UPDATE resto_cart_tbl SET quantity='$quantity' WHERE id='$id'";

if(mysqli_query($con,$sql)){
	
	echo "success";
}
else{
	echo"failed";
}


?>