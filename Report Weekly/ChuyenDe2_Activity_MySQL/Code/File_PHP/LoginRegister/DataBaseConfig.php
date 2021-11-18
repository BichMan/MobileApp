<?php

class DataBaseConfig {
	public $servername;
	public $user_name;
	public $password;
	public $databasename;

	public function __construct() {

		$this->servername = 'localhost';
		$this->user_name = 'root';
		$this->password = '';
		$this->databasename = 'loginregister';

	}
}

?>
