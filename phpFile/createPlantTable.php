<?php

include "connect.php";

$sql = "CREATE TABLE Plant(
    id VARCHAR(20) NOT NULL,
    plantname VARCHAR(20) NOT NULL,
    exchange VARCHAR(20) NOT NULL,
    share VARCHAR(20) NOT NULL,
    hope VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(id) REFERENCES Users(id)
) charset=utf8";

if (mysqli_query($con,$sql)){
    echo "Plant 테이블 생성 성공";
} else {
    echo "테이블 생성 오류 : " . mysqli_error($con);
    exit;
}

mysqli_close($con);

?>