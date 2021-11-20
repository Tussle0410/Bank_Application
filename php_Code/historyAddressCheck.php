<?php
error_reporting(E_ALL);
ini_set('errors_display',1);
include('dbcon.php');
$ID = isset($_POST['ID']) ? $_POST['ID'] : '';
$Name = isset($_POST['productionName']) ? $_POST['productionName'] : '';
$sql = "select * from accountaddress natural join production where ID='$ID' and productionName='$Name' limit 1";
$stmt = $con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	$data=array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
		extract($row);
		array_push($data,
			array('address'=>$row['address'],
			'name'=>$row['addressName'],
			'money'=>$row['money']));
	}
	header("Content-Type:application/json; charset=utf8");
	$json = json_encode(array("info"=>$data),JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;
}else{
	echo 'fail';
}
?>

