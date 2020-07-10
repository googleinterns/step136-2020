
// loads all the recipes when the recipe loads
async function loadRecipes() {
  loadUserRecipes();
}

async function loadUserRecipes() {
  const response = await fetch('/list-private-recipes');
  const recipes = await response.json();

  let recipesDiv = document.getElementById("user-recipes");
  recipesDiv.innerHTML = "";
  
  let size = 0;
  if (Object.keys(recipes)) {
    for (let key of Object.keys(recipes)) {
        size++;
        let value = recipes[key];
        createUserRecipeCard(value);
    }
    if(size == 0){
        recipesDiv.innerText = "You have not uploaded any recipes yet.";
        recipesDiv.style.height = "100px";
    }
  }
}

createUserRecipeCard = (recipeInfo) => {
  let docDiv = document.getElementById("user-recipes");

  let recipeDiv = createElement("div", "", {"class": "recipe-card"});
  let imageDiv = createElement("div", "", {"class": "image-div"});
  let textDiv = createElement("div", "", {"class": "text-div"});

  let elementsToAddToImageDiv = [
    createImage(recipeInfo["name"], recipeInfo["imageBlobKey"]),
    createElement("button", "", {"class": "card-button icon top left far fa-trash-alt"}),
    createElement("button", "", {"class": "card-button icon top right fa fa-edit"}),
    createElement("button", "Planner ", {"class": "card-button bottom more-left planner-btn"}),
    createElement("button", "Cookbook ", {"class": "card-button bottom more-right cookbook-btn"}),
  ];
  console.log("elementsToAddToImageDiv");
  console.log(elementsToAddToImageDiv);

  let elementsToAddToTextDiv = [
    createElement("p", recipeInfo["name"], {"class": "recipe-card-name"}),
    createElement("p", recipeInfo["description"], {"class": "recipe-card-description"}),
  ];

  let allElementsToAdd = [imageDiv, textDiv];

  // Adds all the elements to the recipe card, then appends the recipe card
  // to the div
  elementsToAddToImageDiv.forEach(elem => imageDiv.appendChild(elem));
  elementsToAddToTextDiv.forEach(elem => textDiv.appendChild(elem));
  allElementsToAdd.forEach(elem => recipeDiv.appendChild(elem));
  docDiv.appendChild(recipeDiv);

  let plannerButtons = document.getElementsByClassName("planner-btn");
  let cookbookButtons = document.getElementsByClassName("cookbook-btn");
  const add1 = createElement("i", "add_circle_outline", {"class": "material-icons"});
  const add2 = createElement("i", "add_circle_outline", {"class": "material-icons"});
  for (let i = 0; i < plannerButtons.length; i++) {
    cookbookButtons[i].appendChild(add1);
    plannerButtons[i].appendChild(add2);
  }
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