<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');
$Email = isset($_POST['Email'])?$_POST['Email'] : '';
$sql = "select * from user where Email = '$Email'";
$stmt = $con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	echo 'false';
}else
	echo 'true';
?>

