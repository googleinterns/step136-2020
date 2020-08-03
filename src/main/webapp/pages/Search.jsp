<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Search Results</title>
    <%@ include file="../components/GlobalConfig.jsp"%>
    <script src="https://apis.google.com/js/platform.js?onload=initPageGoogleSignin" async defer></script>
  </head>
  <body onload="search()">
      <%@ include file="../components/topbar.jsp"%>
      <div id="extended search">
      </div>
      <div id="content">
      </div>
  </body>
</html>