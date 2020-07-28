// constants
const NO_PLANNER_RECIPES = "You have not added any recipes to your planner yet.";
const NO_COOKBOOK_RECIPES = "You have not added any recipes to your cookbook yet.";
const NO_USER_RECIPES = "You have not uploaded any recipes yet.";
const USER_RECIPES_DIV = "user-recipes";
const PLANNER_RECIPES_DIV = "planner-recipes";
const COOKBOOK_RECIPES_DIV = "cookbook-recipes";

// Can be passed to API onload to garuntee auth2 is loaded before execution.
function initUserPageGoogleSignin() {
  initGoogleUserWithListener(loadRecipes);
}

// loads all the recipes when the recipe loads
async function loadRecipes() {
  loadUserRecipes();
  setUpDivWithNoRecipes("planner-recipes", NO_PLANNER_RECIPES);
  setUpDivWithNoRecipes("cookbook-recipes", NO_COOKBOOK_RECIPES);
  document.getElementById("idToken").value = getIdToken();
}


// loads the user made/uploaded recipes specifically from the 
// general createRecipeCard function and adds the necessary buttons
async function loadUserRecipes() {
  const response = await fetch('/list-user-recipes?idToken='+ getIdToken());
  const recipes = await response.json();

  let recipesDiv = document.getElementById(USER_RECIPES_DIV);
  recipesDiv.innerHTML = "";
  
  if (Object.keys(recipes)) {
    if (Object.keys(recipes).length == 0) {
      setUpDivWithNoRecipes(USER_RECIPES_DIV, NO_USER_RECIPES);
    }
    else {
      for (let key of Object.keys(recipes)) {
        let value = recipes[key];
        createRecipeCard(USER_RECIPES_DIV, value);

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
      let message = "Are you sure you want to delete the " + recipe.name + " recipe?\n";
      if (recipe.published) {
        message += "This recipe will no longer be able to be accessed by any user.\n";
      }
      message += "This action cannot be undone.";
      const deleteConfirmed = confirm(message);
      if (deleteConfirmed) {
        deleteRecipe(recipe);
        // Remove the recipe from the DOM.
        recipeCard.remove();
      }
      if (recipeCards.length == 0) {
        setUpDivWithNoRecipes(USER_RECIPES_DIV, NO_USER_RECIPES);
      }
    });
  }
}

// tells the server to delete the recipe.
// input is a recipe js object
function deleteRecipe(recipe) {
  const params = new URLSearchParams();
  params.append("id", recipe.id);
  fetch("/delete-recipe", {method: "POST", body: params});
}

// adds edit functionality to the edit button in the recipe cards
function addEditFunctionality(recipes) {
  const editButtons = document.getElementsByClassName('fa-edit');
  const recipeCards = document.getElementsByClassName('recipe-card');
  // there are as many edit buttons as there are recipe cards
  for (let i = 0; i < editButtons.length; i++) {
    let recipe = recipes[i];
    let recipeCard = recipeCards[i];
    editButtons[i].addEventListener('click', () => {
      openModal("edit-recipe-modal");
      addExistingValuesToEditForm(recipe);
    });
  }
}

// adds values of stored recipe to edit recipe form
function addExistingValuesToEditForm(recipe) {
  document.getElementById("recipeID").value = recipe.id;
  document.getElementById("edit-name").value = recipe.name;
  document.getElementById("edit-description").value = recipe.description;
  // sets up tags
  if (recipe.tags.length > 0) {
    document.getElementById("edit-tags").value = recipe.tags[0];
    for (let i = 1; i < recipe.tags.length; i++) {
      document.getElementById("edit-tags").value += ", " + recipe.tags[i];
    }
  }
  // sets up ingredients
  if (recipe.ingredients.length > 0) {
    document.getElementById("edit-ingredients").value = recipe.ingredients[0];
    for (let i = 1; i < recipe.ingredients.length; i++) {
      document.getElementById("edit-ingredients").value += "\n" + recipe.ingredients[i];
    }
  }
  // sets up steps
  if (recipe.steps.length > 0) {
    document.getElementById("edit-steps").value = recipe.steps[0];
    for (let i = 1; i < recipe.steps.length; i++) {
      document.getElementById("edit-steps").value += "\n" + recipe.steps[i];
    }
  }
  // TODO: figure out image value (low priority)
    
  // sets the default to the current value
  if (recipe.published) {
    setOption("first", "public");
    setOption("second", "private");
  } else {
    setOption("first", "private");
    setOption("second", "public");
  }
}

function setOption(id, text) {
  document.getElementById(id).value = text;
  document.getElementById(id).innerText = text;
}

// opens the recipe form modal
function openModal(id) {
  document.getElementById(id).style.display = "block";
  // edit-recipe-modal doesn't have image functionality rn
  if (id == "new-recipe-modal") {
    fetchBlobstoreUrl(id);
  }
}

// takes in div id and message
// makes the recipes container bigger and gives it the message
// to use when there are no recipes in planner/cookbook/user-recipes
setUpDivWithNoRecipes = (divID, message) => {
  let recipesDiv = document.getElementById(divID);
  recipesDiv.innerText = message;
  recipesDiv.style.height = "80px";
}

// not quite sure what this does; something with blobs and images
function fetchBlobstoreUrl(id) {
  fetch('/blobstore-upload-url?divID='+id)
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        let form;
        if (id == "new-recipe-modal") {
          form = document.getElementById("new-recipe-form");
        } else if (id == "edit-recipe-modal") {
          form = document.getElementById("edit-recipe-form");
        } else {
          console.log("invalid modal id");
        }
        form.action = imageUploadUrl;
      });
}

// closes the recipe form modal
function closeModal(id) {
  document.getElementById(id).style.display = "none";
}

