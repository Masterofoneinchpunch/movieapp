/* create a new Hidden element */
function createHidden(name) {
    return createHiddenValue(name, "");
} /* end function createHidden */

function createImage(id, src, className) {
    var newImage = document.createElement("img");
    newImage.id = id;
    newImage.src = src;
    newImage.className = className;
    
    return newImage;
}

function createHiddenValue(name, value) {
    var newHidden = document.createElement("input");
    newHidden.setAttribute("type", "hidden");
    newHidden.value = value;
    newHidden.id = name;
    newHidden.name = name;
    
    return newHidden;
} /* end function createHidden */

/* creates a label with text */
function createLabel(str, className) {
    var newLabel = document.createElement("label");
    var text = document.createTextNode(str);
    newLabel.appendChild(text); 
    newLabel.className = className; 
    return newLabel;
}

/* create a new select element */ 
function createSelect(name, className) {
    var newSelect = document.createElement("select");
    newSelect.id = name;
    newSelect.name = name;
    newSelect.className = className; 
    return newSelect;
}

/* create a new text input element */
function createTextInput(name, maxLength, className) {
    var newTextInput = document.createElement("input");
        newTextInput.setAttribute("type", "text");
        newTextInput.id = name;
        newTextInput.name = name;
        newTextInput.maxLength = maxLength;
        newTextInput.className = className; 

    return newTextInput;
} /* end function createTextInput */

/* returns the correct node within the passed node, using the passed in parameter name;
 * this function also defined in jsManager.js */
function getElement(objNode, name) {
	var returnNode;
		if(objNode.name && objNode.name.match(eval("/^" + name + "$/"))) {
			return objNode;
		} else {
			for (var c=0; c<objNode.childNodes.length; c++) {
				returnNode = getElement(objNode.childNodes[c], name);
				if (returnNode) return returnNode;			
			} // end for
		} // else if (objNode.name == name)
} /* end function getElement(objNode, name) */
