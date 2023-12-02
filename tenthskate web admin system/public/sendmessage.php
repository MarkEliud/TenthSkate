<?php
//  $host       = "localhost";
//     $user       = "root";
//     $pass       = "";
//     $database   = "skaters";

// $connect = new mysqli($host, $user, $pass, $database);
  //include_once('includes/config.php');
  $connect=mysqli_connect('localhost','blupayin_mark','AweSome2030!','blupayin_skaters');

$sender_id = $_POST['sender_id'];
$receiver_id = $_POST['receiver_id'];
$message = $_POST['message'];
$user = $_POST['user'];
//$mysqli = new mysqli('localhost', 'username', 'password', 'database_name');
if ($connect->connect_errno) {
    echo 'Failed to connect to MySQL: (' . $connect->connect_errno . ') ' . $connect->connect_error;
    exit;
} 
 $sql = "INSERT INTO messages (`sender`, `receiver`, `message`, `user`) VALUES ('$sender_id','$receiver_id','$message','$user')";
    
  if ($connect->query($sql) === TRUE) {
  echo "Records updated successfully";
   } else {
  echo "Error updating record: " . $connect->error;
    }

// $stmt = $con->prepare("INSERT INTO messages (sender, receiver, message) VALUES (?, ?, ?)");
// $stmt->bind_param('iis', $sender_id, $receiver_id, $message);
// $stmt->execute();

// $stmt->close();
// $con->close();
?>