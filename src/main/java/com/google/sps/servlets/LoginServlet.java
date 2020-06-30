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

import com.google.appengine.api.datastore.*;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Request: param "redirectUrl" for after login is finished (defaults to index.html).
    // Response: param "isUserLoggedIn" : boolean, "loginPageUrl" : String. 
    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        UserService userService = UserServiceFactory.getUserService();

        String redirectUrl = request.getParameter("redirectUrl");

        JsonObject json = new JsonObject();

        if (!userService.isUserLoggedIn()) {
            String loginPageUrl = userService.createLoginURL(redirectUrl);
            json.addProperty("loginPageUrl", loginPageUrl);
            json.addProperty("isUserLoggedIn", false);
        } else {
            json.addProperty("isUserLoggedIn", true);
        }

        response.getWriter().println(json.toString());
    }
}