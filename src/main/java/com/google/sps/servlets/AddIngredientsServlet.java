package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
@WebServlet("/add-ingredients")
public class AddIngredientsServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long recipeID = request.getParameter("recipeID");
    Key privateKey = KeyFactory.createKey("PrivateRecipe", privateRecipeID);
    Key publicKey = KeyFactory.createKey("PrivateRecipe", privateRecipeID);
    try {
      Entity recipeEntity = datastore.get(privateKey);
    } catch (EntityNotFoundException e) {
      try {
        Entity recipeEntity = datastore.get(publicKey);
      } catch (EntityNotFoundException e) {

      }
    }

    response.sendRedirect("/pages/UserPage.jsp");
  }
}