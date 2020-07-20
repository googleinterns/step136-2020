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
    createElement("button", "Planner ", {"class": "card-button bottom more-left planner-btn"}),
    createElement("button", "Cookbook ", {"class": "card-button bottom more-right cookbook-btn"}),
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

  let plannerButtons = document.getElementsByClassName("planner-btn");
  let cookbookButtons = document.getElementsByClassName("cookbook-btn");
  const add1 = createElement("i", "add_circle_outline", {"class": "material-icons"});
  const add2 = createElement("i", "add_circle_outline", {"class": "material-icons"});
  // using plannerButtons.length is ok because plannerButtons and cookbookButtons will always be the same length
  for (let i = 0; i < plannerButtons.length; i++) {
    // adds the plus  to planner/cookbook buttons
    plannerButtons[i].appendChild(add1);
    cookbookButtons[i].appendChild(add2);

    // where the functionality for planner/cookbook buttons will go
    plannerButtons[i].addEventListener('click', () => {
      alert("You have clicked the add to planner button");
    });
    cookbookButtons[i].addEventListener('click', () => {
      alert("You have clicked the add to cookbook button");
    });
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