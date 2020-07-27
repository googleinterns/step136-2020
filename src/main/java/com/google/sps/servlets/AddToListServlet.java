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
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long id = Long.parseLong(request.getParameter("id"));
    String idToken = request.getParameter("idToken");
    String type = request.getParameter("type");

    boolean contains = false;

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

    Key key = KeyFactory.createKey("Recipe", id);
    if (keys.contains(key)) {
      contains = true;
    } else {
      contains = false;
    }
    System.out.println("contains?: " + contains);

    // returns whether the doPost will remove the recipe or not
    response.setContentType("text/html;");
    response.getWriter().println(String.valueOf(contains));  
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long id = Long.parseLong(request.getParameter("id"));
    String idToken = request.getParameter("idToken");
    String type = request.getParameter("type");

    Key key = KeyFactory.createKey("Recipe", id);
    User user = new User(idToken);

    if (type.equals("cookbook")) {
      user.addCookbookKey(key);
    } else if (type.equals("planner")) {
      user.addPlannerKey(key);
    } else {
      System.out.println("AddToListServlet: invalid type");
    }
  }
}