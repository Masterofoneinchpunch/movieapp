<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Utilities</title>
        <link rel="StyleSheet" href="../css/movie.css">
        <script type="text/javascript">
        </script>
    </head>
    <body>
        <%@ include file="../header.jsp" %>
        <h1>Utilities</h1>
        <h2>Insert Statements</h2>
        <ul>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=ALL">All</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=BOOK">Book</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=COLLECTION">Collection</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=LENDINGHISTORY">Lending History</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=GENRE">Genre</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=MOVIE">Movie</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=MOVIEGENRE">Movie Genre</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=MOVIEREVIEW">Movie Review</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=MOVIESTUDIO">Movie Studio</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=MOVIEWATCHHISTORY">Movie Watch History</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=PERSON">Person</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=STUDIO">Studio</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=TRAILER">Trailer</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=VIDEO">Video</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=VIDEOBOXSET">Video Box Set</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=VIDEOCOLLECTION">Video Collection</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=VIDEOCOMMENTARY">Video Commentary</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=VIDEOEXTRA">Video Extra</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=VIDEOMISC">Video Misc</a></li>
            <li><a href="/movieApp/control/utilities/insertStatements.jsp?listType=VIDEOMOVIE">Video Movie</a></li>
        </ul>
    </body>
</html>
 
