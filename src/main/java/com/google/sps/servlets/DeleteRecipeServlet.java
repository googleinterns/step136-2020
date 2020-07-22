package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
    long publicRecipeID = Long.parseLong(request.getParameter("publicRecipeID"));
    boolean published = Boolean.parseBoolean(request.getParameter("published"));

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    // delete the private recipe
    Key privateKey = KeyFactory.createKey("PrivateRecipe", privateRecipeID);
    datastore.delete(privateKey);

    // if the private recipe had been published, it has a public recipe equivalent that must be deleted as well
    if (published) {
      Key publicKey = KeyFactory.createKey("PublicRecipe", publicRecipeID);
      datastore.delete(publicKey);
    }

    // TODO: delete blobs too
  }
}
