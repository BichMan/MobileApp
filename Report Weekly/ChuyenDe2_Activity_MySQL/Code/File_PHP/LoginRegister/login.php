<?php
require "DataBase.php";
$db = new DataBase();
//kiểm tra một biến nào đó đã được khởi tạo trong bộ nhớ của máy tính hay chưa
if (isset($_POST['user_name']) && isset($_POST['password'])) {
	if ($db->dbConnect()) {
		if ($db->logIn("users", $_POST['user_name'], $_POST['password'])) {
			echo "Login Success";
		} else {
			echo "Username or Password wrong";
		}

	} else {
		echo "Error: Database connection";
	}

} else {
	echo "All fields are required";
}

?>
