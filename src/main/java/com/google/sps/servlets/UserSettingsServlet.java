package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// So far this servlet allows fetching and uploading a display name.
@WebServlet("/settings")
public class UserSettingsServlet extends HttpServlet {
  // This regex checks that user names are alphanumeric and (5 - 25) characters.
  static final String NAMEPATTERN = "^[\\w_ -]{5,25}";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = (String) request.getParameter("idToken");
    User user = new User(idToken);
    JsonObject json = new JsonObject();

    String displayName = user.getName();
    json.addProperty("displayName", displayName);

    response.setContentType("application/json");
    response.getWriter().print(json.toString());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = (String) request.getParameter("idToken");
    User user = new User(idToken);
    JsonObject json = new JsonObject();

    String nameInput = ((String) request.getParameter("nameInput")).trim();

    // No change.
    if (nameInput.equals(user.getName())) {
      return;
    }

    // Check agains regular expression, check for consecutive spaces, determine return message.
    if (!nameInput.matches(NAMEPATTERN)) {
      json.addProperty("success", false);
      json.addProperty("message", "Invalid character in requested name");
    } else if (nameInput.contains("  ")) {
      json.addProperty("success", false);
      json.addProperty("message", "Consecutive spaces not allowed.");
    } else {
      user.setName(nameInput);
      json.addProperty("success", true);
      json.addProperty("message", "Display name succesfully changed to: " + nameInput);
    }

    response.setContentType("application/json");
    response.getWriter().print(json.toString());
  }
}
