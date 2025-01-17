<?php

include("connection.php");

$uid = $_POST['userId'];
$note = $_POST['note'];
$tableNo = $_POST['tableNo'];
$amount = $_POST['amount'];
$cardname = $_POST['cardname'];
$cardnumber = $_POST['cardnumber'];
$cardtype = $_POST['cardtype'];
$cardmonth = $_POST['cardmonth'];
$cardyear = $_POST['cardyear']; 
$cvv = $_POST['cvv']; 

$date = date('d-m-Y');

$sel = "SELECT * FROM resto_cart_tbl WHERE user_id='$uid'";
$result = mysqli_query($con,$sel);

if (mysqli_num_rows($result) > 0) {

	$orderId = mt_rand(100000, 999999);

	while ($row=mysqli_fetch_array($result)){

		$sql2 = "SELECT * FROM resto_foodtable WHERE id='$row[food_id]'";
		$result2 = mysqli_query($con,$sql2);
		$row2 = mysqli_fetch_array($result2);

		$price = $row['quantity'] * $row2['price'];

		$sql3 = "INSERT INTO resto_ordertable(order_id,user_id,food_id,price,quantity,table_no,date,status) VALUES('$orderId','$uid','$row[food_id]',$price,$row[quantity],'$tableNo','$date','ordered')";

		mysqli_query($con,$sql3);

	
	}

	if ($note=="") {
		
		$note ="nil";
	}

	$sql4 = "INSERT INTO resto_notes_tbl(order_id,notes) VALUES('$orderId','$note')";
	mysqli_query($con,$sql4);

    $sql5 = "INSERT INTO resto_paymentable(order_id,userid,amount,card_no,card_name,cvv,date) VALUES('$orderId','$uid','$price','$cardnumber','$cardname','$cvv','$date')";

	mysqli_query($con,$sql5);

	$sql6 = "DELETE FROM resto_cart_tbl WHERE user_id='$uid'";

	mysqli_query($con,$sql6);

	echo "success";



}else {

	echo "Cart is empty";
}


?>