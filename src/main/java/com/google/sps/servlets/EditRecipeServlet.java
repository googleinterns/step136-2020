package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // get edits from form
    String name = request.getParameter("edit-name");
    String tagsResponse = request.getParameter("edit-tags");
    String description = request.getParameter("edit-description");
    String ingredientsResponse = request.getParameter("edit-ingredients");
    String stepsResponse = request.getParameter("edit-steps");
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

    System.out.println("\nname value: " + name+"\n");

    // make filters to find specific recipe that was edited
    Filter nameFilter = new FilterPredicate("name", FilterOperator.EQUAL, name);
    Filter descriptionFilter = new FilterPredicate("description", FilterOperator.EQUAL, description);
    // TODO: further filter by author ID
    // Filter authorFilter = new FilterPredicate("authorID", FilterOperator.valueOf("EQUAL"), authorID));
    Filter composFilter = CompositeFilterOperator.and(nameFilter, descriptionFilter);

    // set filters to the query
    Query query = new Query("PrivateRecipe").setFilter(composFilter);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    Entity recipeEntity = results.asSingleEntity();

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
    System.out.println("got blobkeys from image input");

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

    datastore.put(recipeEntity);

    response.sendRedirect("/pages/UserPage.html");
  }
}