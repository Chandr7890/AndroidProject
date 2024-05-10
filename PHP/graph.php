<?php
// Replace these variables with your database connection details
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "patient";

// Create a connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check the connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get the current year and month
$currentYear = date("Y");
$currentMonth = date("m");

// Calculate the starting month for the last six months
$startMonth = $currentMonth - 6;
if ($startMonth <= 0) {
    $startMonth += 12;
    $currentYear -= 1; // Adjust the year if starting month is in the previous year
}

// Construct your SQL query with the last six months, including the current and next year current month
$sql = "SELECT MONTH(date) AS month, COUNT(*) AS patients 
        FROM tempappo 
        WHERE (YEAR(date) = $currentYear AND MONTH(date) >= $startMonth)
           OR (YEAR(date) = $currentYear - 1 AND MONTH(date) >= $startMonth + 12)
           OR (YEAR(date) = $currentYear AND MONTH(date) = $currentMonth)
           OR (YEAR(date) = $currentYear + 1 AND MONTH(date) = $currentMonth)
        GROUP BY MONTH(date) 
        ORDER BY MONTH(date)";

// Execute the query
$result = $conn->query($sql);

// Initialize an array to store the results
$data = array();

// Check for errors
if (!$result) {
    die("Query failed: " . $conn->error);
}

// Check if there are any results
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $monthNumber = $row['month'];
        $monthName = date("F", mktime(0, 0, 0, $monthNumber, 1, $currentYear)); // Convert month number to month name
        $row['month'] = $monthName; // Replace the month number with month name
        $data[] = $row;
    }
}

// Encode the results as JSON
$json_data = json_encode($data);

// Output the JSON data
header('Content-Type: application/json');
echo $json_data;

// Close the database connection
$conn->close();
?>
