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
        createElement(recipeInfo["name"], "p", {"class": "recipe-card-name"}),
        createElement(recipeInfo["description"], "p", {"class": "recipe-card-description"}),
    ];

    // Adds all the elements to the recipe card, then appends the recipe card
    // to the div
    allElementsToAdd.forEach(elem => recipeDiv.childNodes.push(elem));
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
    // Tries to create an HTML element with the given tag, otherwise throws an object with error info
    try {
        let htmlElement = document.createElement(htmlTag.toLowerCase());
    } catch (error) {
        throw {
            "extended message":"Cannot create an html element of type: " + htmlTag.toString() + "\n",
            "error": error
        };
    }

    let htmlText = document.createTextNode(object);
    htmlElement.appendChild(htmlText);

    // Goes through each option tries to add the attribute to the new HTML element.
    // If it cannot add the attribute, it throws an object with error info
    for (opt in tagOptions) {
        try {
            htmlElement.setAttribute(opt, tagOptions[opt]);
        } catch (error) {
            throw {
                "extended message": "There was an error trying to set element attibute: " +
                    opt.toString() + ":"  + tagOptions[opt].toString() + "\n",
                "error": error
            };
        }
    }
    return htmlElement;
}