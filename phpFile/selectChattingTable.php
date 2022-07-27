<?php

include "connect_.php";

$myID = $_POST['id'];
$yourID = $_POST['userid'];

$ID = [$myID, $yourID];
sort($ID);

$table = "Chatting_" . $ID[0] . "_" . $ID[1] . "_";

$row_data = array();

$result = $conn -> query("SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = SCHEMA () AND TABLE_NAME LIKE '$table'");

if ( $result -> num_rows == 0 ) {

    $sql = "CREATE TABLE $table (
        no INT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
        users VARCHAR(20) NOT NULL,
        text VARCHAR(50) NOT NULL,
        PRIMARY KEY(no)
    ) charset=utf8";

    if ($conn -> query($sql) === TRUE) {
        echo "$table 테이블 생성 성공";
    } else {
        echo "$table 테이블 생성 오류 : " . $conn -> error;
        exit();
    }

} else {

    if ($result_chatting = $conn -> query("SELECT users, text FROM $table")) {

        if (mysqli_num_rows($result_chatting) > 0) {
            while($row = mysqli_fetch_assoc($result_chatting)) {
                array_push($row_data, array('users' => $row["users"], 'text' => $row["text"]));
            }
            header('Content-Type: application/json; charset=utf8');
            $json = json_encode(array("$db_name" => $row_data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;
        } else {
            echo "$table 테이블에 데이터 없음";
        }

    }

}

$conn -> close();

?>