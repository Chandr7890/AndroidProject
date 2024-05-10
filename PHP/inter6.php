<?php
require("conn.php");
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $json = file_get_contents('php://input');
    $data = json_decode($json, true);

    if (isset($_POST["pid"])) {
       
        $id = $_POST["pid"];

        $sql1 = mysqli_query($conn, "SELECT pso2 FROM bsmwt where pid='$id' ");
        $sql2 = mysqli_query($conn, "SELECT eso2 FROM bsmwt where  pid ='$id' ");
        $sql3 = mysqli_query($conn, "SELECT pso2 FROM asmwt where pid='$id' ");
        $sql4 = mysqli_query($conn, "SELECT eso2 FROM asmwt where  pid ='$id' ");

        $row = mysqli_fetch_assoc($sql1);
        $row1= mysqli_fetch_assoc($sql2);
        $row2 = mysqli_fetch_assoc($sql3);
        $row3= mysqli_fetch_assoc($sql4);
        

        // Access the value of 1Qnr1 column from the fetched row
        $s1 = $row['pso2'];
        $s2 = $row1['eso2'];
        $s3 = $row2['pso2'];
        $s4 = $row3['eso2'];
      

        // Print or use the value obtained from SQL1
        // echo "Value from SQL1: " . $valueFromSQL1;
        // echo "Value from SQL2: " . $valueFromSQL2;
      
    
      

        // Perform additional operations based on your requirements
        
        // Assuming you want to send a success message as a response
        $response = array('status' => 'success', 's1' => $s1 , 's2' => $s2,'s3' => $s3 , 's4' => $s4);
        echo json_encode($response);
    } else {
        $response = array('status' => 'failure', 'message' => 'Score not received in the request');
        echo json_encode($response);
    }
} else {
    $response = array('status' => 'failure', 'message' => 'Invalid request method');
    echo json_encode($response);
}
?>
