<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');
$ID = isset($_POST['ID']) ? $_POST['ID'] : '';
$Email = isset($_POST['Email']) ? $_POST['Email'] : '';
$sql = "select PW from user where ID='$ID' and Email='$Email'";
$stmt = $con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	$data = array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
		extract($row);
		array_push($data,
			array('PW'=>$row["PW"]));}
	header('Content-Type: application/json; charset=utf8');
	$json = json_encode(array("foundPW"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;
}else{
	echo 'false';
}
?>

