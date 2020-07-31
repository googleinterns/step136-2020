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
import com.google.gson.Gson;
import com.google.sps.data.Recipe;
import com.google.sps.data.TestData;
import com.google.sps.data.UserQuery;
import com.google.sps.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Used to intitialize or remove all test data from the servlet.
 **/
@WebServlet("/adminAuth_test_congifureTestData")
public class TestingServlet extends HttpServlet {
  /**
   * Handles requests to delete or create new data by reading a url parameter "action"
   * Ideal future implementation is to have this servlet be able to handle complex requests to
   * initialize different types of data for specific types of tests
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (request.getParameter("action").equals("initialize")) {
      TestData.RecipeNameSearch.initializeData();
      response.getWriter().println("I returned from post and initialized");
    } else if (request.getParameter("action").equals("delete")) {
      Utils.SOP("NOT IMPLEMENTED YET");
    }
  }
}