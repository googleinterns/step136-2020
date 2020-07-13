/**
 * Sends the users data to the servlet for a query to be completed.
 */
async function search() {
    let userQuery =  document.getElementById("search-box").value;

    let response = await fetch("/search?query=" + userQuery);
    console.log(response.text());
}