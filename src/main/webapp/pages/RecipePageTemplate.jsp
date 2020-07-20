<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Home</title>
    <%@ include file="../components/GlobalConfig.jsp"%>
  </head>
  <body>
      <%@ include file="../components/topbar.jsp"%>
      <div id="content">
        <div id="details">
          <!--Information about the reicipe, such as name, author, likes, tags, etc.-->
        </div>
        <div id="image-container">
          <!--The div to hold the image retrieved from the Datastore-->
        </div>
        <div id="description">
          <!--The text for the description-->
        </div>
        <div id="ingredients">
          <!--The ingredient list for the recipe-->
        </div>
        <div id="steps">
          <!--The steps for the recipe-->
        </div>
      </div>
  </body>
</html>