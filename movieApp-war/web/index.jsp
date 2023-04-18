<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Video Collection Entry</title>
        <link rel="StyleSheet" href="css/movie.css">
        <script type="text/javascript">
        function submit() {  
            videoCollection.submit();
        }
        function addRow(rowNum) {
            var nextNum = rowNum + 1;
            alert("addRow " + nextNum);
            var currentRow = document.getElementById(["movieRow_" + rowNum]);
            var parent = currentRow.parentElement;
            var newRow = currentRow.cloneNode(true);
            //newRow.
            parent.appendChild(newRow);
            alert(parent.id);
        }
        </script>
    </head>
    <body>
        <h1>Video Collection Entry</h1>    
        <form name="videoCollection" action="${pageContext.request.contextPath}/control/index.jsp" method="post">
            <input type="hidden" name="movieCount" value="" />
            <table>
                <tr><td class="entryLabel">Video Title</td><td class="validationimage"><img src="images/Required.gif" id="imgTitle" class="validation" title="Required" alt="Required" /></td><td><input type="text" name="title" maxLength="100" value="Title" class="entryText" tabindex="1" /></td></tr>
                <tr>
                    <td class="entryLabel">Video Type</td><td class="validationimage"><img src="images/Required.gif" id="imgVideoType" class="validation" title="Required" alt="Required" /></td>
                    <td>
                        <select name="videoType" size="1" tabindex="2" >
                        <option value="-" ></option>
                        <option value="BD">BD (blu-ray)</option>
                        <option value="BD">BD/DVD Combo</option>
                        <option value="DVD">DVD</option>
                        <option value="Laserdisc">Laserdisc</option>
                        <option value="VHS">VHS</option>
                        </select>
                    </td>
                </tr>
                <tr><td class="entryLabel">Year of Release</td><td class="validationimage"></td><td><input type="text" name="year" maxLength="4" value="" class="entryTextYear" tabindex="3" /></td></tr>
                <tr>
                    <td class="entryLabel">Rating</td><td class="validationimage"></td>
                    <td>
                        <select name="videoRating" size="1" tabindex="4" >
                        <option value="-" ></option>
                        <option value="****">****</option>
                        <option value="***½">***½</option>
                        <option value="***">***</option>
                        <option value="**½">**½</option>
                        <option value="**">**</option>
                        <option value="*½">*½</option>
                        <option value="*">*</option>
                        </select>
                    </td>
                </tr>
                <tr><td class="entryLabel">Label</td><td class="validationimage"></td><td><input type="text" name="label" maxLength="40" value="" class="entryText" tabindex="5" /></td></tr>
                <tr><td class="entryLabel">Cost</td><td class="validationimage"></td><td><input type="text" name="cost" maxLength="7" value="" class="entryTextMedium" tabindex="6" /></td></tr>
                <tr><td class="entryLabel">Obtained From</td><td class="validationimage"></td><td><input type="text" name="cost" maxLength="30" value="" class="entryText" tabindex="7" /></td></tr>
                <tr>
                    <td class="entryLabel">Obtained Type</td><td class="validationimage"></td>
                    <td>
                        <select name="obtainedType" size="1" tabindex="8" >
                        <option value="-" ></option>
                        <option value="gift">Gift</option>
                        <option value="onlinePurchase">Online Purchase</option>
                        <option value="storePurchase">Store Purchase</option>
                        <option value="other">Other</option>
                        <option value="unknown">Unknown</option>
                        </select>
                    </td>
                </tr>
                <tr><td class="entryLabel">Comments</td><td class="validationimage"></td><td><textarea name="comments" class="notes" tabindex="9">Comments</textarea></td></tr>
            </table>
            <h2>Movie(s):</h2>
            <table class="editable" id="table">
                <thead><tr><td class="editable"></td><td class="editable">Title</td><td class="editable">Year</td><td class="editable">Runtime</td><td class="editable">MPAA Rating</td><td class="editable">My Rating</td><td class="editable">Add New</td></tr></thead>
                <tbody id="tbody">
                    <tr id="movieRow_1">
                        <td class="validationimage"><img src="images/Required.gif" id="imgMovie_1" class="validation" title="Required" alt="Required" /></td>
                        <td class="editable"><input type="text" name="movieTitle_1" maxLength="100" value="" class="entryText" tabindex="10" /></td>
                        <td class="editable"><input type="text" name="year_1" maxLength="4" value="" class="entryTextYear" tabindex="11" /></td>
                        <td class="editable"><input type="text" name="runTime_1" maxLength="4" value="" class="entryTextYear" tabindex="12" /></td>
                        <td class="editable">
                            <select name="mpaaRating_1" size="1" tabindex="13" >
                            <option value="Unrated" >Unrated</option>
                            <option value="G">G</option>
                            <option value="PG">PG</option>
                            <option value="PG-13">PG-13</option>
                            <option value="R">R</option>
                            <option value="NC-17">NC-17</option>
                            </select>
                        </td>
                        <td class="editable">
                            <select name="movieRating_1" size="1" tabindex="14" >
                            <option value="-" ></option>
                            <option value="****">****</option>
                            <option value="***½">***½</option>
                            <option value="***">***</option>
                            <option value="**½">**½</option>
                            <option value="**">**</option>
                            <option value="*½">*½</option>
                            <option value="*">*</option>
                            </select>
                        </td>
                        <td class="editable center"><img src="images/addNew.gif" id="imgAddNew_1" title="Add New" alt="Add New" onClick="addRow('1');" /></td>
                    </tr>
                </tbody>
            </table>
            <input type="button" tabindex="15" class="button" Value="Save" onClick="submit();"/>
        </form>                                            
    </body>
</html>
 
