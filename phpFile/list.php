<?php  

include "connect.php";

$id=isset($_POST['id']) ? $_POST['id'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if ($id != "" ){ 
$sql="select * from Users where id!='$id'";

$result=mysqli_query($con,$sql);
$data = array();   

if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, array('id'=>$row['id'], 'count'=>$row['count'], 'place'=>$row['place']));
    }}

    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array($db_user=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;
    
} else {  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($con);
} 
 
mysqli_close($con);  
   
?>