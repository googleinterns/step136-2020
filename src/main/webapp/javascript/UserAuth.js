
// To be called before any request that requires user be logged in.
// Action: redirects to user login page, then returns to redirectUrl.
// Args: String redirectUrl defaults to the page function was called form.
function confirmUserAndRedirect(redirectUrl) {
  if (!redirectUrl) {
    redirectUrl = window.location.href;
  }

  fetch(`/login?redirectUrl=${redirectUrl}`).then(response => response.json()).then(json => {
      window.location.replace(json.loginLink);
  });
}

function userLoggedIn() {
  fetch("/user-status").then(response => response.json()).then(userInfo => {
    return userInfo.isUserLoggedIn;
  });
}

// Action: requests logout .
// Args: optional string "redirectUrl" determines location after logout.
//       defaults to index.html.

function logout(redirectUrl) {
  if (!redirectUrl) {
      redirectUrl = "/index.html";
  }

  fetch(`/logout?redirectUrl=${redirectUrl}`).then(response => response.json()).then(json => {
    window.location.replace(json.logoutLink);
  });
}
