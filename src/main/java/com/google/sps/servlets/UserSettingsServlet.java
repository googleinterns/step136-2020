package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/settings")
public class UserSettingsServlet extends HttpServlet {
  // The only reason this class exists rn is to save this regular expression I made.
  // It checks that user names are alphanumeric and (5 - 25) characters.
  // ([^\s\d]( ?){1}){5,25}

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {

  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {

  }
}
