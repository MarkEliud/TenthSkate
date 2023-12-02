<?php 
//    $host       = "localhost";
//     $user       = "root";
//     $pass       = "";
//     $database   = "skaters";

// $connect = new mysqli($host, $user, $pass, $database);
  //include_once('includes/config.php');
  $connect=mysqli_connect('localhost','blupayin_mark','AweSome2030!','blupayin_skaters');

    $status = $_GET['status'];

if($status=="Driver"){
  $stmt = $connect->prepare("SELECT sender FROM messages WHERE user = 'Customer' AND receiver = 'Driver';");
$stmt->execute();
 $stmt->bind_result($sender);
 $products = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 //$temp['id'] = $id; 
 $temp['sender'] = $sender; 
 //$temp['email'] = $email; 

 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
}
if($status=="Inventory Manager"){
  $stmt = $connect->prepare("SELECT sender FROM messages WHERE user = 'Customer' AND receiver = 'Inventory Manager';");
$stmt->execute();
 $stmt->bind_result($sender);
 $products = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 //$temp['id'] = $id; 
 $temp['sender'] = $sender; 
 //$temp['email'] = $email; 

 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
}
if($status=="Finance"){
  $stmt = $connect->prepare("SELECT sender FROM messages WHERE user = 'Customer' AND receiver = 'Finance';");
$stmt->execute();
 $stmt->bind_result($sender);
 $products = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 //$temp['id'] = $id; 
 $temp['sender'] = $sender; 
 //$temp['email'] = $email; 

 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
}
if($status=="Shipment Manager"){
  $stmt = $connect->prepare("SELECT sender FROM messages WHERE user = 'Customer' AND receiver = 'Shipment Manager';");
$stmt->execute();
 $stmt->bind_result($sender);
 $products = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 //$temp['id'] = $id; 
 $temp['sender'] = $sender; 
 //$temp['email'] = $email; 

 array_push($products, $temp);
 }
 
 //displaying the result in json format 
 echo json_encode($products);
}
// if($status=="Pilot"){
//   $stmt = $con->prepare("SELECT sender FROM messages WHERE user = 'Customer' AND receiver = 'Pilot';");
// $stmt->execute();
//  $stmt->bind_result($sender);
//  $products = array(); 
 
//  //traversing through all the result 
//  while($stmt->fetch()){
//  $temp = array();
//  //$temp['id'] = $id; 
//  $temp['sender'] = $sender; 
//  //$temp['email'] = $email; 

//  array_push($products, $temp);
//  }
 
 //displaying the result in json format 
//  echo json_encode($products);
// }