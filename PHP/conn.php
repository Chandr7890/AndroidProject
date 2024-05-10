<?php
$conn=mysqli_connect("localhost","root","","patient");
if($conn->connect_error){
    die("Connection failed ".$conn->connect_error);
}
?>