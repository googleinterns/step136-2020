
/**
 * Takes the recipe info of a recipe as a JS object and the div that the recipe
 * card will be added to. Creates only one recipe card. For the method to work,
 * recipeInfo is expected to have the following data saved as properties,
 * regardless of the explicit property name:
 * a recipe name, recipe tags,  recipe description, and recipe ID to access more
 * data on the recipe page.
 */
createRecipeCard = (divId, recipeInfo) => {
    let docDiv = document.getElementById(divId);

    // Creates the recipeDiv container and sets the class name
    let recipeDiv = createElement("div", "", {"class": "recipe-card"});

    // This is the list of all the elements which should be rendered on the recipe
    // card. To change how ellements are displayed, change the order in which this
    // list is initilaized
    let allElementsToAdd = [
        createElement("p", recipeInfo["name"], {"class": "recipe-card-name"}),
        createElement("p", recipeInfo["description"], {"class": "recipe-card-description"}),
    ];

    // Adds all the elements to the recipe card, then appends the recipe card
    // to the div
    allElementsToAdd.forEach(elem => recipeDiv.appendChild(elem));
    docDiv.appendChild(recipeDiv);
}

/**
 * Takes the recipe info of a recipe as a JS object and the div that the recipe
 * card will be added to. Creates only one recipe card. For the method to work,
 * recipeInfo is expected to have the following data saved as properties,
 * regardless of the explicit property name:
 * a recipe name, a recipe description, and a recipe
 */
createUserRecipeCard = (recipeInfo) => {
  let docDiv = document.getElementById("user-recipes");

  let recipeDiv = createElement("div", "", {"class": "recipe-card"});
  let imageDiv = createElement("div", "", {"class": "image-div"});
  let textDiv = createElement("div", "", {"class": "text-div"});

  let elementsToAddToImageDiv = [
    createImage(recipeInfo["name"], recipeInfo["imageBlobKey"]),
    createElement("button", "", {"class": "card-button icon top left far fa-trash-alt"}),
    createElement("button", "", {"class": "card-button icon top right fa fa-edit"}),
    createElement("button", "Planner", {"class": "card-button icon bottom more-left planner-btn"}),
    createElement("button", "Cookbook", {"class": "card-button icon bottom more-right cookbook-btn"}),
  ];

  let elementsToAddToTextDiv = [
    createElement("p", recipeInfo["name"], {"class": "recipe-card-name"}),
    createElement("p", recipeInfo["description"], {"class": "recipe-card-description"}),
  ];

  let allElementsToAdd = [imageDiv, textDiv];

  elementsToAddToImageDiv.forEach(elem => imageDiv.appendChild(elem));
  elementsToAddToTextDiv.forEach(elem => textDiv.appendChild(elem));
  allElementsToAdd.forEach(elem => recipeDiv.appendChild(elem));
  docDiv.appendChild(recipeDiv);
}

/**
 * Takes an object which can be represented as a string or a string, an html
 * tag, and the html tag options stored as an object. Returns the reference to the
 * constructed html element. 
 * Default values are set for object and tag options for instances where these aren't
 * needed, such as when making a new container div.
 */
createElement = (htmlTag, object = "", tagOptions = {}) => {
    // Tries to create an HTML element with the given tag
    let htmlElement = document.createElement(htmlTag.toLowerCase());

    let htmlText = document.createTextNode(object);
    htmlElement.appendChild(htmlText);

    // Goes through each option tries to add the attribute to the new HTML element.
    for (opt in tagOptions) {   
        htmlElement.setAttribute(opt, tagOptions[opt]); 
    }
    return htmlElement;
}

/**
 * Takes two objects which represent the name and the blobkey
 * Returns the reference to the constructed image element. 
 */
createImage = (name, blobkey) => {
  let imageElement = document.createElement('img');
  imageElement.src = "/serve?blobkey="+blobkey;
  imageElement.className = "recipe-card-image";
  imageElement.alt = name + " image";
  return imageElement;
}
