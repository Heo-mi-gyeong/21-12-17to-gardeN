<?php

include "connect_.php";

$myID = $_POST['id'];
$yourID = $_POST['userid'];
$text = $_POST['text'];

$ID = [$myID, $yourID];
sort($ID);

$table = "Chatting_" . $ID[0] . "_" . $ID[1] . "_";

$result = $conn -> query("INSERT INTO $table (users, text) VALUES ('$myID', '$text')");

if ($result === TRUE){
    echo "$table 테이블에 행 삽입 생성 성공";
} else {
    echo "$table 테이블에 행 삽입 오류 : " . $conn -> error;
    exit();
}

if ($result_chatting = $conn -> query("SELECT no, users, text FROM $table")) {

    if (mysqli_num_rows($result_chatting) > 0) {
        while($row = mysqli_fetch_assoc($result_chatting)) {
            array_push($row_data,
                array('users' => $row["users"],
                    'text' => $row["text"]));
        }
    } else {
        echo "$table 테이블에 데이터 없음";
    }
}

header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("$db_name"=>$row_data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

$conn -> close();

?>