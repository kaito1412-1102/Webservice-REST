<?php  
	include"connect.php";
	$mangmonan = array();
	$query ="SELECT * FROM food_item";
	$data = mysqli_query($conn,$query);

	while ($row = mysqli_fetch_assoc($data)) {

		array_push($mangmonan, $row);
	}
	echo json_encode($mangmonan);
	// 

?>