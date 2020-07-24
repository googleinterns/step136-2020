package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

// Class representing a Recipe
public class Recipe {
  private ArrayList<String> ingredients;
  private ArrayList<String> steps;
  private ArrayList<String> tags;
  private String authorID;
  private String description;
  private String imageBlobKey;
  private String name;
  private long popularity;
  private long id;

  // Recipe constructor for the recipe cards for which less information is needed
  public Recipe(long id, String name, String imageBlobKey, String description) {
    this.id = id;
    this.name = name;
    this.imageBlobKey = imageBlobKey;
    this.description = description;
    ingredients = new ArrayList<String>();
    steps = new ArrayList<String>();
    tags = new ArrayList<String>();
    popularity = 0;
  }
  
  public Recipe(long id, String name, String imageBlobKey, String description, ArrayList<String> tags, 
      ArrayList<String> ingredients, ArrayList<String> steps, long popularity) {
    this.id = id;
    this.name = name;
    this.imageBlobKey = imageBlobKey;
    this.tags = tags;
    this.description = description;
    this.ingredients = ingredients;
    this.steps = steps;
    this.popularity = popularity;
  }

  // haven't implemented yet
  public void setAuthorId(String id) {
    authorID = id;
  }

  public long getId() {
    return id;
  }

  public void increasePopularity() {
    popularity++;
  }
  
  public void decreasePopularity() {
    popularity--;
  }
}
