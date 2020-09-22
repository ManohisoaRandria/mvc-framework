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
        <a href="Empcontroller-testQueryParam.make">query P</a>
        <h3>File Upload:</h3>
        Select a file to upload: <br />
        <form action = "Empcontroller-upload.make" method = "post" enctype="multipart/form-data">
            <input type = "text" name = "nomImage" /><br/>
            <input type = "number" name = "nb" value="1" /><br/>
            <input type = "file" name = "file" />
            <input type = "checkbox" name = "bla" value="1" />
            <input type = "checkbox" name = "bla" value="2"/>
            <br />
            <input type = "submit" value = "Upload File" />
        </form>
    </body>
</html>
