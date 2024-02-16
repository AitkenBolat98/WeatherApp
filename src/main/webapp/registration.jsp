<%--
  Created by IntelliJ IDEA.
  User: aitke
  Date: 16.02.2024
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Registration</h1>
<form method="post" action="authorization">
    <label for="login">Enter login
        <input id="login" type="text" name="login" required>
    </label>
    <label for="password">Enter password
    <input id="password" name="password" required>
    </label>
    <input type="submit" value="submit">
</form>
</body>
</html>
