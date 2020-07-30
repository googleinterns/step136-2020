/**
 * Sends the users data to the servlet for a query to be completed, then renders the
 * results on the page.
 * TODO: implement redirect to search page
 */
async function search() {
    let userQuery =  document.getElementById("search-box").value;
    document.getElementById("content").innerHTML = "";
    // Uncomment to initialize test data for search testing
    /*let tempForm = new FormData();
    tempForm.append("action", "initialize");
    let myInit = {
        body: new URLSearchParams(tempForm),
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        method: "POST",
    };
    fetch("/adminAuth_test_congifureTestData?", myInit);*/

    let response = await fetch("/search?query=" + userQuery);
    let responseText = await response.text();
    let recipeList = JSON.parse(responseText);

    recipeList.forEach(elem => createRecipeCard("content", elem));
}

/**
 * The function to load the main page and display content properly 
 */
mainPageLoad = () => {
  // TODO: implement the call to search and rendering functions here for the intial load of the
  // search page. This will be called everytime the page is refreshed.
}