package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
  static final String consecutiveSpacePattern = " {2,}"

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    String idToken = (String) request.getParameter("idToken");
    User user = new User(idToken);
    String displayName = user.getName();
    reponse.setAttribute("displayName", displayName);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    reponse.setContentType("application/json");
    String idToken = (String) request.getParameter("idToken");
    User user = new User(idToken);

    String nameInput = (String) request.getParameter("nameInput");
    nameInput.trim();

    if (namePattern.isMatch(nameInput) && !consecutiveSpacePattern.isMatch(nameInput)) {
      user.setName(nameInput);
      response.setAttribute("success", true);
    } else {
      if (!namePattern.isMatch(nameInput)) {
        reponse.setAttribute("message", "Invalid character in requested name");
      } else if (consecutiveSpacePattern.isMatch(nameInput)) {
        response.setAttribute("message", "Consecutive spaces not allowed.");
      } else {
        response.setAttribute("message", "Invalid name input").
      }
      response.setAttribute("success", false)
    }
  }
}
