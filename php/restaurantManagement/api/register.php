<?php

include("connection.php");

$name = $_POST['name'];
$number = $_POST['number'];
$address = $_POST['address'];
$email = $_POST['email'];
$password = $_POST['password'];


$sql ="INSERT INTO resto_user_regi (name,email,number,address,password) VALUES ('$name','$email','$number','$address','$password')";

if(mysqli_query($con,$sql)){
	

	echo "success";
}
else{
	
	echo"failed";
}


?>