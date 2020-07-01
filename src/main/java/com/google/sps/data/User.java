package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

// Class representing a User
public class User {
  private long id;
  private String email;
  private String username;
  private ArrayList<Long> planner;
  private ArrayList<Long> cookbook;
  private ArrayList<Long> userRecipes;
  // shopping list will not be implented until after MVP
  private ArrayList<String> shoppingList;

  public User(long id, String email, String username) {
    this.id = id;
    this.email = email;
    this.username = username;
    planner = new ArrayList<Long>();
    cookbook = new ArrayList<Long>();
    userRecipes = new ArrayList<Long>();
  }

  public User(long id, String email, String username, ArrayList<Long> planner, ArrayList<Long> cookbook, ArrayList<Long> userRecipes) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.cookbook = cookbook;
    this.planner = planner;
    this.userRecipes = userRecipes;
  }

  public void setID(long id) {
    this.id = id;
  }

  public boolean equalIDs(long id) {
    if (this.id == id) {
      return true;
    }
    return false;
  }

  public void addRecipeToPlanner(long id) {
    planner.add(id);
  }

  public void addRecipeToCookbook(long id) {
    cookbook.add(id);
  }

  public void addRecipeToUserRecipes(long id) {
    userRecipes.add(id);
  }
}
