<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');
try{
$ID = isset($_POST['ID']) ? $_POST['ID'] : '';
$Email = isset($_POST['Email']) ? $_POST['Email'] : '';
$Name = isset($_POST['Name']) ? $_POST['Name'] : '';
$Gender = isset($_POST['Gender']) ? $_POST['Gender'] : '';
$Birth = isset($_POST['Birth']) ? $_POST['Birth'] : '1997-04-10';
$PW = isset($_POST['PW']) ? $_POST['PW'] : '';
$Address = isset($_POST['Address']) ? $_POST['Address'] : '';
$sql_user = "insert into user(ID,PW,Name,Gender,Email,Birth) values('$ID', '$PW','$Name','$Gender','$Email','$Birth')";
$stmt_user = $con->prepare($sql_user);
$stmt_user->execute();
$sql_address = "insert into accountaddress(userID,address) values('$ID','$Address')";
$stmt_address = $con->prepare($sql_address);
$stmt_address->execute();
echo 'true';
}catch(PDOException $e){
	echo $e;
}
?>