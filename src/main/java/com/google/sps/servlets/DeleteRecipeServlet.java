package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for deleting recipes. */
@WebServlet("/delete-recipe")
public class DeleteRecipeServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long privateRecipeID = Long.parseLong(request.getParameter("id"));
    String name = request.getParameter("name");
    String description = request.getParameter("description");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    // delete the private recipe
    Key privateKey = KeyFactory.createKey("PrivateRecipe", privateRecipeID);
    datastore.delete(privateKey);

    // make filters to find the public recipe
    Filter nameFilter = new FilterPredicate("name", FilterOperator.EQUAL, name);
    Filter descriptionFilter = new FilterPredicate("description", FilterOperator.EQUAL, description);
    Filter combinedFilter = CompositeFilterOperator.and(nameFilter, descriptionFilter);

    // TODO: save public recipe ID to private recipe
    // get rid of other filters

    Query query = new Query("PublicRecipe").setFilter(combinedFilter);
    PreparedQuery results = datastore.prepare(query);
    
    try {
      Entity publicRecipeEntity = results.asSingleEntity();
      // publicRecipeEntity will only equal null if there is no entity left after the filters are applied
      if (publicRecipeEntity != null) {
        long publicRecipeID = publicRecipeEntity.getKey().getId();
        Key recipeEntitykey = KeyFactory.createKey("PublicRecipe", publicRecipeID);
        datastore.delete(recipeEntitykey);
      }
    } catch (TooManyResultsException e) {
        System.out.println("DeleteServlet: Too many results were found in public recipes.");
    }

  }
}
