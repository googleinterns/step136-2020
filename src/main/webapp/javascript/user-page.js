// constants
const NO_USER_RECIPES = "You have not uploaded any recipes yet.";
const USER_RECIPES_ID = "user-recipes";

// Listener that triggers when a new or different user signs in.
var userChanged = loadRecipes;

// Listener that triggers when sign-in status changes (but not when user changes).
var signInChanged = function(signedIn) {
  if (!signedIn) {
    window.location.href = "/pages/MainPage.jsp";
  }
}

// loads all the recipes when the recipe loads
async function loadRecipes() {
  loadUserRecipes();
  loadTypeRecipes("planner");
  loadTypeRecipes("cookbook");
  document.getElementById("idToken").value = getIdToken();
  setIcons();
}

// loads the user made/uploaded recipes specifically from the 
// general createRecipeCard function and adds the necessary buttons
async function loadUserRecipes() {
  if (!getIdToken()) {
    window.location.href = "/pages/MainPage.jsp";
  }
  const response = await fetch('/list-user-recipes?idToken='+ getIdToken());
  const recipes = await response.json();

  let recipesDiv = document.getElementById(USER_RECIPES_ID);
  recipesDiv.innerHTML = "";
  
  if (Object.keys(recipes)) {
    if (Object.keys(recipes).length == 0) {
      setUpDivWithNoRecipes(USER_RECIPES_ID, NO_USER_RECIPES);
    }
    else {
      for (let key of Object.keys(recipes)) {
        let value = recipes[key];
        createRecipeCard(USER_RECIPES_ID, value);

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
  setIcons();
}

// loads the recipes the user has added to cookbook
async function loadTypeRecipes(type) {
  if (!getIdToken()) {
    window.location.href = "/pages/MainPage.jsp";
  }
  const response = await fetch('/list-type-recipes?idToken='+ getIdToken() + "&type="+type);
  const recipes = await response.json();

  let recipesDivID = type + "-recipes";
  document.getElementById(recipesDivID).innerHTML = "";
  
  if (Object.keys(recipes)) {
    if (Object.keys(recipes).length == 0) {
      setUpDivWithNoRecipes(recipesDivID, "You have not added any recipes to your " + type + " yet.");
    }
    else {
      for (let key of Object.keys(recipes)) {
        let value = recipes[key];
        createRecipeCard(recipesDivID, value);
      }
      const recipesDiv = document.getElementById(recipesDivID);
      const addToListButtons = recipesDiv.getElementsByClassName("add-to-" + type + "-btn");
      const removeFromListButtons = recipesDiv.getElementsByClassName("remove-from-" + type + "-btn");
      // there are as many buttons as there are recipes
      for (let i = 0; i < recipes.length; i++) {
        const addToListButton = addToListButtons[i];
        const removeFromListButton = removeFromListButtons[i];

        addToListButton.style.display = "none";
        removeFromListButton.style.display = "inline-block";
      }
    }
  }
  setIcons();
}

// adds delete functionality to the delete button in the recipe cards
function addDeleteFunctionality(recipes){
  let recipeDiv = document.getElementById(USER_RECIPES_ID);
  const recipeCards = recipeDiv.getElementsByClassName('recipe-card');
  const deleteButtons = document.getElementsByClassName('fa-trash-alt');
  // there are as many delete buttons as there are recipe cards
  for (let i = 0; i < deleteButtons.length; i++) {
    let recipe = recipes[i];
    let recipeCard = recipeCards[i];
    let recipeID = recipeCard.getElementsByClassName("recipe-id")[0];

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
        removeRecipeCardFromLists(recipeID, "planner");
        removeRecipeCardFromLists(recipeID, "cookbook");
        recipeCard.remove();       
      }
      if (recipeCards.length == 0) {
        setUpDivWithNoRecipes(USER_RECIPES_ID, NO_USER_RECIPES);
      }
    });
  }
}

// input is recipe id and type of list
// removes the recipe card from the DOM
function removeRecipeCardFromLists(id, type) {
  const recipeDivID = type + "-recipes";
  let recipeDiv = document.getElementById(recipeDivID);
  let recipeCards = recipeDiv.getElementsByClassName('recipe-card');

  for (let i = 0; i < recipeCards.length; i++) {
    let recipeCard = recipeCards[i];
    let recipeID = recipeCard.getElementsByClassName("recipe-id")[0];
    if (recipeID.innerText == id.innerText) {
      recipeCard.remove();
    }
  }

  if (recipeCards.length == 0) {
    setUpDivWithNoRecipes(recipeDivID, "You have not added any recipes to your " + type + " yet.");
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

function closeShoppingList() {
  closeModal("shopping-list-modal");
  document.getElementById("shopping-list").innerHTML = "";
}

// Fetches list of ingredients from planner and writes them inside the shopping list modal.
function addShoppingList() {
  fetch("/shopping-list?idToken=" + getIdToken()).then(response => response.json()).then(shoppingList => {
    listDiv = document.getElementById("shopping-list");
    if (Object.keys(shoppingList).length > 0) {
      addAsList(shoppingList, listDiv, "Shopping List", false);
    } else {
      message = document.createElement("p");
      message.innerText = "Add to your Planner to start a Shopping List!";
      message.style.textAlign = "center";
      message.style.fontSize = "large";
      listDiv.appendChild(message);
    }
  })
}