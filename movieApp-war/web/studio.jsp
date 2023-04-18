<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Studio</title>
        <link rel="StyleSheet" href="css/movie.css">
        <script type="text/javascript">
        </script>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h1>Collection for ${param.studio}</h1>
        <table>
        <c:forEach var="videoList" items="${videoList}">
            <tr>
                <td width="300"><a href="/movieApp/control/videoSummary.jsp?videoId=${videoList.videoId}">${videoList.title}</a></td>
                <td>${videoList.videoType}</td>
                <td>(${videoList.label})</td><td class="edit"><a href="/movieApp/control/video.jsp?videoId=${videoList.videoId}">Edit</a></td>
            </tr>
        </c:forEach>
        </table>
        <h1>Total: ${videoCount}</h1>
    </body>
</html>
