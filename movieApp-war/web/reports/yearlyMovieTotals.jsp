<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Yearly Movie Totals</title>
        <link rel="StyleSheet" href="../css/movie.css">
        <script type="text/javascript">
        </script>
    </head>
    <body>
        <%@ include file="../header.jsp" %>
        <h1>Yearly Movie Totals (all movies)</h1>
        <table class="display" id="displayTable">
            <thead><tr><td class="display">Year</td><td class="display">Total Count</td><td class="display">Watched Count</td></tr></thead>
        <c:forEach var="movieList" items="${movieList}">
            <tr><td class="display"><a href="/movieApp/control/movie/movies.jsp?year=${movieList.year}">${movieList.year}</a></td><td class="display">${movieList.count}</td><td class="display">${movieList.watchedCount}</td></tr>
        </c:forEach>
        </table>
    </body>
</html>
