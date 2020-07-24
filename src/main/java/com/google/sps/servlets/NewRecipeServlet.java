package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.User;
import com.google.sps.util.FormHelper;
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
@WebServlet("/new-recipe")
public class NewRecipeServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // get responses from form
    String name = request.getParameter("name").trim();
    String tagsResponse = request.getParameter("tags").trim();
    String description = request.getParameter("description").trim();
    String ingredientsResponse = request.getParameter("ingredients").trim();
    String stepsResponse = request.getParameter("steps").trim();
    String privacy = request.getParameter("privacy");
    String idToken = request.getParameter("idToken");

    // prevents empty responses as being saved in datastore and redirects user back to UserPage
    // TODO: inform the user that they're missing stuff
    if (name.equals("") || tagsResponse.equals("") || description.equals("") || ingredientsResponse.equals("") || stepsResponse.equals("")) {
      response.sendRedirect("/pages/UserPage.jsp");
      return;
    }

    // splits the String responses from the form into lists
    List<String> tags = FormHelper.separateByCommas(tagsResponse);
    List<String> ingredients = FormHelper.separateByNewlines(ingredientsResponse);
    List<String> steps = FormHelper.separateByNewlines(stepsResponse);

    User user = new User(idToken);

    Entity recipeEntity = new Entity("Recipe");
    recipeEntity.setProperty("name", name);
    recipeEntity.setProperty("tags", tags);
    recipeEntity.setProperty("description", description);
    recipeEntity.setProperty("ingredients", ingredients);
    recipeEntity.setProperty("steps", steps);
    recipeEntity.setProperty("authorID", user.getId());
    recipeEntity.setProperty("published", false);

    // user has chosen to publish their recipe
    if (privacy.equals("public")) {
      recipeEntity.setProperty("published", true);
    } else {
      recipeEntity.setProperty("published", true);
    }

    // getUploads returns a set of blobs that have been uploaded 
    // the Map object is a list that associates the names of the upload fields to the blobs they contained
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("image");

    // user submitted form without selecting a file, so we can't get a URL. (dev server)
    // redirects user back to UserPage
    if (blobKeys == null || blobKeys.isEmpty()) {
      response.sendRedirect("/pages/UserPage.jsp");
      return;
    } else {
      BlobKey blobkey = blobKeys.get(0);
      BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobkey);
      // user submitted form without selecting a file, so we can't get a URL. (dev server)
      // redirects user back to UserPage
      if (blobInfo.getSize() == 0) {
        blobstoreService.delete(blobkey);
        response.sendRedirect("/pages/UserPage.jsp");
        return;
      } else {
        recipeEntity.setProperty("imageBlobKey", blobkey.getKeyString());
      }
    }
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(recipeEntity);

    response.sendRedirect("/pages/UserPage.jsp");
  }
}