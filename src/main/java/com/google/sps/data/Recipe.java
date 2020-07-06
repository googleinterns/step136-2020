package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;

// Class representing a Recipe
public class Recipe {
  private long id;
  private String recipeName;
  private long authorID;
  private String imageBlobKey;
  private String description;
  private ArrayList<String> tags;
  private ArrayList<String> ingredients;
  private ArrayList<String> steps;
  private int popularity;

  public Recipe(long id, String recipeName, String imageBlobKey, String description, ArrayList<String> tags, 
  ArrayList<String> ingredients, ArrayList<String> steps) {
    this.id = id;
    this.recipeName = recipeName;
    this.imageBlobKey = imageBlobKey;
    this.description = description;
    this.tags = tags;
    this.ingredients = ingredients;
    this.steps = steps;
    popularity = 0;
  }

  public Recipe(long id, String recipeName, String description, ArrayList<String> tags, 
  ArrayList<String> ingredients, ArrayList<String> steps, int popularity) {
    this.id = id;
    this.recipeName = recipeName;
    this.tags = tags;
    this.ingredients = ingredients;
    this.steps = steps;
    this.popularity = popularity;
  }

  public void setID(long id) {
    this.id = id;
  }
  
  public void setAuthorID(long id) {
    authorID = id;
  }

  public long getID() {
    return id;
  }

  public void increasePopularity() {
    popularity++;
  }
  
  public void decreasePopularity() {
    popularity--;
  }
}
