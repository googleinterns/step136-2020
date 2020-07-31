// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.sps.data.Recipe;
import com.google.sps.data.UserQuery;
import com.google.sps.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Handles requests for a Datastore search based on a query string with filters to use.
 * If a distrinction of where the request came from ever needs to be made, e.g. Search page or main
 * page, use request.getHeader("Referer").contains(<insert page path>) to do so.
 **/
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
  /**
   * Handles the get request for Datastore search results. Processes the url search parameters
   * and returns a list in JSON format with all the recipe information to be rendered client side.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Initializes a user query object to create the query filters
    UserQuery uQuery = new UserQuery(
      request.getParameter("query"),
      request.getParameter("tags"),
      request.getParameter("authors")
    );
    
    // Creates the new Query and adds the filter to be used in Datastore
    Query query = new Query("Recipe");
    query.setFilter(uQuery.createSearchFilter());

    // We want popular recipes first
    query.addSort("popularity", Query.SortDirection.DESCENDING);

    // Sets the size of the results to return
    final int resultsToReturn = 12;

    // Makes the query to the datastore and converts it to a list so it can operated on.
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery datastoreResult = datastore.prepare(query);
    List<Entity> recipes = datastoreResult.asList(FetchOptions.Builder.withLimit(resultsToReturn));

    // Create a list of recipe entity information to be sent to the client, and then iterate through
    // the datastore response to create the recipe objects and add them to the list
    ArrayList<Recipe> clientRecipeInfo = new ArrayList<Recipe>();
    for (Entity datastoreRecipe : recipes) {
      clientRecipeInfo.add(
        new Recipe(
          datastoreRecipe.getKey().getId(),
          (String) datastoreRecipe.getProperty("name"),
          (String) datastoreRecipe.getProperty("imageBlobKey"),
          (String) datastoreRecipe.getProperty("description")
        )
      );
    }

    // Sets the response type,converts the data into JSON, and sends the response
    response.setContentType("application:json");
    String recipeResponse = Utils.convertToJson(clientRecipeInfo);
    response.getWriter().println(recipeResponse);
  }
  
}