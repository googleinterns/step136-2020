package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for deleting recipes. */
@WebServlet("/delete-recipe")
public class DeleteRecipeServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long id = Long.parseLong(request.getParameter("id"));

    // PrivateRecipes are the user uploaded recipes so only PrivateRecipes can be deleted (right now)
    Key taskEntityKey = KeyFactory.createKey("PrivateRecipe", id);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.delete(taskEntityKey);
  }
}
