<?php
error_reporting('E_ALL');
ini_set('display_errors',1);
include('dbcon.php');
$email = isset($_POST['email'])?$_POST['email'] : '';
$sql = "select address,Name from accountaddress natural join user where Email='$email'";
$stmt = $con->prepare($sql);
$stmt->execute();
$data = array();
while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
	extract($row);
	array_push($data,
		array('address'=>$row['address'],
		'name'=>$row['Name']));
}
header('Content-Type:application/json; charset=utf8');
$json = json_encode(array("address"=>$data),JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;
?>