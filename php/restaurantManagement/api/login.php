<?php

include("connection.php");

$email = $_POST['email'];
$password = $_POST['password'];

$sql ="SELECT * FROM resto_user_regi WHERE email='$email' and password='$password'";

$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){
	
	$row=mysqli_fetch_assoc($result);	
	$data[]=array('id'=> $row['id'],'name'=> $row['name']);  

	echo json_encode($data);

}
else{
	echo "failed";
}

?>