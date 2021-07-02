<?php
	include"connect.php";
	$name = $_POST['name'];
	$price = $_POST['price'];
	$image_name = $_POST['image_name'];
	// $name = 'abc';
	// $price = "132";
	// $image_name = 'abc';
	$encoded_image = $_POST['encoded_image'];
	$upload_path = "image/$image_name.jpg";
	$upload_name = "image/$image_name.jpg";
	$sql  = "INSERT INTO food_item(name,price,image) VALUES ('$name','$price','$upload_name')";


	if(mysqli_query($conn,$sql)){
		file_put_contents($upload_path, base64_decode($encoded_image));
		$idcate = $conn->insert_id;
		echo $idcate;
		// echo json_encode(array('response'=>"image uploaded success"));
	}else{
		echo json_encode(array('response'=>"image uploaded failed"));
	}
?>