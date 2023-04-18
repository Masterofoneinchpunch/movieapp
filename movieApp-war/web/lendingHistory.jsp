<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lending History</title>
        <link rel="StyleSheet" href="css/movie.css">
        <script type="text/javascript">
        </script>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h1>Lending History for ${param.lentTo}</h1>
        <c:if test="${not empty personLentTo}">
            <div>${personLentTo.frontPicEmbed}</div>
            <div><span class="entryLabel">Birthday:</span> <fmt:formatDate value='${personLentTo.birthDate}' pattern='MMMMM d'/></div>
            <br />
        </c:if>
        <table class="display">
            <thead><tr><td class="editable">Video</td><td class="editable">Lend Date</td><td class="editable">Return Date</td></tr></thead>
        <c:forEach var="lent" items="${lendingHistory}">
            <tr><td class="display"><a href="/movieApp/control/videoSummary.jsp?videoId=${lent.videoId}">${lent.title}</a></td><td class="display"><fmt:formatDate value='${lent.lendingDate}' pattern='MM/dd/yyyy'/><c:if test="${empty lent.lendingDate}">UNKNOWN</c:if></td>
                <td class="display"><fmt:formatDate value='${lent.returnDate}' pattern='MM/dd/yyyy'/><c:if test="${empty lent.returnDate && lent.returnedYn == true}">UNKNOWN</c:if></td></tr>
        </c:forEach>
        </table>
    </body>
</html>
 
