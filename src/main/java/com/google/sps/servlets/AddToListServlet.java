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

    // the print statements will just be there until I add alerts
    Key key = KeyFactory.createKey("Recipe", id);
    if (keys.contains(key)) {
      remove(user, type, key);
      System.out.println("remove");
    } else {
      add(user, type, key);
      System.out.println("add");
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