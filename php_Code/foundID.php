<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');
$name = isset($_POST['name']) ? $_POST['name'] : '';
$birth = isset($_POST['birth']) ? $_POST['birth'] : '';
$sql = "select ID from user where Name='$name' and Birth='$birth'";
$stmt = $con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	$data = array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
		extract($row);
		array_push($data,
			array('ID'=>$row["ID"]));}
	header('Content-Type: application/json; charset=utf8');
	$json = json_encode(array("foundID"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;
}else{
	echo 'false';
}
?>

