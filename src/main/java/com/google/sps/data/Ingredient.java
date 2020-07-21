package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

// Class representing a Recipe
public class Ingredient {
  private long recipeID;
  private String recipeType;
  private String text;

  // Recipe constructor for the recipe cards for which less information is needed
  public Ingredient(long recipeID, String recipeType, String text) {
    this.recipeID = recipeID;
    this.recipeType = recipeType;
    this.text = text;
  }
}
