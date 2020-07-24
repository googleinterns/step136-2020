package com.google.sps.servlets; 
 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.sps.data.Recipe;
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
 
/** Servlet responsible for listing private recipes. */
@WebServlet("/list-user-recipes")
public class ListUserRecipesServlet extends HttpServlet {
   
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = request.getParameter("idToken");
    User user = new User(idToken);
    String authorID = user.getId();

    Filter authorFilter = new FilterPredicate("authorID", FilterOperator.EQUAL, authorID);
    Query query = new Query("Recipe").setFilter(authorFilter);
 
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
 
    List<Recipe> recipes = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String name = (String) entity.getProperty("name");
      String description = (String) entity.getProperty("description");
      String blobkey = (String) entity.getProperty("imageBlobKey");
      ArrayList<String> tags = (ArrayList<String>) entity.getProperty("tags");
      ArrayList<String> ingredients = (ArrayList<String>) entity.getProperty("ingredients");
      ArrayList<String> steps = (ArrayList<String>) entity.getProperty("steps");
      boolean published = (boolean) entity.getProperty("published");

      Recipe recipe = new Recipe(id, name, authorID, blobkey, description, tags, ingredients, steps, published, 0);
      recipes.add(recipe);
    }
 
    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(recipes));
  }
}
 

