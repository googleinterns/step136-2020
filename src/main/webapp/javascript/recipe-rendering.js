/**
 * The function to load the main page and display content properly 
 */
mainPageLoad = () => {
  
}

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
    if (object !== undefined || object !== "") {
      let htmlText = document.createTextNode(object);
      htmlElement.appendChild(htmlText);
    }

    // If the tagOptions object is not undefined or empty, it goes through each 
    // option and adds the attribute to the new HTML element.
    if (tagOptions !== undefined || Object.keys(tagOptions).length > 0) {
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
  imageElement.src = "/serve?blobkey="+blobkey;
  imageElement.alt = name + " image";
  return imageElement;
}