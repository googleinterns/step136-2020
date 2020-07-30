/**
 * Takes the recipe info of a recipe as a JS object and the div that the recipe
 * card will be added to. Creates only one recipe card. For the method to work,
 * recipeInfo is expected to have the following data saved as properties,
 * regardless of the explicit property name:
 * a recipe name, a recipe description, and a recipe
 */
createRecipeCard = (divID, recipeInfo) => {
  let docDiv = document.getElementById(divID);

  let recipeDiv = createElement("div", "", {"class": "recipe-card"});
  let imageDiv = createElement("div", "", {"class": "image-div"});
  let textDiv = createElement("div", "", {"class": "text-div"});

  const id = recipeInfo["id"];
  const name = recipeInfo["name"];
  const idToken = getIdToken();

  const addToPlannerButton = createElement("button", " Planner", {"class": "card-button action-button bottom more-left fas fa-plus add-to-planner-btn"});
  addToPlannerButton.addEventListener('click', () => manageList("add", id, name, idToken, 'planner'));

  const addToCookbookButton = createElement("button", " Cookbook", {"class": "card-button action-button bottom more-right fas fa-plus add-to-cookbook-btn"});
  addToCookbookButton.addEventListener('click', () => manageList("add", id, name, idToken, 'cookbook'));

  const removeFromPlannerButton = createElement("button", " Planner ", {"class": "card-button action-button bottom more-left fa fa-remove remove-from-planner-btn"});
  removeFromPlannerButton.addEventListener('click', () => manageList("remove", id, name, idToken, 'planner'));
  removeFromPlannerButton.style.display = "none";

  const removeFromToCookbookButton = createElement("button", " Cookbook ", {"class": "card-button action-button bottom more-right fa fa-remove remove-from-cookbook-btn"});
  removeFromToCookbookButton.addEventListener('click', () => manageList("remove", id, name, idToken, 'cookbook'));
  removeFromToCookbookButton.style.display = "none";

  let elementsToAddToImageDiv = [ 
    createImage(recipeInfo["name"], recipeInfo["imageBlobKey"]), 
    addToPlannerButton, addToCookbookButton, removeFromPlannerButton, removeFromToCookbookButton,
  ];
  elementsToAddToImageDiv.forEach(elem => imageDiv.appendChild(elem));
  let elementsToAddToTextDiv = [
    createElement("button", recipeInfo["name"], 
      {"class": "recipe-card-name", 
      "onClick": "redirectRecipePage(" + recipeInfo["id"].toString() + ")"}),
    createElement("p", recipeInfo["description"], {"class": "recipe-card-description"}),
  ];
  elementsToAddToTextDiv.forEach(elem => textDiv.appendChild(elem));

  let allElementsToAdd = [imageDiv, textDiv];
  allElementsToAdd.forEach(elem => recipeDiv.appendChild(elem));
  docDiv.appendChild(recipeDiv);
}

/**
 * Takes the id for a recipe in Datastore, and redirects to the recipe page template
 * with the recipe ID as a search param.
 */
function redirectRecipePage(id) {
  window.location.assign("/pages/RecipePageTemplate.jsp?id=" + id.toString());
}

/**
 * Function for loading the recipe template page based on the id in the URL
 * search params.
 */
async function fillRecipeTemplate() { 
  const recipeId = (new URL(document.location)).searchParams.get("id");

  // Gets the servlet data for the recipe
  // TODO: put this code in an if statement to handle recipe not found scenarios
  let response = await fetch("/search-id?recipeId=" + recipeId.toString());
  let responseText = await response.text();
  let recipeInfo = JSON.parse(responseText);

  let detailsSection = document.getElementById("details");
  let imageSection = document.getElementById("image-container");
  let descriptionSection = document.getElementById("description");
  let ingredientSection = document.getElementById("ingredients");
  let stepsSection = document.getElementById("steps");

  addRecipeInfo(recipeInfo, detailsSection);
  imageSection.appendChild(createImage(recipeInfo["name"], recipeInfo["imageBlobKey"]));
  descriptionSection.appendChild(createElement(
      "p", recipeInfo["description"], {"id": "description-text"}));
  addAsList(recipeInfo["ingredients"], ingredientSection, "Ingredients");
  addAsList(recipeInfo["steps"], stepsSection, "Steps");
}

