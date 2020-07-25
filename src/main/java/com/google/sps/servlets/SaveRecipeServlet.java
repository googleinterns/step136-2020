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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.google.sps.data.User;
import java.io.IOException;
import java.lang.SecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Add recipe to user's list depending on the following parameters:
 * "listName" = "cookbook", "planner". The list to add to.
 * "recipeId" = Long
 *  userToken is the user's Google id_token provided by user-auth.js/getIdToken().
 * "userToken" = String
 */
@WebServlet("/save-recipe")
public class SaveRecipeServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String userToken = (String) request.getParameter("idToken");
    String listName = (String) request.getParameter("listName");
    long recipeId = Long.parseLong(request.getParameter("recipeId"));

    User currentUser = new User(userToken);

    Key recipeKey = KeyFactory.createKey("Recipe", recipeId);

    if (listName.equals("cookbook")) {
      currentUser.addCookbookKey(recipeKey);
    } else if (listName.equals("planner")) {
      currentUser.addPlannerKey(recipeKey);
    } else {
      System.out.println("Invalid listName " + listName + ". No action.");
    }
  }
}
