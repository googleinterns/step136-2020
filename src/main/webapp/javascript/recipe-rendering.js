/**
 * The function to load the main page and display content properly 
 */
mainPageLoad = () => {
  // TODO: implement the call to search and rendering functions here for the intial load of the
  // search page. This will be called everytime the page is refreshed.
}

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

  let elementsToAddToImageDiv = [
    createImage(recipeInfo["name"], recipeInfo["imageBlobKey"]),
    createElement("button", "Planner ", {"class": "card-button bottom more-left add-to-planner-btn"}),
    createElement("button", "Cookbook ", {"class": "card-button bottom more-right add-to-cookbook-btn"}),
    createElement("button", "Planner ", {"class": "card-button bottom more-left remove-from-planner-btn"}),
    createElement("button", "Cookbook ", {"class": "card-button bottom more-right remove-from-cookbook-btn"}),
  ];
  elementsToAddToImageDiv.forEach(elem => imageDiv.appendChild(elem));

  let elementsToAddToTextDiv = [
    createElement("p", recipeInfo["name"], {"class": "recipe-card-name"}),
    createElement("p", recipeInfo["description"], {"class": "recipe-card-description"}),
  ];
  elementsToAddToTextDiv.forEach(elem => textDiv.appendChild(elem));

  let allElementsToAdd = [imageDiv, textDiv];
  allElementsToAdd.forEach(elem => recipeDiv.appendChild(elem));
  docDiv.appendChild(recipeDiv);

  let addToPlannerButtons = document.getElementsByClassName("add-to-planner-btn");
  let addToCookbookButtons = document.getElementsByClassName("add-to-cookbook-btn");
  let removeFromPlannerButtons = document.getElementsByClassName("remove-from-planner-btn");
  let removeFromToCookbookButtons = document.getElementsByClassName("remove-from-cookbook-btn");
  // using plannerButtons.length is ok because plannerButtons and cookbookButtons will always be the same length
  for (let i = 0; i < addToPlannerButtons.length; i++) {
    const addToPlannerButton = addToPlannerButtons[i];
    const addToCookbookButton = addToCookbookButtons[i];
    const removeFromPlannerButton = removeFromPlannerButtons[i];
    const removeFromToCookbookButton = removeFromToCookbookButtons[i];
    // adds the plus  to planner/cookbook buttons
    addToPlannerButton.appendChild(createAddCircle());
    addToCookbookButton.appendChild(createAddCircle());
    removeFromPlannerButton.appendChild(createAddCircle());
    removeFromToCookbookButton.appendChild(createAddCircle());

    const id = recipeInfo["id"];
    const name = recipeInfo["name"];
    const idToken = getIdToken();
    addToPlannerButton.addEventListener('click', () => addToList(id, name, idToken, 'planner'));
    addToCookbookButton.addEventListener('click', () => addToList(id, name, idToken, 'cookbook'));
    removeFromPlannerButton.addEventListener('click', () => removeFromList(id, name, idToken, 'planner'));
    removeFromToCookbookButton.addEventListener('click', () => removeFromList(id, name, idToken, 'cookbook'));
    removeFromPlannerButton.style.display = "none";
    removeFromToCookbookButton.style.display = "none";
  }
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

  // If the object parameter is not undefined or an empty string, it 
  // creates a textnode and adds it to the HTML element
  if (object !== undefined && object !== "") {
    let htmlText = document.createTextNode(object);
    htmlElement.appendChild(htmlText);
  }

  // If the tagOptions object is not undefined or empty, it goes through each 
  // option and adds the attribute to the new HTML element.
  if (tagOptions !== undefined && Object.keys(tagOptions).length > 0) {
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
function addToList(id, name, idToken, type) {
  console.log("addToList", name);
  fetch('/add-list?id=' + id + "&idToken=" + idToken + "&type=" + type)
      .then(response => response.text()).then((contains) => {
    if ((/true/i).test(contains)) {
      alert("You have already added the " + name + " recipe to your " + type);
    } else {
      // tells the server to add the recipe's key to the user's cookbook/planner
      const params = new URLSearchParams();
      params.append("id", id);
      params.append("idToken", getIdToken());
      params.append("type", type);
      fetch("/add-list", {method: "POST", body: params});
    }
  });
  if (document.URL.includes("UserPage")) {
    location.reload();
  }
}

// asks the user to confirm that they want to remove the recipe from that list
// if yes, removes the recipe
function removeFromList(id, name, idToken, type) {
  console.log("removeFromList", name);
  fetch('/remove-list?id=' + id + "&idToken=" + idToken + "&type=" + type)
      .then(response => response.text()).then((contains) => {
    console.log(contains, (/true/i).test(contains))
    if ((/true/i).test(contains)) {
      const confirmedRemove = confirm("Are you sure you want to remove the " + name + " recipe from your " + type + "?");
      // tells the server to remove the recipe's key from the user's cookbook/planner
      if (confirmedRemove) {
        const params = new URLSearchParams();
        params.append("id", id);
        params.append("idToken", getIdToken());
        params.append("type", type);
        fetch("/remove-list", {method: "POST", body: params});
      }
    } 
  });
  if (document.URL.includes("UserPage")) {
    location.reload();
  }
}

function createAddCircle() {
  return createElement("i", "add_circle_outline", {"class": "material-icons"});
}