package com.google.sps.util;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserHelper {
    public static String getId() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        return user.getUserId();
    }

    public static String getEmail() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        return user.getEmail();
    }
}