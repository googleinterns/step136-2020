package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

// Class representing a User
public class User {
  private ArrayList<Long> cookbook;
  private ArrayList<Long> planner;
  private ArrayList<Long> userRecipes;
  private String email;
  private String id;
  private String username;
  // shopping list will not be implented until after MVP
  private ArrayList<String> shoppingList;

  public User(String id, String email, String username) {
    this.id = id;
    this.email = email;
    this.username = username;
    planner = new ArrayList<Long>();
    cookbook = new ArrayList<Long>();
    userRecipes = new ArrayList<Long>();
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean equalIds(String id) {
    return this.id.equals(id);
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

  public void removeRecipeFromPlanner(long id) {
    planner.remove(new Long(id));
  }

  public void removeRecipeFromCookbook(long id) {
    cookbook.remove(new Long(id));
  }

  public void removeRecipeFromUserRecipes(long id) {
    userRecipes.remove(new Long(id));
  }
}
