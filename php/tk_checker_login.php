<?php 

include 'conn.php';
$con = OpenCon();
 


 if($_SERVER['REQUEST_METHOD']=='POST'){
 //Getting values 
 $username = $_POST['email'];
 $password = $_POST['password'];
 
 //Creating sql query
 $sql = "SELECT * FROM tk_checker_account WHERE tk_checker_uname='$username' AND tk_checker_password='$password';";
 
 //importing dbConnect.php script 
 
 
 //executing query
 $result = mysqli_query($con,$sql);
 
 //fetching result
 $check = mysqli_fetch_array($result);
 
 //if we got some result 
 if(isset($check)){
 //displaying success 
 echo "success";
 }else{
 //displaying failure
 echo "failure";
 }
 CloseCon($con);
 }

 ?>