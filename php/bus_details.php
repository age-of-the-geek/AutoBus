<?php

include 'conn.php';
$con = OpenCon();

if ($_SERVER['REQUEST_METHOD'] =='POST'){

    $number = $_POST['bus_number'];
    $total_seats = $_POST['total_seats'];
    $available_seats = $_POST['available_seats'];
    $bus_route = $_POST['bus_route'];
    $bus_leaving_time = $_POST['bus_leaving_time'];
    $bus_reaching_time = $_POST['bus_reaching_time'];
    $bus_driver_name = $_POST['bus_driver_name'];
    $bus_ticketchecker_name = $_POST['bus_ticketchecker_name'];
    $bus_rating = $_POST['bus_rating'];
    $bus_break_time = $_POST['bus_break_time'];
    $bus_company = $_POST['bus_company'];

    

    

    $sql = "INSERT INTO bus_detail (bus_number, bus_total_seats, bus_available_seats,
    bus_route,bus_leaving_time,bus_reaching_time,bus_driver_name,bus_ticketchecker_name,
    bus_rating,bus_break_time,bus_company) 
    VALUES ('$number', '$total_seats', '$available_seats' ,'$bus_route','$bus_leaving_time','$bus_reaching_time'
    ,'$bus_driver_name','$bus_ticketchecker_name','$bus_rating','$bus_break_time','$bus_company')";

    if ( mysqli_query($con, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($con);

    } else {

        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
        CloseCon($con);
    }
}

?>