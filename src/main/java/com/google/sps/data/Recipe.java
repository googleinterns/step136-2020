package com.google.sps.data;

import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.time.format.FormatStyle;

// Class representing a Recipe
public class Recipe {
  // it's not done yet and I'm sure I'm going to add a bunch more methods but here's what I have so far
  private long id;
  private String recipeName;
  private long authorID;
  private String imageUrl;
  private String description;
  private ArrayList<String> tags;
  private ArrayList<String> ingredients;
  private ArrayList<String> steps;
  private int popularity;

  public Recipe(long id, String recipeName, String description, ArrayList<String> tags, ArrayList<String> ingredients, ArrayList<String> steps) {
    this.id = id;
    this.recipeName = recipeName;
    this.tags = tags;
    this.ingredients = ingredients;
    this.steps = steps;
    popularity = 0;
  }

  public Recipe(long id, String recipeName, String description, ArrayList<String> tags, ArrayList<String> ingredients, ArrayList<String> steps, int popularity) {
    this.id = id;
    this.recipeName = recipeName;
    this.tags = tags;
    this.ingredients = ingredients;
    this.steps = steps;
    this.popularity = popularity;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setID(long id) {
    this.id = id;
  }
  
  public void setAuthorID(long id) {
    authorID = id;
  }

  public void increasePopularity() {
    popularity++;
  }
  
  public void decreasePopularity() {
    popularity--;
  }
}
