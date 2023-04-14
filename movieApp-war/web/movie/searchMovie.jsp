<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Movie</title>       
        <link rel="StyleSheet" href="../css/movie.css">
        
        <script type="text/javascript">
        function search() {
            searchMovie.actionType.value = "Search";
            if (validateForm()) {
                searchMovie.submit();
            } else {
                message.innerHTML = "Please correct data entry errors.";
            }          
        }
        
        function closeSelf() {
            window.close();
        }

        function resetForm() {
        }    

        function validateForm() {
            resetForm();
            
            var validForm = true;                        
            return validForm;
        }         
        </script>
   </head>
    <body>
        <%@ include file="../header.jsp" %>
        <h1>Search Movie <span id="message" class="warningLabel center">${message}</span></h1>
        <form name="searchMovie" action="${pageContext.request.contextPath}/control/movie/searchMovie.jsp" method="post" onsubmit="search();">
        <input type="hidden" name="actionType" />    
        <table>
            <tr><td class="entryLabel">Movie Title</td><td class="validationimage"><img class="balance" src="../images/Required.gif" id="imgTitle" title="Required" alt="Required" /></td><td><input autofocus type="text" name="title" maxLength="100" value="${param.title}" class="entryTextLong" tabindex="1" onchange="isModified('title');" /><span id="titleModified" class="warning"></span></td></tr>
        </table>
        
        <c:if test="${not empty movies}">
            <br/>
            <table class="display" id="displayTable">
                    <thead><tr><td class="display">Title</td><td class="display">Year</td><td class="display">Runtime</td><td class="display">Country</td><td class="display">Language</td><td class="display">MPAA Rating</td><td class="display">My Rating</td><td class="display">Last Watched</td>
                    </tr></thead>
                <c:forEach var="movie" items="${movies}" varStatus="movieStatus">
                    <tr><td class="display"><span title = "movieId: ${movie.movieId}"><c:if test="${not empty movie.review}"><a href="/movieApp/control/movie/movieReview.jsp?actionType=Load&movieId=${movie.movieId}" target="_blank"></c:if>${movie.title}<c:if test="${not empty movie.review}"></a></span></c:if></td><td class="display">${movie.year}</td><td class="display">${movie.runtime}</td><td class="display">${movie.country}</td><td class="display">${movie.language}</td><td class="display">${movie.mpaaRating}</td><td class="display">${movie.myRating}</td><td class="display"><fmt:formatDate value='${movie.lastWatched}' pattern='MM/dd/yyyy'/></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <h1>Total: ${movieCount}</h1>
        
        <input type="button" tabindex="2" class="button" value="Search Movie" onClick="search();"/>
        <input type="button" tabindex="3" class="button" value="Close" onClick="closeSelf();"/>
        </form>
    </body>
</html>

