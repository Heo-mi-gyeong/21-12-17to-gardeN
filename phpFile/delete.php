<?php  

include "connect.php";

$id=isset($_POST['id']) ? $_POST['id'] : '';
$plantname=isset($_POST['count']) ? $_POST['count'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if ($plantname != ""){
    
    $sql="DELETE FROM Plant where id='$id' AND plantname='$plantname'";
    $result=mysqli_query($con,$sql);
    
    if($result){  
	    echo '성공적으로 삭제됐습니다.';
    } else {
	    echo '실패';
    }
    
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("$db_name"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;

} else {  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($con);
} 
 
mysqli_close($con);  
   
?>