var userChanged = function() {
  fetch("/settings?idToken=" + getIdToken()).then(reponse => reponse.json()).then(json => {
    userName = json["displayName"]
    document.getElementById("name-input-field").value = userName;
  })
}