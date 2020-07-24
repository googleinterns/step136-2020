package com.google.sps.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.sps.util.TestUtil;
import com.google.sps.util.Utils;
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
    public static final ArrayList<String> TAG_LIST = new ArrayList<String>(
        Arrays.asList("test", "name", "search"));
    public static final ArrayList<String> INGREDIENT_LIST = new ArrayList<String>(
        Arrays.asList("none"));
    public static final ArrayList<String> STEPS_LIST = new ArrayList<String>(
        Arrays.asList("none"));
    public static final String ENTITY_NAME = "PublicRecipe";
    public static final String ENTITY_DESCRIPTION = "THIS IS A TEST RECIPE FOR THE SEARCH BY NAME TEST";
    
    // Specifies the number of test sets that should be made
    public static final int NUM_SETS = 2;

    // The array determines the size of each set, based on the value at each index
    public static final int[] TEST_SET_SIZES = {10, 5};

    // The array determines the name for each set, based on the value at each index
    public static final String[] TEST_SET_NAMES = {
      "recipes for testing name search",
      "alt recipes for testing name search"
    };

    // Creates the datastore and puts all the test recipes into the Datastore
    public static void initializeData() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        // Tries to iteratively create test data based on the number of sets, and catches an index error
        // if one of the array properties doesn't match the expected amount of set properties. 
        if (NUM_SETS == TEST_SET_NAMES.length && NUM_SETS == TEST_SET_SIZES.length) {
          int offset = 0;
          for (int setNum = 0; setNum < NUM_SETS; setNum++) {
              Entity[] tempEntityList = TestUtil.generateTestRecipes(
                  TEST_SET_SIZES[setNum], offset, ENTITY_DESCRIPTION, TAG_LIST, INGREDIENT_LIST,
                  STEPS_LIST, ENTITY_NAME, TEST_SET_NAMES[setNum], "Mathew-Testing"
              
              );
              offset += TEST_SET_SIZES[setNum];
              
              // puts all the enties in Datastore assuming an error wasn't thrown
              for (Entity recipeElement : tempEntityList) {
                  datastore.put(recipeElement);
              }
          }
        } else {
            Utils.SOP(String.join("\n",
              "An array out of bounds exception was just prevented.",
              "Please check that the size of TEST_SET_NAMES and TEST_SET_SIZES are equal to NUM_SETS"
            ));
        }
    }
  }
}