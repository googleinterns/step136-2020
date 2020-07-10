var auth2;
// client ID is generated from my google APIs credentials page.
const CLIENT_ID = '1034390229233-u07o0iaas2oql8l4jhe7fevpfsbrtv7n.apps.googleusercontent.com'

// googleUser is generated with auth2.currentUser.get().
// Further Google Sign in documentation is available at:
// https://developers.google.com/identity/sign-in/web/reference

/**
 * This ID token is an encrypted token containing user information,
 * including user unique ID and profile data.
 * ID token must be sent to backend for processing for any user-related datastore operation.
 */
function getIdToken() {
  return auth2.currentUser.get().getAuthResponse().id_token;
}

/**
 * Loads the auth2 library for the g-signin API, and then initializes
 * the googleAuth object.
 * Passed as jquery parameter, executed by API script.
 */
function onStart() {
  gapi.load('auth2', initSigninV2);
}

/**
 * Initializes google authentication API and sets event listeners.
 */
function initSigninV2() {
  auth2 = gapi.auth2.init({
    client_id : CLIENT_ID
  });

  gapi.signin2.render('g-signin-container');

  auth2.currentUser.listen(function(newGoogleUser) {
    if (auth2.isSignedIn.get()) {
      insertUserInfo(newGoogleUser);
    }
  });

  auth2.isSignedIn.listen(function(signedIn) {
    if (signedIn) {
      enableUserDropdown();
    } else {
      disableUserDropdown();
    }
  });
}

/**
 * Signs out current user from g-signin API.
 */
function signOut() {
  auth2.signOut();
}

/**
 * To be run in currentUser listener, activates whenever current user changes.
 *
 * @param {GoogleUser} current user to provide profile info.
 */
function insertUserInfo(googleUser) {
  // Insert user name into user-page anchor
  let text = googleUser.getBasicProfile().getGivenName() + '\'s Page';
  const userPageAnchor = document.getElementById('user-page-anchor');
  userPageAnchor.innerText = text;
}

function enableUserDropdown() {
  const dropdown = document.querySelector('.dropdown');
  dropdown.addEventListener('mouseover', showDropdown = function() {
    dropdown.querySelector('.dropdown-content').style.display = 'block';
  });
  dropdown.addEventListener('mouseleave', hideDropdown = function() {
    dropdown.querySelector('.dropdown-content').style.display = 'none';
  });
}

function disableUserDropdown() {
  const dropdown = document.querySelector('.dropdown');
  dropdown.removeEventListener('mouseover', showDropdown);
  dropdown.removeEventListener('mouseleave', hideDropdown);
  dropdown.querySelector('.dropdown-content').style.display = 'none';
}

/**
 * Provides a sign-in popup if user is signed out,
 * returns a promise when user is signed in.
 * 
 */
function confirmUser() {
  return new Promise((resolve, reject) => {
    if (!auth2.isSignedIn.get()) {
      auth2.signIn().then(resolve).catch(reject);
    } else {
      resolve();
    }
  })
}
