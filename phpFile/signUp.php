<!DOCTYPE html>
<html>
<body>
<header> <meta name="viewport" content="width=device-width,initial-scale=1"> </header>

<?php

include "connect.php";
  
$sql = "SELECT * FROM Users";

$result = mysqli_query($con, $sql);

$row_id = array();
while($row = mysqli_fetch_assoc($result)) {
  array_push($row_id, $row['id']);
}

mysqli_close($con);
?>

<h2>to-gardeN</h2>

<form action="signUpCompl.php" method="post" onsubmit="return signUpTest()">
  <fieldset>
    <legend>회원가입</legend>
    
    <label for="id">아이디:</label>
    <input type="text" id="id" name="id" placeholder="_(언더바) 사용 불가" size="15">
    <input type="button" onClick="checkID()" value="중복 확인"><br>
    
    <label for="pw1">비밀번호:</label>
    <input type="password" id="pw1" name="pw1" size="15"><br>
    
    <label for="pw2">비밀번호 재확인:</label>
    <input type="password" id="pw2" name="pw2"><br>

    <label for="place">(선택)내가 거래할 동네:</label>
    <input type="text" id="place" name="place" placeholder="읍·면·동까지 입력해주세요."><br><br>
    
    <input type="submit" value="회원가입 하기">
  </fieldset>
</form>

<script type="text/javascript">
  var i = 0;
  function checkID(){
      var inputid = document.getElementById('id').value;
      var userid = <?php echo json_encode($row_id)?>;
      if (inputid.includes("_") == true) {
        alert("_(언더바)는 사용하실 수 없습니다.")
        return false;
      } else if (userid.includes(inputid) == true) {
        alert("중복되는 아이디가 있습니다.")
        return false;
      } else {
        i = 1;
        alert("사용 가능한 아이디입니다.")
        return true;
      }
  }

  function signUpTest() {
      var inputid = document.getElementById('id').value;
      var password1 = document.getElementById('pw1').value;
      var password2 = document.getElementById('pw2').value;
      if (!inputid) {
        alert("아이디를 입력하세요.");
        return false;
      } else if (!password1) {
        alert("비밀번호를 입력하세요.");
        return false;
      } else if (!password2) {
        alert("비밀번호 재확인하세요.");
        return false;
      } else if (password1 != password2){
        alert("비밀번호가 일치하지 않습니다.");
        return false;
      } else if(i == 0) {
        alert("아이디 중복 확인을 하세요.")
        return false;
      } else if(i == 1) {
        alert("회원가입이 완료되었습니다.");
        return true;
      }
  }
</script>

</body>
</html>