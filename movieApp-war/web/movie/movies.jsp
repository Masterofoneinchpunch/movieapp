<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movies: ${title}</title>
        <link rel="StyleSheet" href="../css/movie.css">
    </head>
    <body>
        <%@ include file="../header.jsp" %>
        <h1>Movies: ${title}</h1>
        <table class="display" id="displayTable">
                <thead><tr><td class="display">Title</td><td class="display">Year</td><td class="display">Runtime</td><td class="display">Country</td><td class="display">Language</td><td class="display">MPAA Rating</td><td class="display">My Rating</td><td class="display">Last Watched</td>
                </tr></thead>
            <c:forEach var="movie" items="${movies}" varStatus="movieStatus">
                <tr><td class="display"><span title = "movieId: ${movie.movieId}"><c:if test="${not empty movie.review}"><a href="/movieApp/control/movie/movieReview.jsp?actionType=Load&movieId=${movie.movieId}" target="_blank"></c:if>${movie.title}<c:if test="${not empty movie.review}"></a></span></c:if></td><td class="display">${movie.year}</td><td class="display">${movie.runtime}</td><td class="display">${movie.country}</td><td class="display">${movie.language}</td><td class="display">${movie.mpaaRating}</td><td class="display">${movie.myRating}</td><td class="display"><fmt:formatDate value='${movie.lastWatched}' pattern='MM/dd/yyyy'/></td>
                </tr>
            </c:forEach>
        </table>
        <h1>Total: ${movieCount}</h1>
    </body>
</html>
