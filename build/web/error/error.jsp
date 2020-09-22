<%--
    Document   : error
    Created on : Sep 15, 2020, 1:52:04 PM
    Author     : manohisoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Error : <%= request.getParameter("err")%></h1>
    </body>
</html>
