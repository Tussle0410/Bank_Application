<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');
$address = isset($_POST['address'])?$_POST['address'] : '';
$sql = "select * from accountaddress where address = '$address'";
$stmt = $con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	echo 'false';
}else{
	echo 'true';
}
?>