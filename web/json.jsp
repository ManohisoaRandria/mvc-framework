<%--
    Document   : json
    Created on : Sep 15, 2020, 2:46:05 PM
    Author     : manohisoa
--%>

<%@page import="controller.Personne"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1> <% String daPersonneta = (String) request.getAttribute("data");%>
            <%= daPersonneta%>
        </h1>
    </body>
</html>
