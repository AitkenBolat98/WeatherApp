<%--
  Created by IntelliJ IDEA.
  User: aitke
  Date: 15.02.2024
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<p><a href="registration.jsp">registration</a></p>
<p><a href="authorization.jsp">authorization</a> </p>
<p>Enter the name of the city</p>
<form method="post" action="main">
    <label for="location">
        <input id ="location" type="text" name="location" required>
    </label>
    <input type="submit" value="submit">
</form>
</body>
</html>
