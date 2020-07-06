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
    let recipeDiv = document.createElement("div");
    recipeDiv.setAttribute("class", "recipe-card");

    /**
     * Creates the html content to include in the recipe card div.
     * This approach was preferred to conserve space vs using 
     * document.createElement and other methods 
     */
    let htmlString =
        createElement(
            removeHTML(recipeInfo["name"]),
            "p", "class=\"recipe-card-name\"") +
        createElement(
            removeHTML(recipeInfo["description"]),
            "p", "class=\"recipe-card-description\"");

    recipeDiv.innerHTML = htmlString;
    docDiv.appendChild(recipeDiv);
}

/**
 * Takes an object which can be represented as a string or a string, an html
 * tag, and the html tag options, to create the html text. Returns the resulting
 * text.
 */
createElement = (object, htmlTag, tagOptions) => {
    return "<" + htmlTag + " " + tagOptions + ">" + object + "</" + htmlTag + ">";
}

/**
 * Takes a string and replaces any HTML tags with the html entity name, preventing
 * HTML injection while reducing the work done.
 */
removeHTML = (str) => {
    let replaced = str.replace(/\</g, "&lt");
    replaced = replaced.replace(/\>/g, "&gt");
    return replaced;
}