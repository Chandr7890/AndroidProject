<?php
require("conn.php");
// Check if the POST request contains the expected parameters
if ($_SERVER['REQUEST_METHOD'] === 'POST' &&
    isset($_POST['param1']) && isset($_POST['param2']) &&
    isset($_POST['param3']) && isset($_POST['param4']) &&
    isset($_POST['param5']) && isset($_POST['param6']) &&
    isset($_POST['param7']) && isset($_POST['param8'])) {

    // Retrieve the values sent from the app
    $param1 = $_POST['param1'];
    $param2 = $_POST['param2'];
    $param3 = $_POST['param3'];
    $param4 = $_POST['param4'];
    $param5 = $_POST['param5'];
    $param6 = $_POST['param6'];
    $param7 = $_POST['param7'];
    $param8 = $_POST['param8'];
    $param9 = $_POST['param9'];
    $param10 = $_POST['param10'];
    $param11 = $_POST['param11'];
    $param12 = $_POST['param12'];
    $id = $_POST["id"];

    // Do something with the received values, for example, you can insert them into a database
    // Replace this with your logic

    // Example: Connect to a MySQL database and insert values
  
    $sql1 = mysqli_query($conn, "SELECT pbp FROM bsmwt where pid='$id' ");
    $sql2 = mysqli_query($conn, "SELECT phr FROM bsmwt where  pid ='$id' ");
    $sql3 = mysqli_query($conn, "SELECT pso2 FROM bsmwt where pid='$id' ");
    $sql4 = mysqli_query($conn, "SELECT pdys FROM bsmwt where  pid ='$id' ");
    $sql5 = mysqli_query($conn, "SELECT pfat FROM bsmwt where pid='$id' ");
    $sql6 = mysqli_query($conn, "SELECT ebp FROM bsmwt where  pid ='$id' ");
    $sql7 = mysqli_query($conn, "SELECT ehr  FROM bsmwt where pid='$id' ");
    $sql8 = mysqli_query($conn, "SELECT eso2 FROM bsmwt where  pid ='$id' ");
    $row = mysqli_fetch_assoc($sql1);
    $row1= mysqli_fetch_assoc($sql2);
    $row2 = mysqli_fetch_assoc($sql3);
    $row3= mysqli_fetch_assoc($sql4);
    $row4= mysqli_fetch_assoc($sql5);
    $row5= mysqli_fetch_assoc($sql6);
    $row6 = mysqli_fetch_assoc($sql7);
    $row7= mysqli_fetch_assoc($sql8);

    // Access the value of 1Qnr1 column from the fetched row
    $s1 = $row['pbp'];
    $s2 = $row1['phr'];
    $s3 = $row2['pso2'];
    $s4 = $row3['pdys'];
    $s5 = $row4['pfat'];
    $s6 = $row5['ebp'];
    $s7 = $row6['ehr'];
    $s8 = $row7['eso2'];
    if($s1===""&&$s2===""&&$s3===""&&$s4===""&&$s5===""&&$s6===""&&$s7===""&&$s8===""){

    // Example query to insert values into a table
    $sql = "UPDATE bsmwt SET pbp='$param1', phr='$param2', pso2='$param3', pdys='$param4', pfat='$param5', ebp='$param6', ehr='$param7', eso2='$param8',edys='$param9',efat='$param10',t1='$param11',t2='$param12' WHERE pid='$id'";
    $conn->query($sql);   
    echo "Data inserted successfully"; 
}else{
    $sql1 = "UPDATE asmwt SET pbp='$param1', phr='$param2', pso2='$param3', pdys='$param4', pfat='$param5', ebp='$param6', ehr='$param7', eso2='$param8',edys='$param9',efat='$param10',t1='$param11',t2='$param12' WHERE pid='$id'";
         $conn->query($sql1);   
        echo "Data inserted successfully";
    }
    if ($conn->query($sql) === false&&$conn->query($sql1) === false) {
        echo "Error: " . $sql . "<br>" . $conn->error;
    } 

    $conn->close();
} else {
    // Parameters are missing
    echo "Missing parameters";
}
?>
