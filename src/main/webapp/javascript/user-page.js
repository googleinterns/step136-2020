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
      noRecipes("user-recipes", "You have not uploaded any recipes yet.");
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
    }
  }
}

function addDeleteFunctionality(recipes){
  // functionality of delete button
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
        noRecipes("user-recipes", "You have not uploaded any recipes yet.");
      }
    });
  }
}

// takes in div id and message
noRecipes = (divID, message) => {
  let recipesDiv = document.getElementById(divID);
  recipesDiv.innerText = message;
  recipesDiv.style.height = "100px";
}

// opens the recipe form modal
function openModal() {
  document.getElementById("recipe-modal").style.display = "block";
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
function closeModal() {
  document.getElementById("recipe-modal").style.display = "none";
}

// tells the server to delete the recipe.
// input is a recipe js object
function deleteRecipe(recipe) {
  const params = new URLSearchParams();
  params.append("id", recipe.id);
  fetch("/delete-recipe", {method: "POST", body: params});
}