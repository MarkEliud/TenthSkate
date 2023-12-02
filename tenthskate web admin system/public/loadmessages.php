<?php
//  $host       = "localhost";
//     $user       = "root";
//     $pass       = "";
//     $database   = "skaters";

// $connect = new mysqli($host, $user, $pass, $database);
  //  include_once('includes/config.php');
  $connect=mysqli_connect('localhost','blupayin_mark','AweSome2030!','blupayin_skaters');

$sender_id = $_GET['sender_id'];
$receiver_id = $_GET['receiver_id'];

//$mysqli = new mysqli('localhost', 'username', 'password', 'database_name');
if ($connect->connect_errno) {
    echo 'Failed to connect to MySQL: (' . $connect->connect_errno . ') ' . $connect->connect_error;
    exit;
}

$result = $connect->query("SELECT * FROM messages WHERE (sender='$sender_id' AND receiver='$receiver_id') OR (sender='$receiver_id' AND receiver='$sender_id')");

$rows = array();
while ($row = $result->fetch_assoc()) {
    $rows[] = $row;
}
echo json_encode($rows);

$result->free();
$connect->close();
?>