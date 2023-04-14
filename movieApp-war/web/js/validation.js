function isNumber(fieldObj) {
	isNum = false;
	stringNum = fieldObj.value;
	if(stringNum === "") {
            isNum = true;
	} else if (stringNum.match(/([ ]{0,})(\d{1,})([ ]{0,})/)) {
            fieldObj.value = RegExp.$2;
            isNum = true;
	} 
	return isNum;
} 

function validateText(textField, img) {
    if (textField.value.trim() === "") {
        img.src = "images/valError.gif";
        img.alt = "This item is required; please enter a value!";
        img.title = "This item is required; please enter a value!";
        return false;
    }
    return true;
}

function validateNumber(textField, img) {
    var valid = isNumber(textField);
    if (valid === false) {
        img.src = "images/valError.gif";
        img.alt = "This item must be a number!";
        img.title = "This item must be a number!";
    }
    return valid;
}

function isCurrency(fieldObj) {
   isNum = false;
    stringNum = fieldObj.value;
    if(stringNum === "") {
      isNum = true;
    } else if (stringNum.match(/(\s{0,})(\d{1,})([\.]{1})(\d{3,})(\s{0,})/)) {
      fieldObj.value = RegExp.$2 + '.' + RegExp.$4;
      isNum = false;
    } else if (stringNum.match(/(\s{0,})(\d{1,})([\.]{1})(\d{1,2})(\s{0,})/)) {
      fieldObj.value = RegExp.$2 + '.' + RegExp.$4;
      isNum = true;
    } else if (stringNum.match(/(\s{0,})([\.]{1})(\d{3,})(\s{0,})/)) {
      fieldObj.value = 0 + '.' + RegExp.$3;
      isNum = false;
    } else if (stringNum.match(/(\s{0,})([\.]{1})(\d{1,2})(\s{0,})/)) {
      fieldObj.value = 0 + '.' + RegExp.$3;
      isNum = true;
    }
    return isNum;
}

function validateCurrency(textField, img) {
    var valid = isCurrency(textField);
    if (valid === false) {
        img.src = "images/valError.gif";
        img.alt = "This item should be in currency form - ex. $5.30!";
        img.title = "This item should be in currency form - ex. $5.30!";
    }
    return valid;
}

function validateSelect(select, img) {
    if ((select.selectedIndex === 0 || select.selectedIndex === -1) && (select.value === "")) {
        img.src = "images/valError.gif";
        img.alt = "This item is required; please select an option.";
        img.title = "This item is required; please select an option.";
        return false;
    }
    return true;
}

function resetImage(img, required) {
    if (required === true) {
        img.src = "images/Required.gif";
        img.title = "";
        img.alt = "";
    } else {
        img.src = "images/pixel.gif";
        img.title = "";
        img.alt = "";
    }
}

