<?php

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Form</title>
</head>

<body>
<fieldset>
<legend>Form</legend>
<form name="frmContact" method="post" action="contact.php">
<p>
<label for="name">Name </label>
<input type="text" name="name" id="name">
</p>
<p>
<label for="user">User</label>
<input type="text" name="user" id="user">
</p>
<p>
<label for="email">Email</label>
<input type="text" name="email" id="email">
</p>
<p>
<label for="password">password</label>
<input type="text" name="password" id="password">
</p>
<p>
<label for="status">Status</label>
<input type="text" name="status" id="status">
</p>
<p>&nbsp;</p>
<p>
<input type="submit" name="Submit" id="Submit" value="Submit">
</p>
</form>
</fieldset>
</body>
</html>
?>