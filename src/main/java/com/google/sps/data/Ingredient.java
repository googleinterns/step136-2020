package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

// Class representing a Recipe
public class Ingredient {
  private long recipeID;
  private String text;
  private boolean crossedOut;

  // Recipe constructor for the recipe cards for which less information is needed
  public Ingredient(long recipeID, String text, boolean crossedOut) {
    this.recipeID = recipeID;
    this.text = text;
    this.crossedOut = crossedOut;
  }
}
