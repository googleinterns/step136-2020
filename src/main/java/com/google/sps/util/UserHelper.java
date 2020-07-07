package com.google.sps.util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserHelper {

  // returns: opaque String unique ID of the currently logged-in user.
  public static String getId() {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    return user.getUserId();
  }

  // returns: String email address of the currently logged-in user.
  public static String getEmail() {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    return user.getEmail();
  }

  public static boolean isUserLoggedIn() {
    UserService userService = UserServiceFactory.getUserService();
    return userService.isUserLoggedIn();
  }
}