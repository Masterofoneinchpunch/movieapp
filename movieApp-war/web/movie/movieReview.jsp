<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Movie Review - ${movie.title} (${movie.year})</title>       
        <link rel="StyleSheet" href="../css/movie.css">
   </head>
    <body>
        <%@ include file="../header.jsp" %>
        <h1>${movie.title} (${movie.year})</h1>
        <h2>My Movie Review</h2>
        <p>${movie.review}</p>
    </body>
</html>
