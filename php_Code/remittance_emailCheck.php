<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');
$email = isset($_POST['email']) ? $_POST['email'] : '';
$name = isset($_POST['name']) ? $_POST['name'] : '';
$sql = "select * from accountaddress natural join user where email='$email' and name='$name'";
$stmt = $con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	echo 'true';
}else{
	echo 'false';
}
?>