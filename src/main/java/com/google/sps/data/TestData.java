package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.util.TestUtil;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Class used for creating mock data for various testing
 */
public class TestData {
  /**
   * Nested class used to initialize recipes for testing basic recipe name searching
   */
  public static final class RecipeNameSearch {
    // Properties for test recipes in this static class
    private static final ArrayList<String> TAG_LIST = new ArrayList<String>(
        Arrays.asList("test", "name", "search"));
    private static final ArrayList<String> INGRED_LIST = new ArrayList<String>(
        Arrays.asList("none"));
    private static final ArrayList<String> STEPS_LIST = new ArrayList<String>(
        Arrays.asList("none"));
    private static final String ENTITY_NAME = "PublicRecipe";
    private static final String RECIPE_NAME = "recipes for testing name search";
    private static final String ALT_RECIPE_NAME = "alt recipes for testing name search";

    // Recipes which are used for testing in this class
    private static final Entity RECIPE_001 = TestUtil.createRecipeEntity(
      ENTITY_NAME, RECIPE_NAME, "THIS IS TEST RECIPE 001 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_002 = TestUtil.createRecipeEntity(
      ENTITY_NAME, RECIPE_NAME, "THIS IS TEST RECIPE 002 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_003 = TestUtil.createRecipeEntity(
      ENTITY_NAME, RECIPE_NAME, "THIS IS TEST RECIPE 003 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_004 = TestUtil.createRecipeEntity(
      ENTITY_NAME, RECIPE_NAME, "THIS IS TEST RECIPE 004 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_005 = TestUtil.createRecipeEntity(
      ENTITY_NAME, RECIPE_NAME, "THIS IS TEST RECIPE 005 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_006 = TestUtil.createRecipeEntity(
      ENTITY_NAME, RECIPE_NAME, "THIS IS TEST RECIPE 006 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_007 = TestUtil.createRecipeEntity(
      ENTITY_NAME, ALT_RECIPE_NAME, "THIS IS TEST RECIPE 007 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_008 = TestUtil.createRecipeEntity(
      ENTITY_NAME, ALT_RECIPE_NAME, "THIS IS TEST RECIPE 008 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_009 = TestUtil.createRecipeEntity(
      ENTITY_NAME, ALT_RECIPE_NAME, "THIS IS TEST RECIPE 009 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);
    private static final Entity RECIPE_010 = TestUtil.createRecipeEntity(
      ENTITY_NAME, ALT_RECIPE_NAME, "THIS IS TEST RECIPE 010 FOR THE SEARCH BY NAME TEST",
      TAG_LIST, INGRED_LIST, STEPS_LIST, -1);

    // Array of all test entities
    private static Entity[] testRecipes = new Entity[]{RECIPE_001, RECIPE_002, RECIPE_003, RECIPE_004,
      RECIPE_005, RECIPE_006, RECIPE_007, RECIPE_008, RECIPE_009, RECIPE_010};
    
    // Creates the datastore and puts all the test recipes into the Datastore
    public static void initializeData() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        for (int i = 0; i < 10; i++) {
          datastore.put(testRecipes[i]);
        }
        return;
    }
  }
}