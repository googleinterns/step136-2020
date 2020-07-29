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

/** Servlet responsible for adding recipe keys to user's planner/cookbook lists. */
@WebServlet("/manage-list")
public class ManageListServlet extends HttpServlet {

  // checks whether the recipe already exists in the user's list
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // gets values from request
    long id = Long.parseLong(request.getParameter("id"));
    String idToken = request.getParameter("idToken");
    String type = request.getParameter("type");

    boolean contains = false;

    // creates a user based on the passed in user id token
    User user = new User(idToken);
    // gets the appropriate list based on the passed in list type
    List<Key> keys;
    if (type.equals("cookbook")) {
      keys = user.getCookbookList();
    } else if (type.equals("planner")) {
      keys = user.getPlannerList();
    } else {
      keys = Collections.emptyList();
      System.out.println("AddToListServlet: invalid type");
    }

    // creates a key based on the passed in recipe ID
    Key key = KeyFactory.createKey("Recipe", id);
    // checks whether the key already exists in list
    if (keys.contains(key)) {
      contains = true;
    } else {
      contains = false;
    }

    response.setContentType("text/html;");
    response.getWriter().println(String.valueOf(contains));  
  }

  // adds a recipe key to user's list
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // gets values from request
    long id = Long.parseLong(request.getParameter("id"));
    String idToken = request.getParameter("idToken");
    String type = request.getParameter("type");
    String action = request.getParameter("action");

    // creates a key based on the passed in recipe ID
    Key key = KeyFactory.createKey("Recipe", id);
    // creates a user based on the passed in user id token
    User user = new User(idToken);

    // adds created key to appropriate list based on passed in list type
    if (type.equals("cookbook")) {
      if (action.equals("add")) {
        user.addCookbookKey(key);
      } else if (action.equals("remove")) {
        user.removeCookbookKey(key);
      } else {
        System.out.println("ManageListServlet: invalid action " + action);
      }
    } else if (type.equals("planner")) {
      if (action.equals("add")) {
        user.addPlannerKey(key);
      } else if (action.equals("remove")) {
        user.removePlannerKey(key);
      } else {
        System.out.println("ManageListServlet: invalid action " + action);
      }
    } else {
      System.out.println("AddToListServlet: invalid type " + type);
    }
  }
}