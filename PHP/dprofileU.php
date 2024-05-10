<?php
// Include the database connection file (replace with your actual database connection code)
require("conn.php");

// Check if the request is a POST request
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get the JSON data from the request body
    $json_data = file_get_contents("php://input");

    // Parse the JSON data into an associative array
    $data = json_decode($json_data, true);

    // Check if the JSON data was successfully parsed
    if ($data !== null) {
        // Extract data from the JSON
        $field1 = $data['field1'];
        $field2 = $data['field2'];
        $field3 = $data['field3'];
        $field4 = $data['field4'];

        // Perform the database update (replace with your database update code)
        $sql = "UPDATE d_profile SET D_id ='$field1', D_name = '$field2', D_dep = '$field3', D_phno = '$field4' where D_id='$field1' ";
        $stmt=mysqli_query($conn, $sql);
        echo json_encode(array("status"=> "success","message"=> "Data updated succesfully"));

        // Check if the update was successful
        // if (my> 0) {
        //     $response = array('message' => 'Data updated successfully');
        //     echo json_encode($response);
        // } else {
        //     $response = array('message' => 'Data not updated');
        //     echo json_encode($response);
        // }
    } else {
        $response = array('message' => 'Invalid JSON data');
        echo json_encode($response);
        
    }
} else {
    $response = array('message' => 'Invalid request method');
    echo json_encode($response);
}
?>
