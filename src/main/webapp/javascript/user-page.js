
async function loadRecipes() {
console.log("check 1");
  loadUserRecipes();
}

async function loadUserRecipes() {
    console.log("check 2");
  const response = await fetch('/list-private-recipes');
  const recipes = await response.json();
  console.log(recipes);

  if (Object.keys(recipes)) {
    const recipesElement = document.getElementById('user-recipes');
    recipesElement.innerText = "";
    for (let key of Object.keys(recipes)) {
        let value = recipes[key];
        recipesElement.appendChild(createImage(value));
    }
    console.log(recipesElement);
  }
  
}

function createImage(value) {
  const imageElement = document.createElement('img');
  const blobkey = value.imageBlobKey;
  imageElement.src = "/serve?blobkey="+blobkey;
  console.log(imageElement);
  return imageElement;
}


function openModal() {
  document.getElementById("recipe-modal").style.display = "block";
  fetchBlobstoreUrl();
}

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

function closeModal() {
  document.getElementById("recipe-modal").style.display = "none";
}

// Tells the server to delete the recipe.
// Input is a recipe js object
function deleteRecipe(recipe) {
  const params = new URLSearchParams();
  params.append("id", recipe.id);
  fetch("/delete-recipe", {method: "POST", body: params});
}