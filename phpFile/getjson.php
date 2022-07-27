<?php  

include "connect.php";

$id=isset($_POST['id']) ? $_POST['id'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

mysqli_set_charset($con,"utf8"); 

if ($id != "" ){ 
$sql="select * from Plant where id='$id'";

$result=mysqli_query($con,$sql);
$data = array();   
if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array(
	'id'=>$row[0],
	'plantname'=>$row[1],
	'exchange'=>$row[2],
	'share'=>$row[3],
	'hope'=>$row[4],
	'image'=>$row[5]

        ));
    }}

    header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("$db_name"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($con);
} 


 
mysqli_close($con);  
   
?>