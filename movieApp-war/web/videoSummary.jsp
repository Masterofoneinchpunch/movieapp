<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Video Summary - ${video.title}</title>
        <link rel="StyleSheet" href="css/movie.css">
    <style>
    .tooltip {
      position: relative;
      display: inline-block;
      padding: 3px;
    }

    .tooltip .tooltiptext {
      visibility: hidden;
      width: 120px;
      background-color: #003366;
      color: #fff;
      text-align: center;
      border-radius: 6px;
      padding: 5px 0;
      position: absolute;
      z-index: 1;
      top: 150%;
      left: 50%;
      margin-left: -60px;
    }

    .tooltip .tooltiptext::after {
      content: " ";
      position: absolute;
      bottom: 100%;  /* At the top of the tooltip */
      left: 50%;
      margin-left: -5px;
      border-width: 5px;
      border-style: solid;
      border-color: transparent transparent black transparent;
    }    
    
    .tooltip:hover .tooltiptext {
      visibility: visible;
    }
    </style>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h1>${video.title} <span class="edit center"><a href="/movieApp/control/video.jsp?videoId=${video.videoId}">(Edit)</a></span></h1>
        <table>
            <c:if test="${not empty video.currentlyLentTo}">
                <tr class="warningLabel"><td>Not Available:</td><td>Borrowed by ${video.currentlyLentTo}</td></tr>
            </c:if>
            <tr><td class="entryLabel">Label:</td><td class="displayText">${video.label} <span class="warning">${video.spineNumber}</span></td></tr>
            <tr><td class="entryLabel">Video Type:</td><td class="displayText">${video.videoType}</td></tr>
            <tr><td class="entryLabel">Case Type:</td><td class="displayText">${video.caseType}</td></tr>
            <tr><td class="entryLabel">Year of ${video.videoType} Release:</td><td class="displayText">${video.yearOfRelease}</td></tr>
            <tr><td class="entryLabel">Region:</td><td class="displayText">${video.region}</td></tr>
            <tr><td class="entryLabel">Slipcover:</td><td class="displayText"><c:choose><c:when test="${video.slipcoverYn == true}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td></tr>
            <tr><td class="entryLabel">OOP:</td><td class="displayText"><c:choose><c:when test="${video.oopYn == true}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td></tr>
            <c:if test="${not empty video.obtainedFrom}">
                <tr><td class="entryLabel">Obtained From: </td><td class="displayText">${video.obtainedFrom}<c:if test="${not empty video.cost}"> for <fmt:formatNumber value='${video.cost}' type='currency' /></c:if></td></tr>   
            </c:if>
            <tr><td class="entryLabel">Location:</td><td class="displayText">${video.location}</td></tr>
        </table>

        <c:if test="${not empty video.videoMovies}">
            <div class="label">Movie(s):</div>
            <table class="display" id="displayTable">
                    <thead><tr><td class="display">Title</td><td class="display">Year</td><td class="display">Runtime</td><td class="display">Country</td><td class="display">Language</td><td class="display">MPAA Rating</td><td class="display">My Rating</td><td class="display">Last Watched</td>
                        <c:if test="${video.hasVideoMisc == true}"><td class="display">Miscellaneous</td></c:if>
                        <c:if test="${video.hasCommentary == true}"><td class="display">Commentary(s)</td></c:if>
                    </tr></thead>
                <c:forEach var="movie" items="${video.videoMovies}">
                <tr><td class="display"><c:if test="${not empty movie.review}"><a href="/movieApp/control/movie/movieReview.jsp?actionType=Load&movieId=${movie.movieId}" target="_blank"></c:if><span title = "movieId: ${movie.movieId}">${movie.title}</span><c:if test="${not empty movie.review}"></a></c:if></td><td class="display">${movie.year}</td><td class="display">${movie.runtime}</td><td class="display">${movie.country}</td><td class="display">${movie.language}</td><td class="display">${movie.mpaaRating}</td><td class="display">${movie.myRating}</td>
                    <td class="display">
                        <fmt:formatDate value='${movie.lastWatched}' pattern='MM/dd/yyyy'/> 
                        <c:if test="${not empty movie.watchHistory}">
                            <span class="buttonRelativeSize tooltip" onClick="" >Previous Watches
                                <span class="tooltiptext">
                                    <c:forEach var="movieWatch" items="${movie.watchHistory}"><span><fmt:formatDate value='${movieWatch}' pattern='MM/dd/yyyy'/></span><br /></c:forEach>
                                </span>
                            </span>
                        </c:if>
                    </td>
                        <c:if test="${video.hasVideoMisc == true}">
                        <td class="display"><c:forEach var="videoMisc" items="${movie.videoMisc}"><span <c:if test="${videoMisc.warningYn == true}">class="warning"</c:if>>${videoMisc.videoMisc}</span><br /></c:forEach></td>
                        </c:if>
                        <c:if test="${video.hasCommentary == true}">
                        <td class="display"><c:forEach var="commentary" varStatus="status" items="${movie.commentaries}"><c:if test="${!(status.first == true && status.last == true)}">${status.count})</c:if> ${commentary}<br /></c:forEach></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${not empty video.videoBoxSet}">
            <div class="label"></div>
            <div>
            <fieldset>
                <legend><b>Box Set Contains:</b></legend>
                <ul>
                <c:forEach var="boxSetList" items="${video.videoBoxSet}">
                    <li><a href="/movieApp/control/videoSummary.jsp?videoId=${boxSetList.videoId}">${boxSetList.videoTitle}</a></li>
                </c:forEach>
                </ul>
            </fieldset>
            </div>
        </c:if>
        <c:if test="${not empty video.videoExtras}">
            <div class="label">Extra(s):</div>
            <c:forEach var="videoExtra" items="${video.videoExtras}">
                ${videoExtra.title} <span class="description">${videoExtra.description}</span><br />
            </c:forEach>
        </c:if>
        <c:if test="${not empty video.videoTrailers}">
            <div class="label">Trailer(s):</div>
            <c:forEach var="trailer" items="${video.videoTrailers}">
                ${trailer.embedCode}<br />
            </c:forEach>
        </c:if>
        <c:if test="${not empty video.comments}">
            <div class="label">Comments:</div>${video.comments}<br />
        </c:if>                            
        <c:if test="${video.partOfCollection == true}">
            <br />
            <div>
            <fieldset>
                <legend><b>Part of:</b></legend>
                <ul>
                    <c:if test="${video.partOfCriterion == true}"><li><a href="/movieApp/control/videoCollectionType.jsp?collectionType=Criterion&labelSearch=true">Criterion</a></li></c:if>
                </ul>
                <c:forEach var="collection" items="${video.videoCollections}">
                    <a href="/movieApp/control/videoCollectionType.jsp?collectionType=${collection}">${collection}</a>
                </c:forEach>    
            </fieldset>
            </div>
        </c:if>    
    </body>
</html>
 
