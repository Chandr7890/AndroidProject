<?php
require("conn.php");
// Include your database connection script

// Perform a database query

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    $json = file_get_contents('php://input');
    $data = json_decode($json, true);

    if(isset($_POST['P_id'])){
        $pid = $_POST['P_id'];
        $q1 = "select * from d_profile where D_id='$pid'";
        $result1 = mysqli_query($conn, $q1);
        if(mysqli_num_rows($result1) === 0)
{    $query1 = "insert into d_profile values('$pid','name','dep','phno')";
    mysqli_query($conn, $query1);
}
    
    
    
    $query = "SELECT * FROM d_profile where D_id='$pid'";
$result = mysqli_query($conn, $query);
    

if ($result) {
    $data = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $data = $row;
    }
    

    echo json_encode($data);
} else {
    echo "Error fetching data";
}
}
}

mysqli_close($conn);
?>