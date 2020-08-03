<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/style-main-recipes.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <%@ include file="../components/GlobalConfig.jsp"%>
    <script src="../javascript/recipe-rendering.js"></script>
    <script src="../javascript/user-auth.js"></script>
    <script src="https://apis.google.com/js/platform.js?onload=initGoogleUserWithListener" async defer></script>
    <script src="https://apis.google.com/js/platform.js?onload=initPageGoogleSignin" async defer></script>
  </head>
  <body onload="mainPageLoad();">
      <%@ include file="../components/topbar.jsp"%>
      <div id="content">
        <div id="main-recipes-container">
        </div>
      </div>
  </body>
</html>