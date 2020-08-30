<%--
    Document   : index
    Created on : 16 mars 2020, 09:34:40
    Author     : Ihagatiana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="Empcontroller-test.make">
            <input type="text" name="a" />
            <br/>
            <input type="text" name="b" />
            <br/>
            <input type="checkbox" name="check" value="1" />
            <br/>
            <input type="checkbox" name="check" value="2" />
            <br/>
            <input type="checkbox" name="check" value="3" />
            <br/>
            <input type="submit" value="valider" />
        </form>
        <a href="Empcontroller-lister.make?a=rakoto&b=776&dt=2020-12-1">Avec extension</a>

    </body>
</html>
