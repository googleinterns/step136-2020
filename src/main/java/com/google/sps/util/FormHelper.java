package com.google.sps.util;

import com.google.appengine.api.datastore.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FormHelper {

  // input: String of values separated by commas
  // returns: values split by commas, trimmed and in lowercase and without any empty/null members
  public static ArrayList<String> separateByCommas(String input) {
    ArrayList<String> list = new ArrayList<String>(Arrays.asList(Arrays.stream(input.split(",")).map(String::trim).map(String::toLowerCase).toArray(String[]::new)));
    list.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));
    return list;
  }

  // input: String of values separated by newlines
  // returns: values split by newlines, trimmed and without any empty/null/newline members
  public static ArrayList<String> separateByNewlines(String input) {
    ArrayList<String> list = new ArrayList<String>(Arrays.asList(Arrays.stream(input.split("\n")).map(String::trim).toArray(String[]::new)));
    list.removeAll(Arrays.asList("", null, "\n", "\r\n", "\r"));
    return list;
  }

  // input: a PrivateRecipe entity
  // returns: a PublicRecipe entity copied from the PrivateRecipe
  public static Entity copyToNewPublicRecipe(Entity recipe) {
    Entity entity = new Entity("PublicRecipe");
    entity.setProperty("name", recipe.getProperty("name"));
    entity.setProperty("tags", recipe.getProperty("tags"));
    entity.setProperty("description", recipe.getProperty("description"));
    entity.setProperty("ingredients", recipe.getProperty("ingredients"));
    entity.setProperty("steps", recipe.getProperty("steps"));
    entity.setProperty("imageBlobKey", recipe.getProperty("imageBlobKey"));
    entity.setProperty("authorID", recipe.getProperty("authorID"));
    return entity;
  }

  // input: a PrivateRecipe entity and a PublicRecipe entity
  // copies the PrivateRecipe entity to an existing PublicRecipe entity
  public static void copyToPublicRecipe(Entity privateRecipe, Entity publicRecipe) {
    publicRecipe.setProperty("name", privateRecipe.getProperty("name"));
    publicRecipe.setProperty("tags", privateRecipe.getProperty("tags"));
    publicRecipe.setProperty("description", privateRecipe.getProperty("description"));
    publicRecipe.setProperty("ingredients", privateRecipe.getProperty("ingredients"));
    publicRecipe.setProperty("steps", privateRecipe.getProperty("steps"));
    publicRecipe.setProperty("imageBlobKey", privateRecipe.getProperty("imageBlobKey"));
    publicRecipe.setProperty("authorID", privateRecipe.getProperty("authorID"));
  }
}