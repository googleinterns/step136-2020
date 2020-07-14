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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * provides get method for adding a recipe ID to one of the three User recipe lists.
 * request parameters: "destination", "inputID"
 * "destination" may be one of three user recipe list: "cookbook", "userRecipes", "planner".
 * "inputID" must be the recipe ID to be added.
 */
@WebServlet("/user-action")
public class UserActionServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String destination = (String) request.getParameter("destination");
    long id = Long.parse((String) request.getParameter("inputID"));
    
    // Must be changed to implement distinct users with sign-in API.
    User user = User.getTestUser();

    if (destination.equals("cookbook")) {
      user.addRecipeToCookbook(id);
    } else if (destination.equals("userRecipes")) {
      user.addRecipeToUserRecipes(id);
    } else if (destination.equals("planner")) {
      user.addRecipeToPlanner(id);
    }
  }
}
