<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="webSup" uri="/WEB-INF/tlds/webSupport.tld" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Video Collection Entry - <c:if test="${empty video.title}">New</c:if>${video.title}</title>
        <link rel="StyleSheet" href="css/movie.css">
        <script language="JavaScript" type="text/javascript" src="js/dynCreation.js"></script>
        <script language="JavaScript" type="text/javascript" src="js/ajax.js"></script>
        <script language="JavaScript" type="text/javascript" src="js/validation.js"></script>
        <script type="text/javascript">
                
        var originalData;
        <c:if test="${not empty video.json}">originalData = ${video.json};</c:if>
        
        function isModified(attribute) {
            if (originalData) {
                var modified = document.getElementById(attribute + "Modified");
                if (originalData[attribute] !== videoCollection[attribute].value) {
                    modified.innerHTML = "*";
                } else{
                    modified.innerHTML = "";
                }                       
            }
        }

        function edit(movieId) {           
            var movieTitleTd = document.getElementById("tdMovieTitle_" + movieId);           
            var movieTitleText = createTextInput("movieTitle_" + movieId, 100, "entryLabel");
            movieTitleText.value = movieTitleTd.textContent;
            movieTitleTd.replaceChild(movieTitleText, movieTitleTd.childNodes[0]);
        
            var yearTd = document.getElementById("tdYear_" + movieId);           
            var movieYearText = createTextInput("movieYear_" + movieId, 4, "entryTextYear");
            movieYearText.value = yearTd.textContent;
            if (yearTd.textContent) {
                movieYearText.value = yearTd.textContent; 
                yearTd.replaceChild(movieYearText, yearTd.childNodes[0]);
            } else {
                yearTd.appendChild(movieYearText);
            }                         
            
            var runTimeTd = document.getElementById("tdRuntime_" + movieId);
            var runtimeText = createTextInput("runtime_" + movieId, 4, "entryTextYear");
            if (runTimeTd.textContent) {
                runtimeText.value = runTimeTd.textContent;
                runTimeTd.replaceChild(runtimeText, runTimeTd.childNodes[0]);
            } else {
                runTimeTd.appendChild(runtimeText);
            }
            
            var countryTd = document.getElementById("tdCountry_" + movieId);
            var countrySelect = createSelect("country_" + movieId, "");
            <webSup:javaScriptOptions name="countrySelect" lookup="COUNTRY" />        
            if (countryTd.textContent) {
                countrySelect.value = countryTd.textContent;
                countryTd.replaceChild(countrySelect, countryTd.childNodes[0]);
            } else {
                countryTd.appendChild(countrySelect);
            }                         
        
            var tdLanguage = document.getElementById("tdLanguage_" + movieId);
            var languageSelect = createSelect("language_" + movieId, "");
            <webSup:javaScriptOptions name="languageSelect" lookup="LANGUAGE" />
            if (tdLanguage.textContent) {
                languageSelect.value = tdLanguage.textContent;
                tdLanguage.replaceChild(languageSelect, tdLanguage.childNodes[0]);
            } else {
                tdLanguage.appendChild(languageSelect);
            }                         
            
            var tdMpaaRating = document.getElementById("tdMpaaRating_" + movieId);
            var mpaaRatingSelect = createSelect("mpaaRating_" + movieId, "");
            <webSup:javaScriptOptions name="mpaaRatingSelect" lookup="MPAARATING" />
            if (tdMpaaRating.textContent) {
                mpaaRatingSelect.value = tdMpaaRating.textContent;
                tdMpaaRating.replaceChild(mpaaRatingSelect, tdMpaaRating.childNodes[0]);
            } else {
                tdMpaaRating.appendChild(mpaaRatingSelect);
            }
            
            //tdMyRating_$
            var tdMyRating = document.getElementById("tdMyRating_" + movieId);
            var myRatingSelect = createSelect("myRating_" + movieId, "");
            <webSup:javaScriptOptions name="myRatingSelect" lookup="MYRATING" />
            if (tdMyRating.textContent) {
                myRatingSelect.value = tdMyRating.textContent;
                tdMyRating.replaceChild(myRatingSelect, tdMyRating.childNodes[0]);
            } else {
                tdMyRating.appendChild(myRatingSelect);
            }
    
            var tdLastWatched = document.getElementById("tdLastWatched_" + movieId);
            var lastWatchedText = createTextInput("lastWatched_" + movieId, 10, "entryTextMedium");
            if (tdLastWatched.textContent) {
                lastWatchedText.value = tdLastWatched.textContent;
                tdLastWatched.replaceChild(lastWatchedText, tdLastWatched.childNodes[0]);
            } else {
                tdLastWatched.appendChild(lastWatchedText);
            }           
            
            var tdEditImage = document.getElementById("tdEditImage_" + movieId);
            tdEditImage.innerHTML = "";
            tdEditImage.innerHTML = '<input type="button" tabindex="18" class="buttonRelativeSize" value="Save" onClick="saveMovie(' + movieId + ');" /><span id="saveMovieMessage_' + movieId + '" class="warningLabel"></span>';        
        }

        function editExtra(extraId) {
            var extraTitleTd = document.getElementById("tdExtraTitle_" + extraId);
            var extraTitleText = createTextInput("extraTitle_" + extraId, 60, "entryTextLong");
            extraTitleText.value = extraTitleTd.textContent;
            extraTitleTd.replaceChild(extraTitleText, extraTitleTd.childNodes[0]);
            
            var tdExtraDesc = document.getElementById("tdExtraDesc_" + extraId);
            var extraDescText = createTextInput("extraDesc_" + extraId, 150, "entryTextLong");
            if (tdExtraDesc.textContent) {
                extraDescText.value = tdExtraDesc.textContent;
                tdExtraDesc.replaceChild(extraDescText, tdExtraDesc.childNodes[0]);
            } else {
                tdExtraDesc.appendChild(extraDescText);
            }

            var tdEditExtraImage = document.getElementById("tdEditExtraImage_" + extraId);
            tdEditExtraImage.innerHTML = "";        
            tdEditExtraImage.innerHTML = '<input type="button" class="buttonRelativeSize" value="Save" onClick="saveExtra(' + extraId + ');" /><span id="saveExtraMessage_' + extraId + '" class="warningLabel"></span>';        
        }
        
        function saveMovie(movieId) {
            var formData = new FormData();
            formData.set("actionType", "Save");
            formData.set("movieId", movieId);
            formData.set("title", document.getElementById("movieTitle_" + movieId).value);
            formData.set("year", document.getElementById("movieYear_" + movieId).value);
            formData.set("runtime", document.getElementById("runtime_" + movieId).value);
            formData.set("country", document.getElementById("country_" + movieId).value);
            formData.set("language", document.getElementById("language_" + movieId).value);
            formData.set("mpaaRating", document.getElementById("mpaaRating_" + movieId).value);
            formData.set("myRating", document.getElementById("myRating_" + movieId).value);
            formData.set("lastWatched", document.getElementById("lastWatched_" + movieId).value);
            
            saveAjax("/movieApp/control/movie/movie.jsp", afterSaveMovie, formData, movieId);
        }

        function saveExtra(extraId) {            
            var formData = new FormData();
            formData.set("actionType", "Save");
            formData.set("extraId", extraId);
            formData.set("videoExtraId", extraId);
            formData.set("extraTitle", document.getElementById("extraTitle_" + extraId).value);
            formData.set("extraDesc", document.getElementById("extraDesc_" + extraId).value);
            
            saveAjax("/movieApp/control/video/extra.jsp", afterSaveExtra, formData, extraId);
        }    

        function afterSaveMovie(movieId) {
            var saveMovieMessage = document.getElementById("saveMovieMessage_" + movieId);
            saveMovieMessage.textContent = "Saved";
        }

        function afterSaveExtra(extraId) {
            var saveExtraMessage = document.getElementById("saveExtraMessage_" + extraId);
            saveExtraMessage.textContent = "Saved";
        }

        function save() {  
            videoCollection.actionType.value = "Save";
            if (validateForm()) {
                videoCollection.submit();
            } else {
                message.innerHTML = "Please correct data entry errors.";
            }          
        }

        function deleteVideo() {  
            videoCollection.actionType.value = "Delete";
            if (confirm("Are you sure you want to delete this video?")) {
                videoCollection.submit();
            }
        }

        function createCopy() {
            videoCollection.actionType.value = "CreateCopy";
            if (confirm("Are you sure you want to create a copy?  This will copy the video portion only and not the extras.")) {
                videoCollection.submit();
            }
        }    
            
        function addExtra() {
            videoCollection.newExtraCount.value = new Number(videoCollection.newExtraCount.value) + 1;
            var newExtraCount = videoCollection.newExtraCount.value;
            var saveExtraRow = document.getElementById("saveExtraRow");
           
            var newTR = document.createElement("tr");
            
            var extraTd = document.createElement("td");
            extraTd.className = "display";
            var extraTitleText = createTextInput("videoExtraTitle_new_" + newExtraCount, 60, "entryTextLong");
            extraTd.appendChild(extraTitleText);
            
            newTR.appendChild(extraTd);
            
            var extraDescTd = document.createElement("td");
            extraDescTd.className = "display";
            var extraDescText = createTextInput("videoExtraDescription_new_" + newExtraCount, 150, "entryTextLong");
            extraDescTd.appendChild(extraDescText);
            
            newTR.appendChild(extraDescTd);
            
            saveExtraRow.insertAdjacentElement("beforebegin", newTR)
        }
        
        function searchMovie() {
            window.open('/movieApp/control/movie/searchMovie.jsp','searchMovie','height=600,width=800,scrollbars=yes');
        }
        
        function addMovie(columns) {
            videoCollection.newCount.value = new Number(videoCollection.newCount.value) + 1;
            var newCount = videoCollection.newCount.value;
            var saveButtonRow = document.getElementById("saveButtonRow");
            
            var newTR = document.createElement("tr");
            
            var movieTd = document.createElement("td");
            movieTd.className = "display";
            
            var titleImageSpan = document.createElement("span");
            titleImageSpan.className = "validationimage";
            titleImageSpan.appendChild(createImage("imgTitle_new_" + newCount, "images/Required.gif", "validation"));           
            movieTd.appendChild(titleImageSpan);
            var movieTitleText = createTextInput("movieTitle_new_" + newCount, 100, "entryLabel");
            movieTd.appendChild(movieTitleText);
            newTR.appendChild(movieTd);
            
            var yearTd = document.createElement("td");
            yearTd.className = "display";
            var movieYearText = createTextInput("movieYear_new_" + newCount, 4, "entryTextYear");
            yearTd.appendChild(movieYearText);
            newTR.appendChild(yearTd);
            
            var runtimeTd = document.createElement("td");
            runtimeTd.className = "display";
            var movieRuntimeText = createTextInput("runtime_new_" + newCount, 4, "entryTextYear");
            runtimeTd.appendChild(movieRuntimeText);
            newTR.appendChild(runtimeTd);
            
            var countryTd = document.createElement("td");
            countryTd.className = "display";
            var countrySelect = createSelect("country_new_" + newCount, "");
            <webSup:javaScriptOptions name="countrySelect" lookup="COUNTRY" />
            countryTd.appendChild(countrySelect);
            newTR.appendChild(countryTd);

            var languageTd = document.createElement("td");
            languageTd.className = "display";
            var languageSelect = createSelect("language_new_" + newCount, "");
            <webSup:javaScriptOptions name="languageSelect" lookup="LANGUAGE" />
            languageTd.appendChild(languageSelect);
            newTR.appendChild(languageTd);
    
            var mpaaRatingTd = document.createElement("td");
            mpaaRatingTd.className = "display";
            var mpaaRatingSelect = createSelect("mpaaRating_new_" + newCount, "");
            <webSup:javaScriptOptions name="mpaaRatingSelect" lookup="MPAARATING" />
            mpaaRatingTd.appendChild(mpaaRatingSelect);   
            newTR.appendChild(mpaaRatingTd);
            
            var myRatingTd = document.createElement("td");
            myRatingTd.className = "display";
            var myRatingSelect = createSelect("myRating_new_" + newCount, "");
            <webSup:javaScriptOptions name="myRatingSelect" lookup="MYRATING" />
            myRatingTd.appendChild(myRatingSelect); 
            newTR.appendChild(myRatingTd);
            
            var lastWatchedTd = document.createElement("td");
            lastWatchedTd.className = "display";
            var lastWatchedText = createTextInput("lastWatched_new_" + newCount, 10, "entryTextMedium");
            lastWatchedTd.appendChild(lastWatchedText);
            newTR.appendChild(lastWatchedTd);
            
            var commentaryTd  = document.createElement("td");
            commentaryTd.className = "display";
            var commentaryText = createTextInput("commentary_new_" + newCount, 100, "entryLabel");
            commentaryTd.appendChild(commentaryText);
            newTR.appendChild(commentaryTd);
            
            for (var i = 9; i < columns; i++) {
                var newTD = document.createElement("td");
                newTD.innerText = "";
                newTD.className = "display";
                newTR.appendChild(newTD);   
            }
            saveButtonRow.insertAdjacentElement("beforebegin", newTR);            
        }

        function resetForm() {
            resetImage(videoCollection.imgTitle, false);
            resetImage(videoCollection.imgVideoType, false);
            resetImage(videoCollection.imgYearOfRelease, false);
            resetImage(videoCollection.imgDiscCount, false);
        }    

        function validateForm() {
            resetForm();
            
            var validForm = true;
            
            validForm = (validateText(videoCollection.title, videoCollection.imgTitle) === true) ? validForm : false;
            validForm = (validateSelect(videoCollection.videoType, videoCollection.imgVideoType) === true) ? validForm : false;
            validForm = (validateNumber(videoCollection.yearOfRelease, videoCollection.imgYearOfRelease) === true) ? validForm : false;
            validForm = (validateNumber(videoCollection.discCount, videoCollection.imgDiscCount) === true) ? validForm : false;
            validForm = (validateCurrency(videoCollection.cost, videoCollection.imgCost) === true) ? validForm : false;
            
            return validForm;
        }         
        </script>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h1>Video Collection Entry <span id="message" class="warningLabel center">${message}</span></h1>    
        <form name="videoCollection" action="${pageContext.request.contextPath}/control/video.jsp" method="post">
            <input type="hidden" name="videoId" value="${video.videoId}" />
            <input type="hidden" name="actionType" />
            <input type="hidden" name="newCount" value="0" />
            <input type="hidden" name="newExtraCount" value="0" />
            <table>
                <tr><td class="entryLabel">Video Title</td><td class="validationimage"><img class="balance" src="images/Required.gif" id="imgTitle" title="Required" alt="Required" /></td><td><input type="text" name="title" autofocus maxLength="100" value="${video.titleEscapeQuotes}" class="entryTextLong" tabindex="1" onchange="isModified('title');" /><span id="titleModified" class="warning"></span></td></tr>
                <tr>
                    <td class="entryLabel">Video Type</td><td class="validationimage"><img src="images/Required.gif" id="imgVideoType" class="validation" title="Required" alt="Required" /></td>
                    <td><webSup:select name="videoType" value="${video.videoType}" lookup="VIDEOTYPE" tabindex="2" onchange="isModified('videoType');" /><span id="videoTypeModified" class="warning"></span></td>
                </tr>
                <tr><td class="entryLabel">Year of Release</td><td class="validationimage"><img class="balance" src="images/pixel.gif" id="imgYearOfRelease" title="" alt="" /></td><td><input type="text" name="yearOfRelease" maxLength="4" value="${video.yearOfRelease}" class="entryTextYear" tabindex="3" onchange="isModified('yearOfRelease');" /><span id="yearOfReleaseModified" class="warning"></span></td></tr>
                <tr>
                    <td class="entryLabel">Region</td><td class="validationimage"></td>
                    <td><webSup:select name="region" value="${video.region}" lookup="REGION" tabindex="4" onchange="isModified('region');" /><span id="regionModified" class="warning"></span></td>
                </tr>
                <tr><td class="entryLabel">Case Type</td><td class="validationimage"></td><td><webSup:select name="caseType" value="${video.caseType}" lookup="CASETYPE" tabindex="5" onchange="isModified('caseType');" /><span id="caseTypeModified" class="warning"></span></td></tr>                
                <tr><td class="entryLabel">Disc Count</td><td class="validationimage"><img class="balance" src="images/pixel.gif" id="imgDiscCount" title="" alt="" /></td><td><input type="text" name="discCount" maxLength="2" value="${video.discCount}" class="entryTextYear" tabindex="6" onchange="isModified('discCount');" /><span id="discCountModified" class="warning"></span></td></tr>                
                <tr><td class="entryLabel">Spine Number</td><td class="validationimage"></td><td><input type="text" name="spineNumber" maxLength="7" value="${video.spineNumber}" class="entryText7Digits" tabindex="7" onchange="isModified('spineNumber');" /><span id="spineNumberModified" class="warning"></span></td></tr>                
                <tr><td class="entryLabel">Label</td><td class="validationimage"></td><td><input type="text" name="label" maxLength="40" value="${video.label}" class="entryText" tabindex="8" onchange="isModified('label');" /><span id="labelModified" class="warning"></span></td></tr>
                <tr><td class="entryLabel">Cost</td><td class="validationimage"><img class="balance" src="images/pixel.gif" id="imgCost" title="" alt="" /></td><td><input type="text" name="cost" maxLength="7" value="<fmt:formatNumber value='${video.cost}' type='currency' />" class="entryTextMedium" tabindex="9" onchange="isModified('cost');" /><span id="costModified" class="warning"></span></td></tr>
                <tr><td class="entryLabel">Obtained From</td><td class="validationimage"></td><td><input type="text" name="obtainedFrom" maxLength="30" value="${video.obtainedFrom}" class="entryText" tabindex="10" onchange="isModified('obtainedFrom');" /><span id="obtainedFromModified" class="warning"></span></td></tr>
                <tr>
                    <td class="entryLabel">Obtained Type</td><td class="validationimage"></td>
                    <td><webSup:select name="obtainedType" value="${video.obtainedType}" lookup="OBTAINEDTYPE" tabindex="11" onchange="isModified('obtainedType');" /><span id="obtainedTypeModified" class="warning"></span></td>
                </tr>
                <tr><td class="entryLabel">Location</td><td class="validationimage"></td><td><input type="text" name="location" maxLength="30" value="${video.location}" class="entryText" tabindex="12" onchange="isModified('location');" /><span id="locationModified" class="warning"></span></td></tr>
                <tr><td class="entryLabel">Total Minutes</td><td class="validationimage"></td><td><input type="text" name="totalMinutes" maxLength="4" value="${video.totalMinutes}" class="entryTextYear" tabindex="12" onchange="isModified('totalMinutes');" /><span id="totalMinutesModified" class="warning"></span></td></tr>
                <tr><td class="entryLabel">Slipcover</td><td class="validationimage"></td><td><input type="checkbox" class="left" name="slipcoverYn" value="true" <c:if test="${video.slipcoverYn == true}">checked</c:if> tabindex="13" onchange="isModified('slipcoverYn');" /><span id="slipcoverYnModified" class="warning"></span></td></tr>
                <tr><td class="entryLabel">OOP</td><td class="validationimage"></td><td><input type="checkbox" class="left" name="oopYn" value="true" <c:if test="${video.oopYn == true}">checked</c:if> tabindex="14" onchange="isModified('oopYn');" /><span id="oopYnModified" class="warning"></span></td></tr>
                <tr><td class="entryLabel">Comments</td><td class="validationimage"></td><td><textarea name="comments" class="notes" tabindex="17">${video.comments}</textarea></td></tr>
            </table>
            <div class="label">Movie(s):</div>
            <c:set var="totalMovieColumns" value="9" />
            <table class="display" id="displayMovies">
                    <thead><tr><td class="display">Title</td><td class="display">Year</td><td class="display">Runtime</td><td class="display">Country</td><td class="display">Language</td><td class="display">MPAA Rating</td><td class="display">My Rating</td><td class="display">Last Watched</td>
                        <c:if test="${video.hasVideoMisc == true}"><td class="display">Miscellaneous</td><c:set var="totalMovieColumns" value="${totalMovieColumns + 1}" /></c:if>
                        <td class="display">Commentary(s)</td>
                        <c:if test="${!empty video.videoMovies}"><td class="display">Edit</td></c:if>
                    </tr></thead>
                <c:if test="${not empty video.videoMovies}"><c:set var="totalMovieColumns" value="${totalMovieColumns + 1}" /></c:if>                        
                <c:forEach var="movie" items="${video.videoMovies}">                     
                    <tr><input type="hidden" name="movieId" value="${movie.movieId}" /><td id="tdMovieTitle_${movie.movieId}" class="display">${movie.title}</td><td id="tdYear_${movie.movieId}" class="display">${movie.year}</td><td id="tdRuntime_${movie.movieId}" class="display">${movie.runtime}</td><td id="tdCountry_${movie.movieId}" class="display">${movie.country}</td><td id="tdLanguage_${movie.movieId}" class="display">${movie.language}</td><td id="tdMpaaRating_${movie.movieId}" class="display">${movie.mpaaRating}</td><td id="tdMyRating_${movie.movieId}" class="display">${movie.myRating}</td><td id="tdLastWatched_${movie.movieId}" class="display"><fmt:formatDate value='${movie.lastWatched}' pattern='MM/dd/yyyy'/></td>
                        <c:if test="${video.hasVideoMisc == true}">
                        <td class="display"><c:forEach var="videoMisc" items="${movie.videoMisc}"><span <c:if test="${videoMisc.warningYn == true}">class="warning"</c:if>>${videoMisc.videoMisc}</span><c:if test="${!status.last}"><br/></c:if></c:forEach></td>
                        </c:if>
                        <td class="display"><c:forEach var="commentary" varStatus="commStatus" items="${movie.commentaries}">${commentary}<c:if test="${!status.last}"><br/></c:if></c:forEach></td>
                        <td id="tdEditImage_${movie.movieId}" class="display"><img id="editImage_${movie.movieId}" src="images/edit.gif" title="Edit Movie" alt="Edit Movie" onClick="edit(${movie.movieId});" /></td>
                    </tr>
                </c:forEach>
                    <tr id="saveButtonRow"><td colspan="10" class="center"><input type="button" tabindex="18" class="button" value="Add Movie" onClick="addMovie(${totalMovieColumns});"/>
                        <input type="button" tabindex="19" class="button" value="Search Movie" onClick="searchMovie();"/></td></tr>
            </table>
            <div class="label">Extra(s):</div>
            <table class="display" id="displayVideoExtras">
                <thead><tr><td class="display">Extra</td><td class="display">Description</td>
                       <c:if test="${!empty video.videoExtras}"><td class="display">Edit</td></c:if></tr></thead>
                <c:forEach var="videoExtra" items="${video.videoExtras}">
                    <tr><td class="display" id="tdExtraTitle_${videoExtra.videoExtraId}">${videoExtra.title}</td><td class="display" id="tdExtraDesc_${videoExtra.videoExtraId}">${videoExtra.description}</td>
                    <td id="tdEditExtraImage_${videoExtra.videoExtraId}" class="display"><img id="editExtraImage_${videoExtra.videoExtraId}" src="images/edit.gif" title="Edit Extra" alt="Edit Extra" onClick="editExtra(${videoExtra.videoExtraId});" /></td>
                    </tr>
                </c:forEach>
                <tr id="saveExtraRow"><td colspan="2" class="center"><input type="button" tabindex="18" class="button" value="Add Extra" onClick="addExtra();"/></td></tr>
            </table>
            <span>
                <input type="button" tabindex="18" class="button" value="Save" onClick="save();"/>
                <span style="display:inline-block; float:right;">
                <c:if test="${!empty video.videoId}">
                    <span class="">
                        <input type="checkbox" id="copyExtras" name="copyExtras" value="true"><label for="copyExtras"> Copy Extras</label><br>
                    </span>
                    <input type="button" tabindex="19" class="button" value="Create Copy" onClick="createCopy();"/><br>
                </c:if>
                <c:if test="${!empty video.videoId}">
                    <input type="button" tabindex="20" class="button" value="Delete" onClick="deleteVideo();"/><br>
                </c:if>
                <span class="editedInfo">Last Modified: <fmt:formatDate value="${video.lastModified}" pattern="MM/dd/yyyy hh:mm a"/></span>
                </span>
            </span>
            <h2></h2>
        </form>                                            
    </body>
</html>