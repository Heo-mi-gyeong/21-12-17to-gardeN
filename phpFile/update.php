<?php  

include "connect.php";

$id=isset($_POST['id']) ? $_POST['id'] : '';
$count=isset($_POST['count']) ? $_POST['count'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

mysqli_set_charset($con,"utf8"); 

if ($id != "") {

    $sql="UPDATE Users SET count = '$count' WHERE id = '$id';";
    $result=mysqli_query($con,$sql);
    
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("me"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;
    
} else {  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($con);
} 

mysqli_close($con);  
   
?>