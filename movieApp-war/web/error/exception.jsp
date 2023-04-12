<%@page isErrorPage="true" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exception page</title>
        <link rel="StyleSheet" href="../css/movie.css">
        <script type="text/javascript">
        </script>
    </head>
    <body>
        <% if (exception != null) { %>
        <%@ include file="../header.jsp" %>
        <h1>Exception</h1>
        <p class="label">Message:</p>
        <blockquote>
           <%= exception.getMessage() %>
        </blockquote>

        <p class="label">Here is the stack trace:</p>
        <blockquote>
        <% exception.printStackTrace(new java.io.PrintWriter(pageContext.getOut())); %>
        <% } %>
        </blockquote>
    </body>
</html>
