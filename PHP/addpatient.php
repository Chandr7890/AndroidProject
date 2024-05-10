<?php
require("conn.php");
if($_SERVER["REQUEST_METHOD"] == "POST" ) {
    // Decode the JSON data
    $json = file_get_contents('php://input');//data1["pid"];
    $data1 = json_decode($json, true);
    if(isset($_POST["pid"]) && isset($_POST["password"])){
        $pid = $_POST["pid"];
        $password = $_POST["password"];
        $name = $_POST["name"];
        $age = $_POST["age"];
        $sex = $_POST["sex"];
        $cause = $_POST["cause"];
        $sql = "insert into login values('$pid','$password')";
        // $sql1 = "INSERT INTO patient_info(pid, name, age, sex, cause) VALUES ('$pid', '$name', '$age', '$sex', '$cause')";

        
       
     $sql1 = "insert into patient_info(pid,name,age,sex,cause) values('$pid','$name','$age','$sex','$cause')";
    
     $q1 = "select * from P_profile where P_id='$pid'";
        $result1 = mysqli_query($conn, $q1);
        if(mysqli_num_rows($result1) === 0)
{    $query1 = "insert into P_profile values('$pid','name','gender','phno','image')";
    mysqli_query($conn, $query1);
}
    
    
   mysqli_query($conn,"insert into st values('$pid','','')");
    mysqli_query($conn,"INSERT INTO bsmwt(pid, pbp, phr, pso2, pdys, pfat, ebp, ehr, eso2, edys, efat, t1, t2) 
VALUES ('$pid', '', '', '', '', '', '', '', '', '', '', '', '')");
 mysqli_query($conn,"INSERT INTO asmwt(pid, pbp, phr, pso2, pdys, pfat, ebp, ehr, eso2, edys, efat, t1, t2) 
 VALUES ('$pid', '', '', '', '', '', '', '', '', '', '', '', '')");

        if(mysqli_query($conn, $sql) && mysqli_query($conn,$sql1)){
            $response = array('status' => 'success', 'message' => 'data inserted succesfully');
          echo json_encode($response);
    }
    else{
        $response = array('status'=> 'fail','message'=>"data not intrested");
    }
    
    }
}

?>