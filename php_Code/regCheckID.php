<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');	
$ID=isset($_POST['ID']) ? $_POST['ID'] : '';
$sql = "select * from user where ID = '$ID'";
$stmt = $con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	echo 'false';
}else{
	echo 'true';
}
?>

