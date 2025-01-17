<?php

include("connection.php");

$restID = $_POST['restId'];

$sql = "SELECT * FROM resto_foodtable WHERE resto_id='$restID'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) > 0){

	while($row = mysqli_fetch_assoc($result))
		$data["data"][] = $row;
	echo json_encode($data);
}
else{
	echo "failed";
}

?>