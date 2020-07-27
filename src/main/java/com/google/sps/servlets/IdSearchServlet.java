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
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.sps.data.Recipe;
import com.google.sps.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Handles requests for a Datastore search with a given ID.
 **/
@WebServlet("/search-id")
public class IdSearchServlet extends HttpServlet {
  /**
   * Handles the get request for Datastore search results. The url search parameters should only
   * have a recipe ID and the servlet will respond with the recipe info in JSON format to be rendered
   * client side.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Gets the id for the recipe from the URL params
    Long searchedId = Long.parseLong(request.getParameter("recipeId"));

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key recipeKey = KeyFactory.createKey("PublicRecipe", searchedId);

    // Tries to get the recipe from Datastore and send it back, otherwise sends a different response
    // the front end can handle to notify the user the recipe isn't available
    try {
      Entity datastoreRecipe = datastore.get(recipeKey);
      
      Recipe entityAsRecipe = new Recipe(
        searchedId,
        (String) datastoreRecipe.getProperty("name"),
        (String) datastoreRecipe.getProperty("imageBlobKey"),
        (String) datastoreRecipe.getProperty("description"),
        (ArrayList<String>) datastoreRecipe.getProperty("tags"),
        (ArrayList<String>) datastoreRecipe.getProperty("ingredients"),
        (ArrayList<String>) datastoreRecipe.getProperty("steps"),
        (boolean) datastoreRecipe.getProperty("published"),
        (Long) datastoreRecipe.getProperty("popularity")
      );

      // Sets the response type,converts the data into JSON, and sends the response
      response.setContentType("application:json");
      String recipeResponse = Utils.convertToJson(entityAsRecipe);
      response.getWriter().println(recipeResponse);
    } catch (EntityNotFoundException ENFE) {
      /** 
       * TODO: Because recipes can be deleted or edited to be private by the author, in this catch
       * block we want to send a response that can be handled to inform the user that the recipe is
       * cannot be retrieved anymore
       */
    }
  }
}
