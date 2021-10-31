<?php
error_reporting('E_ALL');
ini_set('display_errors',1);
include('dbcon.php');
$address = isset($_POST['address']) ? $_POST['address'] : '';
$receiveAddress = isset($_POST['receiveAddress']) ? $_POST['receiveAddress'] : '';
$ID = isset($_POST['ID']) ? $_POST['ID'] : '';
$name = isset($_POST['name']) ? $_POST['name'] : '';
$receiveName = isset($_POST['receiveName'])? $_POST['receiveName'] : '';
$money = isset($_POST['money']) ? $_POST['money'] : '';
$pw = isset($_POST['pw']) ? $_POST['pw'] : '';
$pwCheckSql = "select * from user where ID='$ID' and PW='$pw'";
$pwCheckStmt = $con->prepare($pwCheckSql);
$pwCheckStmt->execute();
if($pwCheckStmt->rowCount() > 0){
	$remittanceSql  = "update 
}else{
	echo 'false'
?>

