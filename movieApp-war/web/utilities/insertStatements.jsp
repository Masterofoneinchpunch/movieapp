<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Video Collection</title>
        <link rel="StyleSheet" href="../css/movie.css">
        <script type="text/javascript">
        </script>
    </head>
    <body>
        <%@ include file="../header.jsp" %>
        <h1>Insert Statements</h1>
        <h2>${param.listType}</h2>
        <span class="small">
        <c:forEach var="row" items="${listType}">
            <c:out value="${row}" escapeXml="true"/><br/>
        </c:forEach>
        </span>
    </body>
</html>
 
