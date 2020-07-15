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

import com.google.gson.Gson;
import com.google.sps.data.User;
import java.io.IOException;
import java.lang.SecurityException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides get method for adding a recipe ID to one of the three User recipe lists.
 * Request parameters: "destination", "inputID", "idToken"
 * "destination" may be one of three user recipe list: "cookbook", "userRecipes", "planner".
 * "inputID" must contain the recipe ID to be added.
 * Google ID token is required for instantiation of User object, acquire from user-auth.js/getIdToken.
 */
@WebServlet("/user-action")
public class UserActionServlet extends HttpServlet {

  public static final String COOKBOOK = "cookbook";
  public static final String USER_RECIPES = "userRecipes";
  public static final String PLANNER = "planner";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String destination = (String) request.getParameter("destination");
    long id = Long.parseLong((String) request.getParameter("idInput"));
    String idToken =  (String) request.getParameter("idToken");
    
    User user;
    try {
      user = new User(idToken);
    } catch(SecurityException e) {
      System.out.println("ERROR: User verification failed.");
      return;
    }

    if (destination.equals(COOKBOOK)) {
      user.addRecipeToCookbook(id);
    } else if (destination.equals(USER_RECIPES)) {
      user.addRecipeToUserRecipes(id);
    } else if (destination.equals(PLANNER)) {
      user.addRecipeToPlanner(id);
    } else {
      System.out.println("USAGE ERROR: Invalid destination value.");
    }
  }
}
