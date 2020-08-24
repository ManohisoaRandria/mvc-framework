<%-- 
    Document   : session
    Created on : 8 avr. 2020, 13:11:00
    Author     : P11A-MANOHISOA
--%>

<%@page import="utils.ModelView"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>session</h1>
        <% ModelView data=(ModelView)request.getAttribute("data");%>
        <% int data2=(int)data.get("sessA");%>
        
        hello: <%= data2%>
    </body>
</html>
