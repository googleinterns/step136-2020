package com.google.sps.servlets; 
 
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.google.sps.data.Recipe;
import com.google.sps.data.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/** Servlet responsible for listing private recipes. */
@WebServlet("/list-type-recipes")
public class ListTypeRecipesServlet extends HttpServlet {
   
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = request.getParameter("idToken");
    String type = request.getParameter("type").toLowerCase();
    User user = new User(idToken);

    List<Key> keys;
    if (type.equals("cookbook")) {
      keys = user.getCookbookList();
    } else if (type.equals("planner")) {
      keys = user.getPlannerList();
    } else {
      keys = Collections.emptyList();
      System.out.println("ListTypeRecipesServlet: invalid type");
    }

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    List<Recipe> recipes = new ArrayList<>();
    for (Key key : keys) {
      try {
        // TODO: do not include private recipes
        Entity entity = datastore.get(key);
        long id = key.getId();
        String name = (String) entity.getProperty("name");
        String description = (String) entity.getProperty("description");
        String blobkey = (String) entity.getProperty("imageBlobKey");

        Recipe recipe = new Recipe(id, name, blobkey, description);
        recipes.add(recipe);
      } catch (EntityNotFoundException e) {
        System.out.println("ListTypeRecipesServlet: Entity not found with key from user key list. This can happen when the recipe is deleted.");
      }
    }
 
    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(recipes));
  }
}
 

