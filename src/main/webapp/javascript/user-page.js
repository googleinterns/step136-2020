
// loads all the recipes when the recipe loads
async function loadRecipes() {
  loadUserRecipes();
}

async function loadUserRecipes() {
  const response = await fetch('/list-private-recipes');
  const recipes = await response.json();

  if (Object.keys(recipes)) {
    for (let key of Object.keys(recipes)) {
        let value = recipes[key];
        createUserRecipeCard(value);
    }
  }
}

createUserRecipeCard = (recipeInfo) => {
  let docDiv = document.getElementById("user-recipes");

   // Creates the recipeDiv container and sets the class name
  let recipeDiv = createElement("div", "", {"class": "recipe-card"});

  // This is the list of all the elements which should be rendered on the recipe
  // card. To change how ellements are displayed, change the order in which this
  // list is initilaized
  let allElementsToAdd = [
    createImage(recipeInfo["name"], recipeInfo["imageBlobKey"]),
    createElement("p", recipeInfo["name"], {"class": "recipe-card-name"}),
    createElement("button", "Delete", {"class": "far fa-trash-alt"}),
    createElement("button", "Edit", {"class": "fa fa-edit"}),
    createElement("button", "Add to Planner", {"class": "add_circle_outline"}),
    createElement("button", "Add to Cookbook", {"class": "add_circle_outline"}),
    createElement("p", recipeInfo["name"], {"class": "recipe-card-name"}),
    createElement("p", recipeInfo["description"], {"class": "recipe-card-description"}),
  ];

  // Adds all the elements to the recipe card, then appends the recipe card
  // to the div
  allElementsToAdd.forEach(elem => recipeDiv.appendChild(elem));
  console.log(recipeDiv);
  docDiv.appendChild(recipeDiv);
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