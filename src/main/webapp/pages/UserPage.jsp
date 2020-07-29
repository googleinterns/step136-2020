<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>User Page</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/style-userpage.css">
    <link rel="stylesheet" href="../css/style-sign-in.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="../javascript/user-page.js"></script>
    <script src="../javascript/user-auth.js"></script>
    <script src="../javascript/recipe-rendering.js"></script>
    <script src="https://apis.google.com/js/platform.js?onload=initUserPageGoogleSignin" async defer></script>
    <script src="https://kit.fontawesome.com/9d7d4957c5.js" crossorigin="anonymous"></script>
  </head>
  <body>
    <%@ include file="../components/topbar.jsp"%>
    <div id="content">
      <h1 class="center">My Page</h1>
      <div class="tab">
        <button class="tablinks active" onclick="openTab(recipes, shopping-list)">Saved Recipes</button>
        <button class="tablinks" onlick="openTab(shopping-list, recipes)">Shopping List</button>
      </div>
      <div class="tab-content" id="recipes">
        <!-- Planner content -->
        <div>
          <div id="header">
            <h2>Planner</h2>
          </div>
          <div class="recipes-container" id="planner-recipes"></div>
        </div>
        <!-- Cookbook content -->
        <div>
          <div id="header">
            <h2>Cookbook</h2>
          </div>
          <div class="recipes-container" id="cookbook-recipes"></div>
        </div>
        <!-- User Recipes content -->
        <div>
          <div id="header">
            <h2>My Recipes</h2>
            <button type="button" onClick="openModal('new-recipe-modal')">Add Recipe</button>
          </div>
          <div class="recipes-container" id="user-recipes"></div>
          <div class="modal" id="new-recipe-modal">
            <span onclick="closeModal('new-recipe-modal')" class="close">&times;</span>
            <form id="new-recipe-form" class="recipe-form" method="POST" enctype="multipart/form-data" action="/new-recipe">
              <h1 class="center">Create Recipe</h1>
              <input type="hidden" id="idToken" name="idToken">
              <label for="name" required>Recipe Name:</label><br>
              <input type="text" id="name" name="name">
              <br><br>
              <label for="tags">Tags:<br><i>Separate each tag with a comma.</i></label><br>
              <input type="text" id="tags" name="tags">
              <br><br>
              <label for="description" required>Description:</label><br>
              <textarea id="description" name="description" rows="2"></textarea>
              <br><br>
              <label for="ingredients" required>Ingredients:<br><i>Each ingredient should be on its own line.</i></label><br>
              <textarea id="ingredients" name="ingredients" rows="4"></textarea>
              <br><br>
              <label for="steps" required>Steps:<br><i>Each step should be on its own line. No need to number them.</i></label><br>
              <textarea id="steps" name="steps" rows="4"></textarea>
              <br><br>
              <label for="privacy">Choose a privacy setting:</label>
              <select id="privacy" name="privacy">
                <option value="private">private</option>
                <option value="public">public</option>
              </select>
              <br><br>
              <label for="image">Upload an image</label>
              <input type="file"  accept="image/*" name="image" id="image">
              <br><br>
              <div class="form-buttons">
                <button class="modal-btn" onclick="closeModal('new-recipe-modal')">Cancel</button>
                <input class="modal-btn" type="submit" id="submit-btn" value="Create Recipe">
              </div>
            </form>
          </div>
          <div class="recipes"></div>
            <div class="modal" id="edit-recipe-modal">
              <span onclick="closeModal('edit-recipe-modal')" class="close">&times;</span>
              <form id="edit-recipe-form" class="recipe-form" method="POST" action="/edit-recipe">
                <h1 class="center">Edit Recipe</h1>
                <input type="hidden" id="recipeID" name="recipeID">
                <label for="name" required>Recipe Name:</label><br>
                <input type="text" id="edit-name" name="edit-name">
                <br><br>
                <label for="tags">Tags:<br><i>Separate each tag with a comma.</i></label><br>
                <input type="text" id="edit-tags" name="edit-tags">
                <br><br>
                <label for="description" required>Description:</label><br>
                <textarea id="edit-description" name="edit-description" rows="2"></textarea>
                <br><br>
                <label for="ingredients" required>Ingredients:<br><i>Each ingredient should be on its own line.</i></label><br>
                <textarea id="edit-ingredients" name="edit-ingredients" rows="4"></textarea>
                <br><br>
                <label for="steps" required>Steps:<br><i>Each step should be on its own line. No need to number them.</i></label><br>
                <textarea id="edit-steps" name="edit-steps" rows="4"></textarea>
                <br><br>
                <label for="privacy">Choose a privacy setting:</label>
                <select id="privacy" name="privacy">
                  <option id="first"></option>
                  <option id="second"></option>
                </select>
                <br><br>
                <div class="form-buttons">
                  <button type="button" class="modal-btn" onclick="closeModal('edit-recipe-modal')">Cancel</button>
                  <input class="modal-btn" type="submit" id="submit-btn" value="Submit Changes">
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div id="shopping-cart">
      </div>
    </div>
  </body>
</html>