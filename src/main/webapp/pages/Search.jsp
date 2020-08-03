<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Search Results</title>
    <%@ include file="../components/GlobalConfig.jsp"%>
  </head>
  <body onload="search()">
      <%@ include file="../components/topbar.jsp"%>
      <!--Use content to center page and mantain consistency-->
      <div id="content">
        <!--Use div to reference an area to fill with information regarding the query-->
        <!--TODO: When pagination is implemented part of the info rendered here
          will be the number of returned results-->
        <div id="search-details">
        </div>
        <!--Area where the main parts of the search page will be contained-->
        <div id="search-main-content">
          <!--This is the area where users will be able to add additional search
            params for authors, tags, and ingredients-->
          <div id="tag-box">
            <!--MVP implementation of additional search uses text areas and CSP-->
            <textarea id="tags-input" placeholder="Type tags to search"></textarea>
            <!--MVP implementation of additional search uses text areas and CSP-->
            <textarea id="author-input" placeholder="Type authors to search"></textarea>
            <!--Buttons implemented to make use of the tag box clear and easy-->
            <div id="tag-box-buttons">
              <!--Button that allows the user to clear all the tags the have 
                chosen for the query-->
              <input type="button" id="clear-tag-box" value="Clear" onclick="clearSearchOptions()">
              <!--Button that allows the user to apply all the search options
                they typed to the query-->
              <input type="button" id="update-query" value="Update" onclick="updateSearchResults()">
            </div>
          </div>
          <!--Area where actual recipe results are rendered on the page-->
          <div id="recipes-container">
          </div>
        </div>
      </div>
  </body>
</html>