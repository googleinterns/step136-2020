
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
// Input is a Recipe
function deleteRecipe(recipe) {
  const params = new URLSearchParams();
  params.append("id", recipe.id);
  fetch("/delete-recipe", {method: "POST", body: params});
}
