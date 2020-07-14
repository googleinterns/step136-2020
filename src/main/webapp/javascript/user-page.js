// constants
const NO_PLANNER_RECIPES = "You have not added any recipes to your planner yet.";
const NO_COOKBOOK_RECIPES = "You have not added any recipes to your cookbook yet.";
const NO_USER_RECIPES = "You have not uploaded any recipes yet.";


// loads all the recipes when the recipe loads
async function loadRecipes() {
  loadUserRecipes();
}

async function loadUserRecipes() {
  const response = await fetch('/list-private-recipes');
  const recipes = await response.json();

  let recipesDiv = document.getElementById("user-recipes");
  recipesDiv.innerHTML = "";
  
  if (Object.keys(recipes)) {
    if (Object.keys(recipes).length == 0) {
      setUpDivWithNoRecipes("user-recipes", NO_USER_RECIPES);
    }
    else {
      for (let key of Object.keys(recipes)) {
        let value = recipes[key];
        createRecipeCard("user-recipes", value);

        // since these cards are the user recipe cards, they need edit/delete buttons
        let elementsToAddToImageDiv = [
          createElement("button", "", {"class": "card-button icon top left far fa-trash-alt"}),
          createElement("button", "", {"class": "card-button icon top right fa fa-edit"}),
        ];
        let imageDivs = document.getElementsByClassName("image-div");
        for (let i = 0; i < imageDivs.length; i++) {
          elementsToAddToImageDiv.forEach(elem => imageDivs[i].appendChild(elem));
        }
      }
      addDeleteFunctionality(recipes);
      // TODO: add edit functionality
      addEditFunctionality(recipes);
    }
  }
}

// adds delete functionality to the delete button in the recipe cards
function addDeleteFunctionality(recipes){
  const deleteButtons = document.getElementsByClassName('fa-trash-alt');
  const recipeCards = document.getElementsByClassName('recipe-card');
  // there are as many delete buttons as there are recipe cards
  for (let i = 0; i < deleteButtons.length; i++) {
    let recipe = recipes[i];
    let recipeCard = recipeCards[i];
    deleteButtons[i].addEventListener('click', () => {
      const deleteConfirmed = confirm("Are you sure you want to delete the " 
        + recipe.name + " recipe?\nThis action cannot be undone!");
      if (deleteConfirmed) {
        deleteRecipe(recipe);
        // Remove the recipe from the DOM.
        recipeCard.remove();
      }
      if (recipeCards.length == 0) {
        setUpDivWithNoRecipes("user-recipes", NO_USER_RECIPES);
      }
    });
  }
}

function addEditFunctionality(recipes){
  const editButtons = document.getElementsByClassName('fa-edit');
  const recipeCards = document.getElementsByClassName('recipe-card');
  // there are as many delete buttons as there are recipe cards
  for (let i = 0; i < editButtons.length; i++) {
    let recipe = recipes[i];
    let recipeCard = recipeCards[i];
    editButtons[i].addEventListener('click', () => {
      openModal("edit-recipe-modal");
      addExistingValuesToEditForm(recipe);
    });
  }
}

function addExistingValuesToEditForm(recipe) {
  document.getElementById("edit-name").value = recipe.name;
  document.getElementById("edit-description").value = recipe.description;
  // sets up tags
  if (recipe.tags.length > 0) {
    document.getElementById("edit-tags").value = recipe.tags[0];
  }
  for (let i = 1; i < recipe.tags.length; i++) {
    document.getElementById("edit-tags").value += ", " + recipe.tags[i];
  }
  // sets up ingredients
  if (recipe.ingredients.length > 0) {
    document.getElementById("edit-ingredients").value = recipe.ingredients[0];
  }
  for (let i = 1; i < recipe.ingredients.length; i++) {
    document.getElementById("edit-ingredients").value += "\n" + recipe.ingredients[i];
  }
  // sets up steps
  if (recipe.steps.length > 0) {
    document.getElementById("edit-steps").value = recipe.steps[0];
  }
  for (let i = 1; i < recipe.steps.length; i++) {
    document.getElementById("edit-steps").value += "\n" + recipe.steps[i];
  }
}

// takes in div id and message
// makes the recipes container bigger and gives it the message
// to use when there are no recipes in planner/cookbook/user-recipes
setUpDivWithNoRecipes = (divID, message) => {
  let recipesDiv = document.getElementById(divID);
  recipesDiv.innerText = message;
  recipesDiv.style.height = "100px";
}

// opens the recipe form modal
function openModal(id) {
  document.getElementById(id).style.display = "block";
  fetchBlobstoreUrl();
}

// not quite sure what this does; something with blobs
function fetchBlobstoreUrl() {
  fetch('/blobstore-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('recipe-form');
        messageForm.action = imageUploadUrl;
      });
}

// closes the recipe form modal
function closeModal(id) {
  document.getElementById(id).style.display = "none";
}

// tells the server to delete the recipe.
// input is a recipe js object
function deleteRecipe(recipe) {
  const params = new URLSearchParams();
  params.append("id", recipe.id);
  fetch("/delete-recipe", {method: "POST", body: params});
}
