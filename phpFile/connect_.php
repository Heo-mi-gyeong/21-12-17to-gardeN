<?php

$db_host = "localhost"; 
$db_user = "togarden";
$db_passwd = "capstone0109!";
$db_name = "togarden"; 

$conn = new mysqli($db_host,$db_user,$db_passwd,$db_name);

if ($con -> connect_errno) {
    echo "MySQL 연결 오류: " . $con -> connect_error;
    exit();
}

$conn -> set_charset("utf8");

?>