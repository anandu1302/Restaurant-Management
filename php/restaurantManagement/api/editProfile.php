<?php

include("connection.php");

$uid = $_POST['uid'];
$name = $_POST['name'];
$email = $_POST['email'];
$number = $_POST['number'];
$address = $_POST['address'];


$sql = "UPDATE resto_user_regi SET name='$name',number='$number',email='$email',address='$address' WHERE id='$uid'";
        
 if(mysqli_query($con,$sql)){

    echo "Successfully Updated";

}else{

	echo"Failed to Update Profile";
   
}

?>