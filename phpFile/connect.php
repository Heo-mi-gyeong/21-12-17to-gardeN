<?php

$db_host = "localhost"; 
$db_user = "togarden";
$db_passwd = "capstone0109!";
$db_name = "togarden"; 

$con = mysqli_connect($db_host,$db_user,$db_passwd,$db_name);

if (mysqli_connect_errno()) {
    echo "MySQL 연결 오류: " . mysqli_connect_error();
    exit;
}

mysqli_set_charset($con,"utf8");

?>