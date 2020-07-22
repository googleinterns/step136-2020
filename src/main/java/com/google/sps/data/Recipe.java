package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

// Class representing a Recipe
public class Recipe {
  private ArrayList<String> ingredients;
  private ArrayList<String> steps;
  private ArrayList<String> tags;
  private boolean published;
  private String authorID;
  private String description;
  private String imageBlobKey;
  private String name;
//   private int popularity;
  private long id;
  private long publicRecipeID;

  // Recipe constructor for the recipe cards for which less information is needed
  public Recipe(long id, String name, String imageBlobKey, String description) {
    this.id = id;
    this.name = name;
    this.imageBlobKey = imageBlobKey;
    this.description = description;
    authorID = "";
    ingredients = new ArrayList<String>();
    steps = new ArrayList<String>();
    tags = new ArrayList<String>();
    published = false;
  }
  

  // Recipe constructor for PublicRecipes that don't need published and publicRecipeID
  public Recipe(long id, String name, Stringa authorID, String imageBlobKey, String description, ArrayList<String> tags, 
      ArrayList<String> ingredients, ArrayList<String> steps) {
    this.id = id;
    this.name = name;
    this.authorID = authorID;
    this.imageBlobKey = imageBlobKey;
    this.tags = tags;
    this.description = description;
    this.ingredients = ingredients;
    this.steps = steps;
    publicRecipeID = 0;
    published = true;
  }

  public Recipe(long id, String name, String authorID, String imageBlobKey, String description, ArrayList<String> tags, 
      ArrayList<String> ingredients, ArrayList<String> steps, boolean published, long publicRecipeID) {
    this.id = id;
    this.name = name;
    this.authorID = authorID;
    this.imageBlobKey = imageBlobKey;
    this.tags = tags;
    this.description = description;
    this.ingredients = ingredients;
    this.steps = steps;
    this.published = published;
    this.publicRecipeID = publicRecipeID;
  }
}
