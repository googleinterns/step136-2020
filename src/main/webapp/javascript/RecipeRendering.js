/**
 * Takes the recipe info of a recipe as a JS object and the div that the recipe
 * card will be added to. Creates only one recipe card. For the method to work,
 * recipeInfo is expected to have the following data saved as properties,
 * regardless of the explicit property name:
 * a recipe name, recipe tags,  recipe description, and recipe ID to access more
 * data on the recipe page.
 */
createRecipeCard = (divId, recipeInfo) => {
    console.log(divId);
    let docDiv = document.getElementById(divId);

    // Creates the recipeDiv container and sets the class name
    let recipeDiv = document.createElement("div");
    recipeDiv.setAttribute("class", "recipe-card");

    let htmlString =
        createElement(recipeInfo["name"], "p", "class=\"recipe-card-name\"") +
        createElement(recipeInfo["description"], "p", "class=\"recipe-card-description\"");

    recipeDiv.innerHTML = htmlString;
    docDiv.appendChild(recipeDiv);
}

createElement = (object, htmlTag, tagOptions) => {
    return "<" + htmlTag + " " + tagOptions + ">" + object + "</" + htmlTag + ">";
}

load = () => {
    createRecipeCard("content", {"name":"test", "description":"this is a test"});
    return;
}