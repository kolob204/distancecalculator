<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 26.11.2019
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>

  <!-- model put doesn't work.  work around-->
<br>
    <ol>
      <c:forEach items="${cities}" var="city">
      <li>id: ${city.id}</li>
      <p><small>name:  ${city.name}</small></p>
      </c:forEach>
    </ol>
<br>
city2   [request.setAttribute("cities2", list)]
<br>
<ol>
    <c:forEach items="${cities2}" var="city2">
        <li>id: ${city2.id}</li>
        <p><small>name:  ${city2.name}</small></p>
    </c:forEach>
</ol>

  </body>
</html>
