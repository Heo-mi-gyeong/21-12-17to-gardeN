<?php

include "connect.php";

$sql = "CREATE TABLE Users (
    no INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    id VARCHAR(20) NOT NULL,
    pw VARCHAR(20) NOT NULL,
    count INT(20) DEFAULT 0,
    PRIMARY KEY(no),
    FOREIGN KEY(id) REFERENCES plant(id)
) charset=utf8";

if (mysqli_query($con,$sql)){
    echo "Users 테이블 생성 성공";
} else {
    echo "Users 테이블 생성 오류 : " . mysqli_error($con);
    exit;
}

mysqli_close($con);

?>