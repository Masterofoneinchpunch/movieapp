function saveAjax(url, behavior, formData, id) {
  const OK = 200;
  let xhttp = new XMLHttpRequest();
  //this is called four times for each ready state 
  xhttp.onreadystatechange = function() {
    //alert(this.readyState + " " + this.status);
    if (this.readyState === XMLHttpRequest.DONE && this.status === OK) {
        behavior(id);
    }
  };
  xhttp.open("POST", url, true);
  xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); //multipart/form-data application/x-www-form-urlencoded

  let formValues = "";
  for (let data of formData) {
      let value = data[0] + "=" + data[1];
      formValues = formValues + value + "&";
  }
  if (formValues !== "") {
      formValues = formValues.slice(0, -1);
  }
  //note if you pass a FormData it will be a content of //multipart/form-data
  xhttp.send(formValues);
}
