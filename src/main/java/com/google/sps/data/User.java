package com.google.sps.data;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.SecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.security.GeneralSecurityException;

public class User {
  // API variables:
  // Client ID is generated from Ali's google APIs credentials page.
  private static final String CLIENT_ID = "1034390229233-u07o0iaas2oql8l4jhe7fevpfsbrtv7n.apps.googleusercontent.com";
  // Payload class contains decrypted user information.

  private static final String PRIVATE_KIND = "Private";
  private static final String PUBLIC_KIND = "Public";
  private static final String COOKBOOK = "cookbook";
  private static final String PLANNER = "planner";
  private static final String DRAFTS = "drafts";

  /**
    Payload object properties:

    These seven fields are only included when the user has granted the "profile" and
    "email" OAuth scopes to the application.
    "sub" or Subject refers to the user-unique ID number.
    "sub" : "110169484474386276334"
    "email" : "testuser@gmail.com",
    "email_verified" : "true",
    "name" : "Test User",
    "picture" : "https://lh4.googleusercontent.com/-kYgzyAWpZzJ/ABCDEFGHI/AAAJKLMNOP/tIXL9Ir44LE/s99-c/photo.jpg",
    "given_name" : "Test",
    "family_name" : "User",
  */
  private Payload payload;

  /**

    */
  private Entity entity;

  /**
   * Verifies user and creates a User instance for accessing and adding user data.
   * throws SecurityException on failure, import from java.lang.SecurityException.
   * @Param idTokenString is the Google-user ID Token provided buy user-auth.js/getIdToken.
   * Token should be passed as URL Fetch argument from front end.
   */
  public User(String idTokenString) throws SecurityException {
    // GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
    // .Builder(UrlFetchTransport.getDefaultInstance(), new JacksonFactory())
    // .setAudience(Collections.singletonList(CLIENT_ID))
    // .build();

    // GoogleIdToken idToken;
    // try {
    //   idToken = verifier.verify(idTokenString);
    // } catch(IOException | GeneralSecurityException e) {
    //   throw new SecurityException("Failed to verify Google token", e);
    // }

    // payload = idToken.getPayload();

    Key userKey = KeyFactory.createKey("User", getId());
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    try {
      entity = datastore.get(userKey);
    } catch(EntityNotFoundException e) {
      entity = new Entity(userKey);
      entity.setProperty("displayName", "testman jones");
      entity.setProperty("cookbook", null);
      entity.setProperty("userRecipes", null);
      entity.setProperty("planner", null);
    }
  }

  /**
   * Returns the String user unique ID.
   */
  public String getId() {
    //return payload.getSubject();
    return "0000000000"
  }

  public String getName() {
    return (String) entity.getProperty("name");
  }

  public void setName(String name) {
    entity.setProperty("name", name);
    uploadEntity();
  }

  public List<Key> getCookbook();

  public List<Key> getUserRecipes();

  public List<Key> getPlanner();

  public void addCookbookKey(Key key);

  public void addPlannerKey(Key key);

  public void addDraftKey(Key key);

  public void removeCookbookKey(Key key);

  public void removePlannerKey(Key key);

  public void removeDraftKey(Key key);

  /**
   * Adds the current version of User entity to datastore.
   */
  private void uploadEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(entity);
  }

  /**

}
