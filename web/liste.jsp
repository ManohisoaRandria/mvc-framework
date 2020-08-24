<%-- 
    Document   : liste.jsp
    Created on : 19 mars 2020, 14:32:38
    Author     : Ihagatiana
--%>

<%@page import="utils.ModelView"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Liste</h1>
        <% ModelView data=(ModelView)request.getAttribute("data");%>
        <% String[]data2=(String[])data.get("emp");String a=(String)data.get("a");%>
        <%= data2[0]%>
        <%= a%>
        
           <a href="Empcontroller-getSession.make">session</a>
    </body>
</html>
