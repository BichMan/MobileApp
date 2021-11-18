<?php
require "DataBaseConfig.php";

class DataBase {
	public $connect;
	public $data;
	private $sql;
	protected $servername;
	protected $user_name;
	protected $password;
	protected $databasename;

	public function __construct() {
		$this->connect = null;
		$this->data = null;
		$this->sql = null;
		$dbc = new DataBaseConfig();
		$this->servername = $dbc->servername;
		$this->user_name = $dbc->user_name;
		$this->password = $dbc->password;
		$this->databasename = $dbc->databasename;
	}

	function dbConnect() {
		//hàm kết nối tới MySQL server
		$this->connect = mysqli_connect($this->servername, $this->user_name, $this->password, $this->databasename);
		return $this->connect;
	}

	function prepareData($data) {
		//mysqli_real_escape_string() tránh các ký tự đặc biệt trong một chuỗi để sử dụng câu lệnh SQL
		//Hàm stripslashes() sẽ loại bỏ các dấu backslashes ( \ ) có trong chuỗi. ( \ ' sẽ trở thành ' , \\ sẽ trở thành \)
		//Hàm htmlspecialchars() sẽ chuyển đổi các ký tự đặc biệt thành HTML entities. Điều đó có nghĩa là nó sẽ thay thế các ký tự như < và > thành &lt và &gt.
		return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
	}

	function logIn($table, $user_name, $password) {
		$user_name = $this->prepareData($user_name); //chuẩn bị dữ liệu
		$password = $this->prepareData($password);
		$this->sql = "select * from " . $table . " where user_name = '" . $user_name . "'";
		$result = mysqli_query($this->connect, $this->sql); //thực hiện câu truy vấn
		$row = mysqli_fetch_assoc($result); //Trả về một dòng kết quả của một truy vấn MySQL nào đó dưới dạng một mảng kết hợp.
		//trả về số lượng hàng trong một tập kết quả.
		if (mysqli_num_rows($result) != 0) {
			$dbuser_name = $row['user_name'];
			$dbpassword = $row['password'];
			if ($dbuser_name == $user_name && password_verify($password, $dbpassword)) {
				$login = true;
			} else {
				$login = false;
			}

		} else {
			$login = false;
		}

		return $login;
	}

	function signUp($table, $fullname, $email, $user_name, $password) {
		$fullname = $this->prepareData($fullname);
		$user_name = $this->prepareData($user_name);
		$password = $this->prepareData($password);
		$email = $this->prepareData($email);
		$password = password_hash($password, PASSWORD_DEFAULT);
		$this->sql =
			"INSERT INTO " . $table . " (fullname, user_name, password, email) VALUES ('" . $fullname . "','" . $user_name . "','" . $password . "','" . $email . "')";
		if (mysqli_query($this->connect, $this->sql)) {
			return true;
		} else {
			return false;
		}

	}

}

?>
