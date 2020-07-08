<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="google-signin-client_id" content="1034390229233-u07o0iaas2oql8l4jhe7fevpfsbrtv7n.apps.googleusercontent.com">
    <title>Test Code</title>
    <link rel="stylesheet" href="../css/style-sign-in.css">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="../javascript/RecipeRendering.js"></script>
    <script src="../javascript/user-auth.js"></script>
    <script src="../javascript/tests.js"></script>
  </head>
  <body onload=runTests()>
      <!--This div is for rendering the recipe cards which result from testing.-->
      <div id="recipe-card-test">
      </div>
      <div id="user-box-test">
        <div id="sign-in-box" class="dropdown">
          <div id="profile-photo-container">
            <img id="profile-photo" src=../images/default_profile.jpeg>
          </div>
          <div id="g-signin-container">
            <div class="g-signin2" data-onsuccess="onSignIn"></div>
          </div>
          <div class="dropdown-content">
            <a href="UserPage.html" id="user-page-anchor"></a>
            <a href="#" onclick="signOut()">Sign out</a>
          </div>
        </div>
      </div>
  </body>
</html>