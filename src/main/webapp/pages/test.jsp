<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Test Code</title>
    <link rel="stylesheet" href="../css/style-sign-in.css">
    <script src="../javascript/RecipeRendering.js"></script>
    <script src="../javascript/user-auth.js"></script>
    <script src="../javascript/tests.js"></script>
    <script src="https://apis.google.com/js/platform.js?onload=onStart" async defer></script>
  </head>
  <body onload=runTests()>
      <!--This div is for rendering the recipe cards which result from testing.-->
      <div id="recipe-card-test">
      </div>

      <div id="sign-box-test">
        <div id="sign-in-box" class="dropdown">
          <div id="profile-photo-container">
            <img id="profile-photo" src=../images/default_profile.jpeg>
          </div>
          <div id="g-signin-container">
          </div>
          <div class="dropdown-content">
            <a href="UserPage.html" id="user-page-anchor"></a>
            <a href="#" onclick="signOut()">Sign out</a>
          </div>
        </div>
      </div>

      <div id="confirm-user-test">
        <p><br>preforms a user specific task, that requires waiting for login</p>
        <button onclick="userSpecificService()" id="user-specific-service">sign in required service</button>
        <p id="test-output"></p>
      </div>
  </body>
</html>