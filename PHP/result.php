<?php

// Assuming you have already established a database connection

// Retrieving data from request
$conn1 = mysqli_connect("localhost", "root", "", "sam");
if ($conn1->connect_error) {
    die("Connection failed " . $conn1->connect_error);
}



$response = array();
   $name= $_POST['name'];

 
    
    // Select query to retrieve inserted data
    $select_query = "SELECT distinct * FROM docdetails WHERE name = '$name'";
    $select_result = mysqli_query($conn1, $select_query);
    
    if ($select_result) {
        $row = mysqli_fetch_assoc($select_result);
        
        // Include retrieved data in the response
        $response['data'] = $row;
    } else {
        $response['data'] = "Error retrieving inserted data: " . mysqli_error($conn1);
    }
    


// Sending response in JSON format
header('Content-Type: application/json');
echo json_encode($response);

?>
