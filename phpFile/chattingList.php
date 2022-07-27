<?php

include "connect_.php";

$myID = $_POST['id'];

$myChatting = "_" . $myID . "_";

$row_data = array();

$result_table = $conn -> query("SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = SCHEMA () AND TABLE_NAME LIKE '%$myChatting%'");

if ( $result_table -> num_rows > 0 ) {
    while($row = mysqli_fetch_assoc($result_table)) {
        $table = $row["TABLE_NAME"];

        $yourID = str_replace($myChatting, '', $table);
        $yourID = str_replace('Chatting', '', $yourID);
        $yourID = str_replace('_', '', $yourID);

        $result_chatting = $conn -> query("SELECT id, count FROM Users WHERE id = '$yourID'");

        while($row_chatting = mysqli_fetch_assoc($result_chatting)) {
            $result_last = $conn -> query("SELECT * FROM $table ORDER BY no DESC LIMIT 1");
            $row_last = mysqli_fetch_assoc($result_last);

            if ($row_last != null) {
                array_push($row_data, array('id' => $row_chatting["id"], 'count' => $row_chatting["count"],
                'last_user' => $row_last["users"], 'last_text' => $row_last["text"]));
            }
        }
    }

    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("$db_name" => $row_data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;

} else {
    echo "$myID 우편 기록 없음";
}

$conn -> close();

?>