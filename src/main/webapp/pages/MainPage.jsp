<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="../css/style-sign-in.css">
    <%@ include file="../components/GlobalConfig.jsp"%>
    <script src="../javascript/recipe-rendering.js"></script>
    <script src="../javascript/user-auth.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
  </head>
  <body onload="mainPageLoad(); initGoogleUserWithListener();">
      <%@ include file="../components/topbar.jsp"%>
      <div id="content">
        
      </div>
  </body>
</html>