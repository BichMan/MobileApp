<?php
require "DataBase.php";
$db = new DataBase();

//kiểm tra một biến nào đó đã được khởi tạo trong bộ nhớ của máy tính hay chưa

if (isset($_POST['fullname']) && isset($_POST['email']) && isset($_POST['user_name']) && isset($_POST['password'])) {
	if ($db->dbConnect()) {
		if ($db->signUp("users", $_POST['fullname'], $_POST['email'], $_POST['user_name'], $_POST['password'])) {
			echo "Sign Up Success";
		} else {
			echo "Sign up Failed";
		}

	} else {
		echo "Error: Database connection";
	}

} else {
	echo "All fields are required";
}

?>
