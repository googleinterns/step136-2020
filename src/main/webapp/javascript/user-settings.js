var userChanged = function() {
  fetch("/settings?idToken=" + getIdToken()).then(reponse => reponse.json()).then(json => {
    userName = json["displayName"]
    document.getElementById("name-input-field").value = userName;
  })
}

var signInChanged = function(signedIn) {
  if (!signedIn) {
    window.location.href = "/pages/MainPage.jsp";
  }
}

let postName = function() {
  params = new URLSearchParams();
  params.append("idToken", getIdToken());

  nameInput = document.getElementById("name-input-field").value;
  params.append("nameInput", nameInput);

  messageArea = document.getElementById("name-feedback");
  fetch("/settings", {
    method : "POST",
    body : params
  }).then(response => response.json()).then(json => {
    if (json["success"]) {
      messageArea.style.color = "blue";
    } else {
      messageArea.style.color = "red";
    }
    messageArea.innerText = json["message"];
  }).catch(() => {
    messageArea.innerText = "";
  });
}