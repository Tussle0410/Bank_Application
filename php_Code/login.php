<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');
$ID = isset($_POST['ID']) ? $_POST['ID'] : '';
$PW = isset($_POST['PW']) ? $_POST['PW'] : '';
$sql = "select * from user where ID=BINARY('$ID') and PW=BINARY('$PW')";
$stmt=$con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	$data = array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
		extract($row);
		array_push($data,
		         array('ID'=>$row["ID"],
               		'Name'=>$row["Name"],
                		'Birth'=>$row["Birth"],
                		'Gender'=>$row["Gender"],
                		'Email'=>$row["Email"],
                		'addressMainKinds'=>$row["addressMainKinds"]
			));
		}
	header('Content-Type: application/json; charset=utf8');
	$json = json_encode(array("login"=>$data),JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;
}else{
	echo 'false';
}
?>


