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
  static final String namePattern = "^[\\w-_]+$";
  static final String consecutiveSpacePattern = " {2,}";

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

    String nameInput = (String) request.getParameter("nameInput");
    nameInput.trim();

    if (namePattern.matches(nameInput) && !consecutiveSpacePattern.matches(nameInput)) {
      user.setName(nameInput);
      json.addProperty("success", true);
    } else {
      if (!namePattern.matches(nameInput)) {
        json.addProperty("message", "Invalid character in requested name");
      } else if (consecutiveSpacePattern.matches(nameInput)) {
        json.addProperty("message", "Consecutive spaces not allowed.");
      } else {
        json.addProperty("message", "Invalid name input");
      }
      json.addProperty("success", false);
    }

    response.setContentType("application/json");
    response.getWriter().print(json.toString());
  }
}
