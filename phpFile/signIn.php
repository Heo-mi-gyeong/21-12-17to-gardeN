<!DOCTYPE html>
<html>
<body>
<header> <meta name="viewport" content="width=device-width,initial-scale=1"> </header>

<?php

include "connect.php";

$sql = "SELECT id, pw FROM Users";

$result = mysqli_query($con, $sql);

$idArray = array();
$pwArray = array(); 

if (mysqli_num_rows($result) > 0) {
    while($row = mysqli_fetch_assoc($result)) {
        $idArray[] = $row["id"];
        $pwArray[] = $row["pw"];
    }
}

mysqli_close($con);

?>

<h2>to-gardeN</h2>
<form onsubmit="return signInTest()">
    <fieldset>
        <legend>로그인</legend>
        <label for="id">아이디:</label>
        <input type="text" id="id" name="id" required><br>
        <label for="pw">비밀번호:</label>
        <input type="password" id="pw" name="pw" required><br><br>
        <button type="submit" >로그인</button>
        <button type="button" onclick="location.href='http://togarden.dothome.co.kr/signUp.php' ">회원가입</button>
    </fieldset>
</form>

<script type="text/javascript">
function CallAndroid(){
window.java.AlertMsg(document.getElementById('id').value);
}

    function signInTest(){
        var inputid = document.getElementById('id').value;
        var inputpw = document.getElementById('pw').value;
        var idArray = <?php echo json_encode($idArray)?>;
        var pwArray = <?php echo json_encode($pwArray)?>;
        if (!inputid) {
            consol.log("아이디를 입력하세요.");
            return false;
        } else if (!inputpw) {
            alert("비밀번호를 입력하세요.");
            return false;
        } else {
            if (idArray.includes(inputid) == false) {
                alert("가입된 아이디가 없습니다.");
                return false;
            } else if (pwArray.includes(inputpw) == false) {
                alert("비밀번호가 틀렸습니다.");
                return false;
            } else {
	    javascript:CallAndroid()
                return true;
            }
        }
    }

</script>
</body>
</html>