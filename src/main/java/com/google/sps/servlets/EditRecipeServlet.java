package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.sps.util.FormHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    String nameResponse = request.getParameter("edit-name");
    String tagsResponse = request.getParameter("edit-tags");
    String descriptionResponse = request.getParameter("edit-description");
    String ingredientsResponse = request.getParameter("edit-ingredients");
    String stepsResponse = request.getParameter("edit-steps");
    // privacy will be used once we give users the option to make their recipes public
    // String privacy = request.getParameter("privacy");

    // gets saved values from DataStore
    String name = (String) recipeEntity.getProperty("name");
    String tags = (ArrayList<String>) recipeEntity.getProperty("tags");
    String description = (String) recipeEntity.getProperty("description");
    String ingredients = (ArrayList<String>) recipeEntity.getProperty("ingredients");
    String steps = (ArrayList<String>) recipeEntity.getProperty("steps");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    long id = Long.parseLong(idResponse);
    Key key = KeyFactory.createKey("PrivateRecipe", id);
    try {
      Entity recipeEntity = datastore.get(key);

      // if the name input in the form is not empty, the form value will be saved
      if (!nameResponse.equals("")) {
        name = nameResponse;
        name = name.trim();
      }
      // if the tags input in the form is not empty, the form value will be saved
      if (!tagsResponse.equals("")) {
        tags = FormHelper.separateByCommas(tagsResponse);
      }
      // if the description input in the form is not empty, the form value will be saved
      if (!descriptionResponse.equals("")) {
        description = descriptionResponse;
        description = description.trim();
      }
      // if the ingredients input in the form is not empty, the form value will be saved
      if (!ingredientsResponse.equals("")) {
        ingredients = FormHelper.separateByNewlines(ingredientsResponse);
      }
      // if the steps input in the form is not empty, the form value will be saved
      if (!stepsResponse.equals("")) {
        steps = FormHelper.separateByNewlines(stepsResponse);
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
    response.sendRedirect("/pages/UserPage.jsp");
  }
}