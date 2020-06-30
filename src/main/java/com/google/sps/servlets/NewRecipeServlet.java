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

/** Servlet responsible for creating new recipes. */
@WebServlet("/new-recipe")
public class NewRecipeServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String recipeName = request.getParameter("recipeName");
    String tagsResponse = request.getParameter("tags");
    String ingredientsResponse = request.getParameter("ingredients");
    String stepsResponse = request.getParameter("steps");
    String privacy = request.getParameter("privacy");

    List<String> tags = new ArrayList<String>(Arrays.asList(tagsResponse.split(",", 0)));
    List<String> ingredients = new ArrayList<String>(Arrays.asList(ingredientsResponse.split("\n", 0)));
    List<String> steps = new ArrayList<String>(Arrays.asList(stepsResponse.split("\n", 0)));

    tags.removeAll(Arrays.asList("", null));
    ingredients.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));
    steps.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));

    for (String tag : tags) {
      tag.trim();
    }
    for (String ingredient : ingredients) {
      ingredient.trim();
    }
    for (String step : steps) {
      step.trim();
    }

    Entity recipeEntity = new Entity("PrivateRecipe");
    recipeEntity.setProperty("recipeName", recipeName);
    recipeEntity.setProperty("tags", tags);
    recipeEntity.setProperty("ingredients", ingredients);
    recipeEntity.setProperty("steps", steps);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(recipeEntity);

    response.sendRedirect("/pages/UserPage.jsp");
  }
}