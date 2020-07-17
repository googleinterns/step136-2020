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

import java.io.IOException;

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
    User Entity Structure:

    Key = User(userID) {
      "name" : String,
      "cookbook" : ArrayList<Key)>,    *May contain public or private recipe kind.
      "planner" : ArrayList<Key)>,    *May contain public or private recipe kind.
      "drafts" : ArrayList<Key>    *May contain private recipe kind.
    }

    * Recipe lists maintain the order they were added in.
    */
  private Entity entity;
  private DatastoreService datastore;
  /**
   * Verifies user and creates a User instance for accessing and adding user data.
   * Throws SecurityException on failure, import from java.lang.SecurityException.
   * @Param idTokenString is the Google-user ID Token provided buy user-auth.js/getIdToken.
   * Token should be passed as URL Fetch argument from front end.
   */
  public User(String idTokenString) throws SecurityException {
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
        .Builder(UrlFetchTransport.getDefaultInstance(), new JacksonFactory())
        .setAudience(Collections.singletonList(CLIENT_ID))
        .build();

    GoogleIdToken idToken;
    try {
      idToken = verifier.verify(idTokenString);
    } catch(IOException | GeneralSecurityException e) {
      throw new SecurityException("Failed to verify Google token", e);
    }

    payload = idToken.getPayload();
    datastore = DatastoreServiceFactory.getDatastoreService();
    Key userKey = KeyFactory.createKey("User", getId());

    try {
      // Check if User already has a profile
      entity = datastore.get(userKey);
    } catch(EntityNotFoundException e) {
      // Create initial User instance, default name from idToken payload.
      entity = new Entity(userKey);
      entity.setProperty("name", (String) payload.get("name"));
    }
  }

  /**
   * Returns the String user unique ID.
   */
  public String getId() {
    return payload.getSubject();
  }

  public String getName() {
    return (String) entity.getProperty("name");
  }

  public void setName(String name) {
    entity.setProperty("name", name);
    uploadEntity();
  }

  public List<Key> getCookbookList() {
    return getRecipeList(COOKBOOK);
  }

  public List<Key> getPlannerList() {
    return getRecipeList(PLANNER);
  }

  public List<Key> getDraftList() {
    return getRecipeList(DRAFTS);
  }

  public void addCookbookKey(Key key) {
    addKey(key, COOKBOOK);
  }

  public void addPlannerKey(Key key) {
    addKey(key, PLANNER);
  }

  public void addDraftKey(Key key) {
    addKey(key, DRAFTS);
  }

  public void removeCookbookKey(Key key) {
    removeKey(key, COOKBOOK);
  }

  public void removePlannerKey(Key key) {
    removeKey(key, PLANNER);
  }

  public void removeDraftKey(Key key) {
    removeKey(key, DRAFTS);
  }

  // PUBLIC METHODS END HERE

  /**
   * Adds the current version of User entity to datastore.
   */
  private void uploadEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(entity);
  }

  /**
   * Helper method for retrieving ArrayList<Key> of given recipe list.
   */
  @SuppressWarnings("unchecked") // Compiler cannot verify generic property type.
  private ArrayList<Key> getRecipeList(String listName) {
    ArrayList<Key> arrayListValue = (ArrayList<Key>) entity.getProperty(listName);
    if (arrayListValue == null) {
      return new ArrayList<Key>();
    }
    return arrayListValue;
  }

  /**
   * Helper method for adding a key to a given recipe list.
   */
  private void addKey(Key key, String listName) {
    ArrayList<Key> keys = getRecipeList(listName);
    if (!keys.contains(key)) {
      keys.add(key);
      entity.setProperty(listName, keys);
      uploadEntity();
    }
  }

  /**
   * Helper methods for deleting a key from a given recipe list.
   */
  private void removeKey(Key key, String listName) {
    ArrayList<Key> keys = getRecipeList(listName);
    if (keys.remove(key)) {
      uploadEntity();
    }
  }
}
