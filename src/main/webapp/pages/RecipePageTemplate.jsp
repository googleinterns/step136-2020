<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Recipe View</title>
    <%@ include file="../components/GlobalConfig.jsp"%>
    <link rel="stylesheet" href="../css/recipe-page.css">
    <!--The script below makes sure that the template isn't filled until the
      page is ready-->
    <script>
      $(document).ready(fillRecipeTemplate());
    </script>
  </head>
  <body>
      <%@ include file="../components/topbar.jsp"%>
      <div id="content">
        <div id="recipe-info">
          <div id="details">
            <!--Information about the reicipe, such as name, author, likes, tags, etc.-->
          </div>
          <div id="add-to-list-buttons">
          </div> 
          <br>
          <div id="image-description">
            <!--Used to adjust the flex styling of the elements in this div-->
            <div id="image-container">
              <!--The div to hold the image retrieved from the Datastore-->
            </div>
            <div id="description">
              <!--The text for the description-->
            </div>
          </div>
          <div id="ingredients">
            <!--The ingredient list for the recipe-->
          </div>
          <div id="steps">
            <!--The steps for the recipe-->
          </div>
        </div>
      </div>
  </body>
</html>