/**
 * Creates the HTML for the information about the recipe and adds it to the div.
 * Current implementation adds the recipe name and the author.
 * Takes the recipe object and div (not div name) being used.
 */
addRecipeInfo = (recipeObj, infoDiv) => {
  let elementsForDiv = [
    createElement("h1", recipeObj["name"], {"id": "recipe-name"})/*,
    createElement("h3", recipeObj["author"], {"id": "recipe-author"})*/
    // Uncomment once the backend can handle sending author info too
  ];
  
  // Adds all the recipe info to the passed div
  elementsForDiv.forEach(elem => infoDiv.appendChild(elem));
}

/**
 * Creates the HTML for some part of the recipe which needs to shown as a list
 * (ingredients, steps, etc.) and adds it to the given div.
 * Takes a list and div (not div name) being used.
 */
addAsList = (listObj, listDiv, listName) => {
  // Constructs the base of how all item created in the function will be 
  // named for CSS
  let cssRef = listName.toLowerCase() + "-list";

  // Creates the title for the list, and the list element
  let listForDiv = createElement("ol", "", {"id": cssRef})
  let listTitle = createElement("h2", listName, {"id": cssRef + "-title"});
  
  // Goes through all the objects in listObj and adds them as items to listForDiv
  listObj.forEach(item => listForDiv.appendChild(
    createElement("li", item, {"class": cssRef + "-item"})));
  
  listDiv.appendChild(listTitle);
  listDiv.appendChild(listForDiv);
}

/**
 * Takes an object which can be represented as a string or a string, an html
 * tag, and the html tag options stored as an object. Returns the reference to the
 * constructed html element. 
 * Default values are set for object and tag options for instances where these aren't
 * needed, such as when making a new container div.
 */
createElement = (htmlTag, object = "", tagOptions = {}) => {
  // Create an HTML element with the given tag
  let htmlElement = document.createElement(htmlTag.toLowerCase());

  // If the object parameter is not null or an empty string, it 
  // creates a textnode and adds it to the HTML element
  if (object !== null && object !== "") {
    let htmlText = document.createTextNode(object);
    htmlElement.appendChild(htmlText);
  }

  // If the tagOptions object is not null or empty, it goes through each 
  // option and adds the attribute to the new HTML element.
  if (tagOptions !== null && Object.keys(tagOptions).length > 0) {
    for (opt in tagOptions) {   
      htmlElement.setAttribute(opt, tagOptions[opt]); 
    }
  }
  return htmlElement;
}

/**
 * Takes two objects which represent the name and the blobkey
 * Returns the reference to the constructed image element. 
 */
createImage = (name, blobkey) => {
  let imageElement = document.createElement('img');
  imageElement.alt = name + " image";
  imageElement.className = "recipe-card-image";
  imageElement.src = "/serve?blobkey="+blobkey;
  return imageElement;
}

// lets the user know if they have already added a recipe to a particular list
// otherwise adds the recipe
function manageList(action, id, name, idToken, type) {
  fetch("/manage-list?id=" + id + "&idToken=" + idToken + "&type=" + type)
      .then(response => response.text()).then((contains) => {
    if ((/true/i).test(contains)) {
      if (action == "add") {
        // if the user is trying to add a recipe and the list already contains it
        // alerts the user that the recipe is already in the list
        alert("You have already added the " + name + " recipe to your " + type);
      } else {
        // if the user is trying to remove a precipe and the list contains it
        // asks the user to confirm that they wish to remove the recipe
        const confirmedRemove = confirm("Are you sure you want to remove the " + name + " recipe from your " + type + "?");
        // tells the server to remove the recipe's key from the user's cookbook/planner
        if (confirmedRemove) {
          const params = new URLSearchParams();
          params.append("action", action);
          params.append("id", id);
          params.append("idToken", getIdToken());
          params.append("type", type);
          fetch("/manage-list", {method: "POST", body: params});
          if (document.URL.includes("UserPage")) {
            location.reload();
          }
        }
      }
    } else {
      if (action == "add") {
        // if the user is trying to add a recipe not already in the list
        const params = new URLSearchParams();
        params.append("action", action);
        params.append("id", id);
        params.append("idToken", getIdToken());
        params.append("type", type);
        fetch("/manage-list", {method: "POST", body: params});
        if (document.URL.includes("UserPage")) {
          location.reload();
        }
      }
    }
  });
}
