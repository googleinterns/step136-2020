
// To be called before any request that requires user be logged in.
// Action: if user not logged in, redirects to login page
// then redirects to redirectUrl regardless.
// Args: String url redirectUrl defaults to the page function was called form.
function confirmUserAndRedirect(redirectUrl) {
    if (!redirectUrl) {
        redirectUrl = window.location.href;
    }

    fetch(`/login?redirectUrl=${redirectUrl}`).then(response => response.json()).then(loginJson => {
        if (loginJson["isUserLoggedIn"]) {
            window.location.href = redirectUrl;
            return;
        }

        let loginPageUrl = loginJson["loginPageUrl"];
        window.location.href = loginPageUrl;
    });
}

// Action: requests logout, returns to index.html
function logout() {
    fetch("/logout");
}