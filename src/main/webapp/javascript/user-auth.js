var auth2;

function getId() {
    return auth2.currentUser.get().getId();
}

function onStart() {
  console.log("onstart");
  gapi.load('auth2', initSigninV2);
}

/**
 * initializes google authentication API and sets event listeners.
 */
function initSigninV2() {
  auth2 = gapi.auth2.init({
    client_id : '1034390229233-u07o0iaas2oql8l4jhe7fevpfsbrtv7n.apps.googleusercontent.com'
  });

  gapi.signin2.render('g-signin-container');

  auth2.currentUser.listen(function(newGoogleUser) {
    insertUserInfo(newGoogleUser);
  });

  auth2.isSignedIn.listen(function(signedIn) {
    if (signedIn) {
      console.log('enable dropdown');
      enableUserDropdown();
    } else {
      console.log('disable dropdown');
      disableUserDropdown();
    }
  });
}

/**
 * returns a promise that resolves when user signs in,
 * or rejects if user does not sign in.
 * currently doesn't work because auth2.signin resolves before signin is complete
 * for some reason.
 */
function confirmUser() {
  return new Promise((resolve, reject) => {
      if (!auth2.isSignedIn.get()) {
          auth2.signIn().then(resolve()).catch(reject());
      } else {
          console.log("already done");
          resolve();
      }
  })
}

/**
 * signs our current user from g-signin API.
 */
function signOut() {
  auth2.signOut().then(function () {
      console.log('User signed out.');
  });
}

/**
 * To be run in currentUser listener, activates whenever current user changes.
 *
 * @param {GoogleUser} current user to provide profile info.
 */
function insertUserInfo(googleUser) {
  // insert user name into user-page anchor
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

function userSpecificService() {
    confirmUser.then(console.log('test ' + auth2.currentUser.get().getBasicProfile().getName()));
}