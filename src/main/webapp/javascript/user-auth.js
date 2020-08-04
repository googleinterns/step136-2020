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
 * Takes a listener arg, to execute when a new user signs in.
 */
function initGoogleUserWithListener(listener) {
  gapi.load('auth2', function() {
    initSigninV2(listener);
  });
}

/**
 * Initializes google authentication API and preforms onload functions.
 * Arg is an optional listener to execute when a new user logs in.
 */
function initSigninV2(listener) {
  auth2 = gapi.auth2.init({
    client_id : CLIENT_ID
  });

  if (document.getElementById('g-signin-container') != null) {
    fillSigninContainer('g-signin-container');
  }

  if (listener != null) {
    auth2.currentUser.listen(listener);
  }
}

// Creates sign in box and sets event listeners.
function fillSigninContainer(signInContainer) {

  gapi.signin2.render(signInContainer);

  auth2.currentUser.listen((newGoogleUser) => {
    if (auth2.isSignedIn.get()) {
      insertUserInfo(newGoogleUser);
    }
  });

  auth2.isSignedIn.listen((isSignedIn) => {
    if (isSignedIn) {
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
  // Insert user name into user-page anchor.
  let text = googleUser.getBasicProfile().getGivenName() + '\'s Recipes';
  const userPageAnchor = document.getElementById('user-page-anchor');
  userPageAnchor.innerText = text;
}

function enableUserDropdown() {
  const dropdown = document.querySelector('.dropdown');
  const signInBox = document.getElementById('sign-in-box');
  dropdown.addEventListener('mouseover', showDropdown = function() {
    signInBox.style.borderBottomColor = 'white';
    signInBox.style.borderRadius = '10px 10px 0 0';
    dropdown.querySelector('.dropdown-content').style.display = 'block';
  });
  dropdown.addEventListener('mouseleave', hideDropdown = function() {
    signInBox.style.borderBottomColor = 'rgb(0, 0, 0, 0.25)';
    signInBox.style.borderRadius = '10px';
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
 * Important: There is a delay between page loading and session restoration.
 * Using this function in onload will force user to login because of this delay.
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
