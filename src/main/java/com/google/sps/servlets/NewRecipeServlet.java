package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
    String name = request.getParameter("name");
    String tagsResponse = request.getParameter("tags");
    String description = request.getParameter("description");
    String ingredientsResponse = request.getParameter("ingredients");
    String stepsResponse = request.getParameter("steps");
    // privacy will be used once we give users the option to make their recipes public
    // String privacy = request.getParameter("privacy");

    // prevents empty responses as being saved in datastore
    // redirects user back to UserPage
    // TODO: inform the user that they're missing stuff
    if (name.equals("") || tagsResponse.equals("") || description.equals("") || ingredientsResponse.equals("") || stepsResponse.equals("")){
      response.sendRedirect("/pages/UserPage.html");
      return;
    }

    // splits the String responses from the form by commas/newlines, trims each member of array, and makes the resulting arrays into Lists
    List<String> tags = new ArrayList<String>(Arrays.asList(Arrays.stream(tagsResponse.split(",")).map(String::trim).toArray(String[]::new)));
    List<String> ingredients = new ArrayList<String>(Arrays.asList(Arrays.stream(ingredientsResponse.split("\n")).map(String::trim).toArray(String[]::new)));
    List<String> steps = new ArrayList<String>(Arrays.asList(Arrays.stream(stepsResponse.split("\n")).map(String::trim).toArray(String[]::new)));

    // trims String responses
    name.trim();
    description.trim();
    
    // removes any empty, null, or newline members of the Lists
    tags.removeAll(Arrays.asList("", null));
    ingredients.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));
    steps.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));

    Entity recipeEntity = new Entity("PrivateRecipe");
    recipeEntity.setProperty("name", name);
    recipeEntity.setProperty("tags", tags);
    recipeEntity.setProperty("description", description);
    recipeEntity.setProperty("ingredients", ingredients);
    recipeEntity.setProperty("steps", steps);

    // getUploads returns a set of blobs that have been uploaded 
    // the Map object is a list that associates the names of the upload fields to the blobs they contained
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("image");
    boolean noImage = false;

    // user submitted form without selecting a file, so we can't get a URL. (dev server)
    // redirects user back to UserPage
    if (blobKeys == null || blobKeys.isEmpty()) {
      response.sendRedirect("/pages/UserPage.html");
      return;
    } else {
      BlobKey blobkey = blobKeys.get(0);
      BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobkey);
      // user submitted form without selecting a file, so we can't get a URL. (dev server)
      // redirects user back to UserPage
      if (blobInfo.getSize() == 0) {
        blobstoreService.delete(blobkey);
        response.sendRedirect("/pages/UserPage.html");
        return;
      } else {
        recipeEntity.setProperty("imageBlobKey", blobkey.getKeyString());
      }
    }

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(recipeEntity);

    response.sendRedirect("/pages/UserPage.html");
  }
}