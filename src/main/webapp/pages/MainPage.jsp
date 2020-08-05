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
  </head>
  <body>
      <%@ include file="../components/topbar.jsp"%>
      <div id="content">
        <div id="main-recipes-container" class="recipes-container">
        </div>
      </div>
      <script>mainPageLoad();</script>
  </body>
</html>