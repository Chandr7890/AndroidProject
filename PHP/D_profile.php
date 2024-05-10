<?php
require("conn.php");
// Include your database connection script

// Perform a database query

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    $json = file_get_contents('php://input');
    $data = json_decode($json, true);

    if(isset($_POST['P_id'])){
        $pid = $_POST['P_id'];
        $q1 = "select * from P_profile where P_id='$pid'";
        $result1 = mysqli_query($conn, $q1);
        if(mysqli_num_rows($result1) === 0)
{    $query1 = "insert into P_profile values('$pid','name','gender','phno','image')";
    mysqli_query($conn, $query1);
}
    
    
    
    $query = "SELECT * FROM P_profile where P_id='$pid'";
$result = mysqli_query($conn, $query);
    

if ($result) {
    $data = mysqli_fetch_assoc($result);

    // Close the database connection
   

    // Return data in JSON format

    

    echo json_encode($data);
} else {
    echo "Error fetching data";
}
}
}

mysqli_close($conn);
?>