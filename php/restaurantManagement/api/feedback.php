<?php

include("connection.php");

$uid = $_POST['uid'];
$rating = $_POST['rating'];
$comments = $_POST['comments'];

$sql ="INSERT INTO resto_feedtable (user_id,rating,feedback) VALUES ('$uid','$rating','$comments')";

if(mysqli_query($con,$sql)){
	
	echo"success";
}
else{
	
	echo"failed";
}

?>