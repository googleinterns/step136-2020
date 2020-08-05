<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Settings</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <%@ include file="../components/GlobalConfig.jsp"%>
    <script src="../javascript/user-settings.js"></script>
  </head>
  <body>
      <%@ include file="../components/topbar.jsp"%>
      <div id="content">
        <p>Names are limited to Alphanumeric characters, spaces, dashes and underscores</p>
        <label for="name-input">Display Name: </label>
        <input type="text" name="name-input" id="name-input-field">
        <input type="button" value="Save Changes" onclick="postName();">
        <p id="name-feedback"></p>
      </div>
  </body>
</html>