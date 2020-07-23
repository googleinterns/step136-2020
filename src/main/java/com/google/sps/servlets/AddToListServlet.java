package com.google.sps.servlets;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for deleting recipes. */
@WebServlet("/add-list")
public class AddToListServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long id = Long.parseLong(request.getParameter("id"));
    long publicRecipeID = Long.parseLong(request.getParameter("publicRecipeID"));
    boolean published = Boolean.parseBoolean(request.getParameter("published"));
    String idToken = request.getParameter("idToken");
    String type = request.getParameter("type");

    User user = new User(idToken);
    List<Key> keys;
    if (type.equals("cookbook")) {
      keys = user.getCookbookList();
    } else if (type.equals("planner")) {
      keys = user.getPlannerList();
    } else {
      keys = Collections.emptyList();
      System.out.println("AddToListServlet: invalid type");
    }

    // recipe is a PublicRecipe
    if (published && publicRecipeID == 0) {
      Key key = KeyFactory.createKey("PublicRecipe", id);
      // if the key is already in the list, then remove it
      if (keys.contains(key)) {
        remove(user, type, key);
      } else { 
        add(user, type, key);
      }
    }
    else {  // recipe is a PrivateRecipe
      Key key = KeyFactory.createKey("PrivateRecipe", id);
      // if the key is already in the list, then remove it
      if (keys.contains(key)) {
        remove(user, type, key);
      } else { 
        add(user, type, key);
      }
    }
  }

  public static void remove(User user, String type, Key key) {
    if (type.equals("cookbook")) {
      user.removeCookbookKey(key);
    } else {
      user.removePlannerKey(key);
    }
  }

  public static void add(User user, String type, Key key) {
    if (type.equals("cookbook")) {
      user.addCookbookKey(key);
    } else {
      user.addPlannerKey(key);
    }
  }
}
