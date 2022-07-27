<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
    // 테이블에 값 쓰기.
    

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        
        $id=$_POST['id'];
        $plantname=$_POST['plantname'];
        $exchange=$_POST['exchange'];
        $share=$_POST['share'];
        $hope=$_POST['hope'];
        $image=$_POST['image'];


        if(!isset($errMSG)) 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다. 
                $stmt = $con->prepare('INSERT INTO Plant(id, plantname, exchange, share, hope,image) VALUES(:id, :plantname, :exchange, :share, :hope,:image)');
                $stmt->bindParam(':id', $id);
                $stmt->bindParam(':plantname', $plantname);
	    $stmt->bindParam(':exchange', $exchange);
	    $stmt->bindParam(':share', $share);
	    $stmt->bindParam(':hope', $hope);
	    $stmt->bindParam(':image', $image);

                if($stmt->execute())
                {
                    $successMSG = "등록되었습니다.";
                }
                else
                {
                    $errMSG = "식물 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                Id: <input type = "text" name = "id" />
                Plantname: <input type = "text" name = "plantname" />
                Exchange: <input type = "text" name = "exchange" />
                Share: <input type = "text" name = "share" />
                Hope: <input type = "text" name = "hope" />
	    Image: <input type = "text" name = "image" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>