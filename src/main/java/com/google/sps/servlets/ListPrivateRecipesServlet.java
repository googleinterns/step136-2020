package com.google.sps.servlets; 
 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Recipe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/** Servlet responsible for listing private recipes. */
@WebServlet("/list-private-recipes")
public class ListPrivateRecipesServlet extends HttpServlet {
   
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // this Query seems to need the addSort method to work so rn it's arbitrarily by the name
    // it can be changed
    // TODO: filter by author ID
    Query query = new Query("PrivateRecipe").addSort("name", SortDirection.DESCENDING);
 
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
 
    List<Recipe> recipes = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
    //   TODO: check if logged in user's id == recipe's authorID
    //   String authorID = (String) entity.getProperty("authorID");
      long id = entity.getKey().getId();
      long publicRecipeID = (long) entity.getProperty("publicRecipeID");
      String name = (String) entity.getProperty("name");
      String description = (String) entity.getProperty("description");
      String blobkey = (String) entity.getProperty("imageBlobKey");
      ArrayList<String> tags = (ArrayList<String>) entity.getProperty("tags");
      ArrayList<String> ingredients = (ArrayList<String>) entity.getProperty("ingredients");
      ArrayList<String> steps = (ArrayList<String>) entity.getProperty("steps");
      boolean published = (boolean) entity.getProperty("published");

      Recipe recipe = new Recipe(id, name, blobkey, description, tags, ingredients, steps, published, 0);
      if (publicRecipeID != 0) {
        recipe.setPublicRecipeID(publicRecipeID);
      }
      recipes.add(recipe);
    }
 
    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(recipes));
  }
}
 

