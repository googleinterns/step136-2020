/**
 * Sends the users data to the servlet for a query to be completed, then renders the
 * results on the page.
 * TODO: implement redirect to search page
 */
async function search() {
    // Uncomment to initialize test data for search testing
    /*let tempForm = new FormData();
    tempForm.append("action", "initialize");
    let myInit = {
        body: new URLSearchParams(tempForm),
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        method: "POST",
    };
    fetch("/adminAuth_test_congifureTestData?", myInit);*/

    const searchQuery = (new URL(document.location)).searchParams.get("query");

    const response = await fetch("/search?query=" + searchQuery);
    const responseText = await response.text();
    const recipeList = JSON.parse(responseText);

    recipeList.forEach(elem => createRecipeCard("content", elem));
}

/**
 * Redirects to the search page with the query in the URL search params.
 */
redirectSearchPage = () => {
  const userQuery =  document.getElementById("search-box").value;
  window.location.assign("/pages/Search.jsp?query=" + userQuery);
}

/**
 * This function is the onclick function for the Update button in the tag box.
 * This function does the following (with helper functions):
 * Gets the text from text areas -> seperates everything by commas and formats
 * the sring list -> makes new fetch request for query -> updates the query 
 * details section -> clears the search results div -> uses the parsed response
 * to fill in the cleared area with new recipe cards.
 */
async function updateSearchResults() {
  // The text versions of the authors and tags the user wants to search for
  // NOTE: tagsText here means tags and ingredients together
  const authorsText = document.getElementById("author-input").value;
  const tagsText = document.getElementById("tags-input").value;

  // The constructed params which are to be added to the fetch request
  const authors = csvToURLParam("authors", authorsText);
  const tags = csvToURLParam("tags", tagsText);
  const query = "query=" + (new URL(document.location)).searchParams.get("query");
  const response = await fetch("/search?" + query + "&" + authors + "&" + tags);
  const responseText = await response.text();
  const recipeList = JSON.parse(responseText);
  console.log(recipeList);
}

/**
 * Clears all the values from the textareas so the user can begin typing new
 * query options.
 */
clearSearchOptions = () => {
  document.getElementById("author-input").value = "";
  document.getElementById("tags-input").value = "";
}

/**
 * The function takes in a string which can be separated as CSVs and a URL param
 * name, and returns a string which can be added as a URL param. For example:
 * csvToURLParm("tags", "easy, meat") => "tags=easy%2Cmeat"
 */
csvToURLParam = (paramName, csvString) => {
  const csvAsList = csvString.toLowerCase().split(",").map(str => str.trim());
  /**
   * We want to convert the CSV values in the list to a single string where each
   * new element is joined by a ",", and can be used to send params in a fetch request
   * along with the original query
   */
  return paramName + "=" + csvAsList.join(",");
}

/**
 * The function to load the main page and display content properly 
 */
mainPageLoad = () => {
  // TODO: implement the call to search and rendering functions here for the intial load of the
  // search page. This will be called everytime the page is refreshed.
}
