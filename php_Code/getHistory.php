<?PHP
error_reporting(E_ALL);
ini_set('display_errors',1);
include('dbcon.php');
$address = isset($_POST['address']) ? $_POST['address'] : '';
$length = isset($_POST['length'])? $_POST['length'] : '';
$orderBy = isset($_POST['orderBy'])? $_POST['orderBy'] : '';
if($orderBy==='money'){
$sql="select * from transactionHistory where (sendAddress = '$address' OR receiveAddress = '$address') AND transactionDate>DATE_SUB(now(),INTERVAL '$length' DAY) order by $orderBy DESC";
}else{
$sql="select * from transactionHistory where (sendAddress = '$address' OR receiveAddress = '$address') AND transactionDate>DATE_SUB(now(),INTERVAL '$length' DAY) order by $orderBy ";
}
$stmt = $con->prepare($sql);
$stmt->execute();
if($stmt->rowCount()>0){
	$data = array();
	while($row=$stmt->fetch(PDO::FETCH_ASSOC)){
		extract($row);
		array_push($data,
			array('sendAddress'=>$row['sendAddress'],
			'receiveAddress'=>$row['receiveAddress'],
			'sendName'=>$row['sendName'],
			'receiveName'=>$row['receiveName'],
			'money'=>$row['money'],
			'transactionDate'=>$row['transactionDate']));
	}
	header("Content-Type:application/json; charset=utf8");
	$json = json_encode(array("history"=>$data),JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;		
}else{
	echo 'fail';
}
?>


