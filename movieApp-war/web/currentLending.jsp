<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Currently Lending</title>
        <link rel="StyleSheet" href="css/movie.css">
        <script type="text/javascript">
        </script>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h1>Currently Lending</h1>
        <table class="display">
            <thead><tr><td class="editable">Video</td><td class="editable">Borrower</td><td class="editable">Lend Date</td></tr></thead>
        <c:forEach var="lent" items="${currentLending}">
            <tr><td class="display"><a href="/movieApp/control/videoSummary.jsp?videoId=${lent.videoId}">${lent.title}</a></td><td class="display"><a href="/movieApp/control/lendingHistory.jsp?lentTo=${lent.lentTo}">${lent.lentTo}</a></td><td class="display"><fmt:formatDate value='${lent.lendingDate}' pattern='MM/dd/yyyy'/></td></tr>
        </c:forEach>
        </table>
    </body>
</html>
 
