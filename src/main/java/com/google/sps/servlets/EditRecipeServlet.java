package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new recipes. */
@WebServlet("/edit-recipe")
public class EditRecipeServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException  {
    // get edits from form
    String idResponse = request.getParameter("recipeID");
    String name = request.getParameter("edit-name");
    String tagsResponse = request.getParameter("edit-tags");
    String description = request.getParameter("edit-description");
    String ingredientsResponse = request.getParameter("edit-ingredients");
    String stepsResponse = request.getParameter("edit-steps");
    // privacy will be used once we give users the option to make their recipes public
    // String privacy = request.getParameter("privacy");

    // declaring the lists
    ArrayList<String> tags;
    ArrayList<String> ingredients;
    ArrayList<String> steps;

    long id = Long.parseLong(idResponse);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Key key = KeyFactory.createKey("PrivateRecipe", id);
    try {
      Entity recipeEntity = datastore.get(key);

      // if the name input in the form is empty, the original value will be saved
      if (name.equals("")) {
        name = (String) recipeEntity.getProperty("name");
      } else {
        // formats the form response
        name.trim();
      }

        // if the tags input in the form is empty, the original value will be saved
      if (tagsResponse.equals("")) {
        tags = (ArrayList<String>) recipeEntity.getProperty("tags");
      } else {
        // splits the String responses from the form by commas, trims each member of array, and makes the resulting arrays into Lists
        tags = new ArrayList<String>(Arrays.asList(Arrays.stream(tagsResponse.split(",")).map(String::trim).toArray(String[]::new)));
        // removes any empty or null members from the list
        tags.removeAll(Arrays.asList("", null));
      }

      // if the description input in the form is empty, the original value will be saved
      if (description.equals("")) {
        description = (String) recipeEntity.getProperty("description");
      } else {
        // formats the form response
        description.trim();
      }

      // if the ingredients input in the form is empty, the original value will be saved
      if (ingredientsResponse.equals("")) {
        ingredients = (ArrayList<String>) recipeEntity.getProperty("tags");
      } else {
        ingredients = new ArrayList<String>(Arrays.asList(Arrays.stream(ingredientsResponse.split("\n")).map(String::trim).toArray(String[]::new)));
        ingredients.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));
      }

      // if the ingredients input in the form is empty, the original value will be saved
      if (stepsResponse.equals("")) {
        steps = (ArrayList<String>) recipeEntity.getProperty("tags");
      } else {
        steps = new ArrayList<String>(Arrays.asList(Arrays.stream(stepsResponse.split("\n")).map(String::trim).toArray(String[]::new)));
        steps.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));
      }

      recipeEntity.setProperty("name", name);
      recipeEntity.setProperty("tags", tags);
      recipeEntity.setProperty("description", description);
      recipeEntity.setProperty("ingredients", ingredients);
      recipeEntity.setProperty("steps", steps);
      recipeEntity.setProperty("imageBlobKey", (String) recipeEntity.getProperty("imageBlobKey"));

      datastore.put(recipeEntity);

    } catch (EntityNotFoundException e) {
        System.out.println("entity not found exception");
    }
    


    response.sendRedirect("/pages/UserPage.html");
  }
}