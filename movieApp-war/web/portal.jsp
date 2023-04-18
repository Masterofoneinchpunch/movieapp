<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Portal</title>
        <link rel="StyleSheet" href="css/movie.css">
        <script type="text/javascript">
        </script>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h1>Portal</h1>
        <hr />
        <h2>Upcoming Birthdays:</h2>
        <table class="display" id="displayTable">
                <thead><tr><td class="display">Birthday</td><td class="display">Person</td><td class="display">Upcoming Age</td>
                </tr></thead>
            <c:forEach var="futureBirthday" items="${futureBirthdays}">
            <tr <c:if test="${futureBirthday.birthdayToday}">class="highlight"</c:if>><td class="display"><fmt:formatDate value='${futureBirthday.futureBirthday}' pattern='MM/dd/yyyy'/></td>
                    <td class="display"><c:if test="${futureBirthday.birthdayToday}"><img src="images/headbang.gif" id="imgHeadBang" title="headbang" alt="headbang" /></c:if> ${futureBirthday.person}</td><td class="display">${futureBirthday.age}</td>
                </tr>
            </c:forEach>
        </table>
        <hr />
        <h2>Fast Links:</h2>
        <h3><a href="/movieApp/control/studio.jsp?studio=Pixar">Pixar Films in Collection</a></h3>
        <h3><a href="/movieApp/control/studio.jsp?studio=Studio%20Ghibli">Studio Ghibli Films in Collection</a></h3>
        <h3><a href="/movieApp/control/genre.jsp?genre=Biblical">Biblical Films in Collection</a></h3>
        <h3><a href="/movieApp/control/genre.jsp?genre=Christmas">Christmas Films in Collection</a></h3>
        <h3><a href="/movieApp/control/genre.jsp?genre=MCU">MCU Films in Collection</a></h3>
        <h3><a href="/movieApp/control/movie/movies.jsp?country=South%20Korea">South Korean Films</a></h3>
        <h2>Media Collections:</h2>
        <h3><a href="/movieApp/control/videoCollectionType.jsp?collectionType=20th%20Century%20Fox%20Studio%20Classics">20th Century Fox Studio Classics</a></h3>
        <h3><a href="/movieApp/control/videoCollectionType.jsp?collectionType=20th%20Century%20Fox%20Film%20Noir">20th Century Fox Film Noir</a></h3>
        <h3><a href="/movieApp/control/videoCollectionType.jsp?collectionType=Criterion&labelSearch=true">Criterion</a></h3>
        <h3><a href="/movieApp/control/videoCollectionType.jsp?collectionType=Fortune%20Star&labelSearch=true">Fortune Star</a></h3>
        <h3><a href="/movieApp/control/videoCollectionType.jsp?collectionType=Tartan%20Asia%20Extreme&labelSearch=true">Tartan Asia Extreme</a></h3>
        <hr />
        <h2>Reports:</h2>
        <h3><a href="/movieApp/control/reports/yearlyMovieTotals.jsp">Yearly Movie Totals</a></h3>
    </body>
</html>
 
