package com.google.sps.data;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

// Class representing a User
public class User {
  // API variables:
  // client ID is generated from Ali's google APIs credentials page.
  private static String CLIENT_ID = "1034390229233-u07o0iaas2oql8l4jhe7fevpfsbrtv7n.apps.googleusercontent.com";
  // Payload class contains decrypted user information.
  private Payload payload;

  /*
    // User Entity Properties:

    "displayName" : "Example Name"
    "cookbook" : [ "longId1", "longId2", "longId3" ]
    "userRecipes" : [ "longId1", "longId2", "longId3" ]
    "planner" : [ "longId1", "longId2", "longId3" ]
    "shoppingList" : [ "longId1", "longId2", "longId3" ]

    // Properties that can be accessed from Payload object:

    // These seven fields are only included when the user has granted the "profile" and
    // "email" OAuth scopes to the application.
    // "sub" or Subject refers to the user-unique ID number.
    "sub" : "110169484474386276334"
    "email": "testuser@gmail.com",
    "email_verified": "true",
    "name" : "Test User",
    "picture": "https://lh4.googleusercontent.com/-kYgzyAWpZzJ/ABCDEFGHI/AAAJKLMNOP/tIXL9Ir44LE/s99-c/photo.jpg",
    "given_name": "Test",
    "family_name": "User",
    "locale": "en"
  */

  private Entity entity;

  private User() {
      // prevents usage of default constructor.
  }

  // Constructs a User instance based on a unique id_token.
  // Verify user using google's ID token verifyer API.
  // This method is commented out because it does not work yet.
  public User(String idTokenString) {
    // GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
    // .Builder(UrlFetchTransport.getDefaultInstance(), new JsonFactory())
    // // Specify the CLIENT_ID of the app that accesses the backend:
    // .setAudience(Collections.singletonList(CLIENT_ID)).build();

    // GoogleIdToken idToken = verifier.verify(idTokenString);
    // if (idToken == null) {
    //   throw new Exception("ID token verification failed");
    // }

    // payload = idToken.getPayload();
  }

  // This creates a standard universal user instance regardless of login status.
  // This test method will not be removed from the webapp prior to deployment.
  public static User getTestUser() {
    User testUser = new User();
    String testID = "000000000000000000000";
    Key userKey = KeyFactory.createKey("User", testID);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    try {
        testUser.entity = datastore.get(userKey);
    } catch(Exception e) {
        // create initial instance of entity
        testUser.entity = new Entity(userKey);
        String emptyJsonArray = "[ ]";
        testUser.entity.setProperty("displayName", "Test Person");
        testUser.entity.setProperty("cookbook", emptyJsonArray);
        testUser.entity.setProperty("userRecipes", emptyJsonArray);
        testUser.entity.setProperty("userRecipes", emptyJsonArray);
        testUser.entity.setProperty("planner", emptyJsonArray);
    }
    return testUser;
  }

  public long getDisplayName() {
    return (long)entity.getProperty("displayName");
  }

// Cookbook and Planner currently support only one type of Recipe entity (namely public).
// Likewise, userRecipes should only include private recipes.

  public ArrayList<Long> getCookbook() {
    return new ArrayList<Long>();
  }

  public ArrayList<Long> getUserRecipes() {
    return new ArrayList<Long>();
  }

  public ArrayList<Long> getPlanner() {
    return new ArrayList<Long>();
  }

  public void setDisplayName(String name) {
    entity.setProperty("displayName", name);
    put();
  }

  public void addRecipeToPlanner(long id) {
    addIDToRecipeList(id, "planner");
  }

  public void addRecipeToCookbook(long id) {
    addIDToRecipeList(id, "cookbook");
  }

  public void addRecipeToUserRecipes(long id) {
    addIDToRecipeList(id, "userRecipes");
  }

  public void removeRecipeFromPlanner(long id) {
    removeIDFromRecipeList(id, "planner");
  }

  public void removeRecipeFromCookbook(long id) {
    removeIDFromRecipeList(id, "cookbook");
  }

  public void removeRecipeFromUserRecipes(long id) {
    removeIDFromRecipeList(id, "userRecipes");
  }

  private void removeIDFromRecipeList(long id, String recipeListType) {
    Gson gson = new Gson();
    ArrayList<Long> list = getPropertyArrayList(recipeListType);
    list.remove(new Long(id));
    entity.setProperty(recipeListType, gson.toJson(list));
    put();
  }

  private void addIDToRecipeList(long id, String recipeListType) {
    Gson gson = new Gson();
    ArrayList<Long> list = getPropertyArrayList(recipeListType);
    if (!list.contains(id)) {
      list.add(id);
    }
    entity.setProperty(recipeListType, gson.toJson(list));
    put();
  }

  // Creates a datastore object and stores current entity in database.
  private void put() {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(entity);
  }

  private ArrayList<Long> getPropertyArrayList(String property) {
    Gson gson = new Gson();
    String jsonArray = (String)entity.getProperty(property);
    Type listType = new TypeToken<List<String>>(){}.getType();
    List<String> stringIDs = gson.fromJson(jsonArray, listType);
    ArrayList<Long> longIDs = new ArrayList<Long>();
    for (String str : stringIDs) {
        longIDs.add(Long.parseLong(str));
    }
    return longIDs;
  }
}
