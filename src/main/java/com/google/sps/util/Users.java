package com.google.sps.util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

// Class for helping get public user info based on query data.
public class Users {
  
  /**
   * returns the display name of the user with the provided ID.
   * @param authorID
   */
  public static String getAuthorName(String authorID) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key userKey = KeyFactory.createKey("User", authorID);
    Entity user;
    try {
      user = datastore.get(userKey)
    } catch {
      System.out.println("Invalid ID");
      return;
    }

    return user.getName();
  }
}