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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.sps.util.UserHelper;

@WebServlet("/user-status")
public class UserStatusServlet extends HttpServlet {

    // Response: returns some user information JSON from the current user's sign-in API profile.
    // Data: "isUserLoggedIn" : boolean
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        UserService userService = UserServiceFactory.getUserService();

        JsonObject userInfo = new JsonObject();
        userInfo.addProperty("isUserLoggedIn", userService.isUserLoggedIn());
        if (userInfo.get("isUserLoggedIn").getAsBoolean()) {
            System.out.println("User is logged in");
            // other properties may be added in the future.
        } else {
            System.out.println("User is not logged in");
        }

        response.getWriter().println(userInfo.toString());
    }
}
