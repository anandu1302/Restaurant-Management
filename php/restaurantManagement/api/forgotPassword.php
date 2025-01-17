<?php 
include('connection.php');

	
	$email=$_POST['email'];

	$sel="SELECT * FROM resto_user_regi WHERE email='$_POST[email]'";
	$result = mysqli_query($con,$sel) or die(mysql_error());
	$row=mysqli_fetch_array($result);

	if($result > 0){

		/* implement email logic here */ 
		
	}else{
		echo "failed";
	}
	
	


?>