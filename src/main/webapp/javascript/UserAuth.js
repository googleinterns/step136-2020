// To be called before any request that requires user logged in.
// Action: if user not logged in, directs to login page, else does nothing.
// Args: Optional string argument provides url location after login, otherwise login redirects to caller url.
function confirmUser(redirectUrl) {
    if (!redirectUrl) {
        redirectUrl = window.location.href;
    }

    fetch(`/login?redirectUrl=${redirectUrl}`).then(response => response.json()).then(loginJson => {
        if (loginJson["isUserLoggedIn"]) {
            return;
        }
        console.log(loginJson);
        let loginPageUrl = loginJson["loginPageUrl"];
        window.location.href = loginPageUrl;
    });
}

function logout() {
    fetch("/logout");
}