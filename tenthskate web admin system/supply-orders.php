<?php
  include_once('includes/config.php');



$status= $_POST["status"];




if($connect){
       

  if($status==1){
    $description = $_POST["description"];
    $price = $_POST["price"];
    $sql = "UPDATE tbl_supply SET status='1', price='$price' WHERE description='$description'";
 //$sql_register = "INSERT INTO tbl_supply (`category`, `description`, `name`, `price`,`qprice`,  `status`)
 // VALUES ('$category','$description','$name','$price','$qprice','$status')";
if(mysqli_query($connect,$sql)){
echo "successfully";
}
else{
echo "Failed";
}
}

else{

    $category = $_POST["category"];
   $description = $_POST["description"];
    $name = $_POST["name"];
  $quantity = $_POST["quantity"];
   $sql_register = "INSERT INTO tbl_supply (`category`, `description`, `name`, `quantity`, `status`)
  VALUES ('$category','$description','$name','$quantity','$status')";
if(mysqli_query($connect,$sql_register)){
echo "successfully";
}
else{
echo "Failed";
}
}
}
else{
echo "Connection Error";
}
?>