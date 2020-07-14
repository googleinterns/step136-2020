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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
  // API variables:
  // Client ID is generated from Ali's google APIs credentials page.
  private static final String CLIENT_ID = "1034390229233-u07o0iaas2oql8l4jhe7fevpfsbrtv7n.apps.googleusercontent.com";
  // Payload class contains decrypted user information.

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
    User Entity Properties:

    "displayName" : "Example Name"
    "cookbook" : [ "ID1", "ID2", "ID3" ]
    "userRecipes" : [ "ID1", "ID2", "ID3" ]
    "planner" : [ "ID1", "ID2", "ID3" ]
    "shoppingList" : [ "ID1", "ID2", "ID3" ]

    Note: ID values must be parsed into Long type.
    */
  private Entity entity;

  /**
   * 
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

    if (idToken != null) {
      payload = idToken.getPayload();
      System.out.println("User ID: " + payload.getSubject());
    }
  }

  /**
   * Returns the String user unique ID.
   */
  public String getId() {
    return payload.getSubject();
  }

  public long getDisplayName() {
    return (long) entity.getProperty("displayName");
  }

  public void setDisplayName(String name) {
    entity.setProperty("displayName", name);
    putEntity();
  }

  /* 
  Cookbook and Planner currently support only one type of Recipe entity (namely public).
  Likewise, userRecipes should only include private recipes.
  */

  public ArrayList<Long> getCookbook() {
    return getPropertyArrayList("cookbook");
  }

  public ArrayList<Long> getUserRecipes() {
    return getPropertyArrayList("userRecipes");
  }

  public ArrayList<Long> getPlanner() {
    return getPropertyArrayList("planner");
  }

  public void addRecipeToPlanner(long id) {
    addIdToRecipeList(id, "planner");
  }

  public void addRecipeToCookbook(long id) {
    addIdToRecipeList(id, "cookbook");
  }

  public void addRecipeToUserRecipes(long id) {
    addIdToRecipeList(id, "userRecipes");
  }

  public void removeRecipeFromPlanner(long id) {
    removeIdFromRecipeList(id, "planner");
  }

  public void removeRecipeFromCookbook(long id) {
    removeIdFromRecipeList(id, "cookbook");
  }

  public void removeRecipeFromUserRecipes(long id) {
    removeIdFromRecipeList(id, "userRecipes");
  }

  /**
   * Helper method for removing recipe IDs form specific user recipe list.
   * @Param id recipe ID to be removed from current User entity.
   * @Param recipeListType Property name of the list to removed from.
   */
  private void removeIdFromRecipeList(long id, String recipeListType) {
    Gson gson = new Gson();
    ArrayList<Long> list = getPropertyArrayList(recipeListType);
    list.remove(new Long(id));
    entity.setProperty(recipeListType, gson.toJson(list));
    putEntity();
  }

  /**
   * Helper method for adding recipe IDs to specific user recipe list.
   * @Param id recipe ID to be added to current User entity.
   * @Param recipeListType Property name of the list to add to.
   */
  private void addIdToRecipeList(long id, String recipeListType) {
    Gson gson = new Gson();
    ArrayList<Long> list = getPropertyArrayList(recipeListType);
    if (!list.contains(id)) {
      list.add(id);
    }
    entity.setProperty(recipeListType, gson.toJson(list));
    putEntity();
  }

  /**
   * Creates a datastore object and stores current entity in database.
   */
  private void putEntity() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(entity);
  }

  /**
   * Gets the (String) Json array of a given recipe list.
   * @Param property Property name of entity list to be obtained.
   * Returns Parsed array as ArrayList<Long>
   */
  private ArrayList<Long> getPropertyArrayList(String property) {
    Gson gson = new Gson();
    String jsonArray = (String) entity.getProperty(property);
    Type listType = new TypeToken<List<String>>(){}.getType();
    List<String> stringIds = gson.fromJson(jsonArray, listType);
    ArrayList<Long> longIds = new ArrayList<Long>();
    for (String str : stringIds) {
        longIds.add(Long.parseLong(str));
    }
    return longIds;
  }
}
