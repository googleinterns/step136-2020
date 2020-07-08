// loads all the recipes when the recipe loads
async function loadRecipes() {
  loadUserRecipes();
}

async function loadUserRecipes() {
  const response = await fetch('/list-private-recipes');
  const recipes = await response.json();

  if (Object.keys(recipes)) {
    const recipesElement = document.getElementById('user-recipes');
    recipesElement.innerText = "";
    for (let key of Object.keys(recipes)) {
        let value = recipes[key];
        // createUserRecipeCard(value)
    }
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