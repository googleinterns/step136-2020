// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.util;

import com.google.appengine.api.datastore.Query;
import java.lang.UnsupportedOperationException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A utility class with static methods to complete search related functions.
 */
public class SearchUtils {
  /**
   * Takes a string and returns a standard version of the string depending on the implementation.
   * This is used for processing a string before parsing for different query data.
   * Current implementation returns strings trimmed and in lowercase.
   */
  public static String standardize(String str) {
   String standard = str.trim().toLowerCase();
   return standard;
  }

  /*
   * Takes a string with a user query and parses it looking for "@" symbols to extract author names. The 
   * result is an arraylist of strings which are the authors the user searched for, without "@" symbol.
   * TODO: Implement this function with regex.
   */
  public static ArrayList<String> parseAuthor(String str) throws 
    UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /*
   * Takes a string with a user query and parses it looking for "#" symbols to extract tags. The 
   * result is an arraylist of strings which are the tags the user searched for, without "#" symbol.
   * TODO: Implement this function when tag system has been decided on
   */
  public static ArrayList<String> parseTags(String str) throws 
    UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * Takes a string of the original query and extracts the plain text with no "@" or "#" symbol,
   * which therefore represents the user's query for a recipe name.
   * TODO: Implement regex functionality to not simply return the string passed through.
   */
  public static String parseRecipeName(String str) {
    return str;
  }

  /**
   * Takes in an authorID and returns the query filter for the given author so it can be added to
   * a query.
   */
  public static Query.FilterPredicate getAuthorFilter(Long authorID) {
    return new Query.FilterPredicate("authorID", Query.FilterOperator.EQUAL, authorID);
  }

  /**
   * Takes in an recipe name and returns the query filter for the given recipe name so it can be added to
   * a query.
   */
  public static Query.FilterPredicate getRecipeNameFilter(String recipeName) {
      return new Query.FilterPredicate("name", Query.FilterOperator.EQUAL, recipeName);
  }

  /*
   * Takes a tag a returns a query filter which can be used to check if the tag is in the tag or
   * ingredient list of the entity property.
   */
  public static Query.CompositeFilter getTagFilter(String tag) {
      // Filter for whether the tag is in the ingredient list
      Query.Filter ingredientFilter = new Query.FilterPredicate("ingredients", Query.FilterOperator.IN, tag);
      
      //Filter for whether the tag is in the tag list
      Query.Filter tagFilter = new Query.FilterPredicate("tags", Query.FilterOperator.IN, tag);

      // returns a composite filter for whether the tag is in one of the arraylists
      return new Query.CompositeFilter(
        Query.CompositeFilterOperator.OR, Arrays.asList(ingredientFilter, tagFilter)
      );
  }
}