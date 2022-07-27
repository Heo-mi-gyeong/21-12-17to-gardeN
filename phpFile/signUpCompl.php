<?php

include "connect.php";

$id = $_POST['id'];
$pw = $_POST['pw1'];
$place = $_POST['place'];

$sql = "INSERT INTO Users (id, pw, place) VALUES ('$id', '$pw', '$place')";

mysqli_query($con,$sql);

echo("<script>location.href='http://togarden.dothome.co.kr/signIn.php';</script>");

mysqli_close($con);

?>