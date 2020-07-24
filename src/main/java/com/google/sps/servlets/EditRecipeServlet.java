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
    String nameResponse = request.getParameter("edit-name").trim();
    String tagsResponse = request.getParameter("edit-tags").trim();
    String descriptionResponse = request.getParameter("edit-description").trim();
    String ingredientsResponse = request.getParameter("edit-ingredients").trim();
    String stepsResponse = request.getParameter("edit-steps").trim();
    String privacy = request.getParameter("privacy");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    long id = Long.parseLong(idResponse);
    Key key = KeyFactory.createKey("Recipe", id);
    try {
      Entity recipeEntity = datastore.get(key);

      // gets saved values from DataStore
      String name = (String) recipeEntity.getProperty("name");
      ArrayList<String> tags = (ArrayList<String>) recipeEntity.getProperty("tags");
      String description = (String) recipeEntity.getProperty("description");
      ArrayList<String> ingredients = (ArrayList<String>) recipeEntity.getProperty("ingredients");
      ArrayList<String> steps = (ArrayList<String>) recipeEntity.getProperty("steps");
      String imageBlobKey = (String) recipeEntity.getProperty("imageBlobKey");
      boolean published = (boolean) recipeEntity.getProperty("published");
      String authorID = (String) recipeEntity.getProperty("authorID");

      // if the name input in the form is not empty, the form value will be saved
      if (!nameResponse.equals("")) {
        name = nameResponse;
      }
      // if the tags input in the form is not empty, the form value will be saved
      if (!tagsResponse.equals("")) {
        tags = FormHelper.separateByCommas(tagsResponse);
      }
      // if the description input in the form is not empty, the form value will be saved
      if (!descriptionResponse.equals("")) {
        description = descriptionResponse;
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
      recipeEntity.setProperty("description", description);
      recipeEntity.setProperty("authorID", authorID);
      recipeEntity.setProperty("imageBlobKey", imageBlobKey);
      recipeEntity.setProperty("ingredients", ingredients);
      recipeEntity.setProperty("steps", steps);
      recipeEntity.setProperty("tags", tags);

      if (privacy.equals("public")) {
        recipeEntity.setProperty("published", true);
      } else {
        recipeEntity.setProperty("published", false);
      }

      datastore.put(recipeEntity);
    } catch (EntityNotFoundException e) {
      System.out.println("EditRecipeServlet: Private recipe entity not found with saved recipe id. This should never happen.");
    }
    response.sendRedirect("/pages/UserPage.jsp");
  }
}