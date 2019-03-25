<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<form action="/loginUser" method="post">
	<input type="text" name="username" />
	<input type="password" name="password" />
	<input type="submit" value="提交" />
</form>
</body>
</html>