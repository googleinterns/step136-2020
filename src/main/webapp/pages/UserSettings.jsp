<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Settings</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="../javascript/user-settings.js"></script>
    <%@ include file="../components/GlobalConfig.jsp"%>
  </head>
  <body>
      <%@ include file="../components/topbar.jsp"%>
      <div id="content">
        <form id="user-settings-form">
          <input id="name-input-field" type="text" name="name-input">
          <input type="submit" value="Save">
          <input type="hidden" name="idToken" id="idTokenField">
        </form>
      </div>
  </body>
</html>