package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet provides Get and Post request methods.
 * Post request initializes an HttpSession for storing a user's Google ID token.
 * Get request invalidates the user session, making the ID token inaccessible.
 */
@WebServlet("/session")
public class UserSessionServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Kill user's session by invalidating.
    HttpSession session = request.getSession(false); // arg : false returns null if session does not exist.
    if (session != null) {
      session.invalidate();
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Add the user's google ID token to the user's session.
    HttpSession session = request.getSession(true); // arg : true creates a session if one does no exist.
    String userToken = (String) request.getParameter("idToken");
    session.setAttribute("idToken", userToken);
  }
}
