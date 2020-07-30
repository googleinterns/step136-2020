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

package com.google.sps.data;

import com.google.appengine.api.datastore.Query;
import java.lang.UnsupportedOperationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Represents the separated user query data which can be used to run different Datastore
 * query requests.
 */
public class UserQuery {
  /**
   * TODO: Implement the tag and author collections as sets to avoid duplicates
   * NOTE: The methods used are from collection interface, so a change in implementation won't break
   * Existing code
   */
  private ArrayList<String> tagsAndIngredients;
  private ArrayList<String> authors;
  private String recipeName;

  /**
   * The constructor takes in the string parameters for the query from the search bar, as well
   * as strings for the tags and authors. These will be passed from the front end with delimeters,
   * so these instance variables are initialized using helper functions which will return the tags
   * in an ArrayList
   * NOTE: the helper functions will initialize tagsAndIngredients and authors to be empty collections
   * if the param value results in empty strings or is null.
   * TODO: implement initialization of tags and authors
   */
  public UserQuery(String query, String tags, String authors) {
    this.recipeName = query;
    this.tagsAndIngredients = new ArrayList<String>();
    this.authors = new ArrayList<String>();
  }

  /**
   * Returns a filter which can be used to construct a Datastore query based on the instance variables
   * of this object. If the object has no data which can be used to construct filters, it will return
   * a single filter for making sure recipes are public.
   */
  public Query.Filter createSearchFilter() {
    // ArrayList which will hold filters for name, authors, tags, and visibility.
    ArrayList<Query.Filter> searchFilters = new ArrayList<Query.Filter>();
    
    // Creates and adds filters to the list if there is data to make the filter
    if (tagsAndIngredients.size() > 1){
      searchFilters.add(createTagsFilter());
    } else if (tagsAndIngredients.size() == 1){
      // Use of collection interface methods only to allow for future flexibility
      String tempTag = tagsAndIngredients.iterator().next();
      searchFilters.add(createSingleTagFilter(tempTag));
    }
    if (authors.size() > 1) {
      searchFilters.add(createAuthorsFilter());
    } else if (authors.size() == 1) {
      // Use of collection interface methods only to allow for future flexibility
      String tempAuthor = authors.iterator().next();
      Query.Filter tempAuthorFilter = new Query.FilterPredicate(
        "authorID", Query.FilterOperator.EQUAL, tempAuthor
      );
      searchFilters.add(tempAuthorFilter);
    }
    if (recipeName != null && !recipeName.equals("")) {
      searchFilters.add(new Query.FilterPredicate("name", Query.FilterOperator.EQUAL, recipeName.toLowerCase().trim()));
    }

    // Adds a filter which makes sure the recipes being searched for are public
    searchFilters.add(new Query.FilterPredicate("published", Query.FilterOperator.EQUAL, true));
    
    if (searchFilters.size() == 1) {
      return searchFilters.get(0);
    } else {
      return new Query.CompositeFilter(
        Query.CompositeFilterOperator.AND, searchFilters
      );
    }
  }

  /*
   * Takes a tag a returns a query filter which can be used to check if the tag is in the tag or
   * ingredient list of the entity property.
   */
  private Query.CompositeFilter createSingleTagFilter(String tag) {
    // Filter for whether the tag is in the ingredient list
    Query.Filter ingredientFilter = new Query.FilterPredicate("ingredients", Query.FilterOperator.IN, tag);
      
    // Filter for whether the tag is in the tag list
    Query.Filter tagFilter = new Query.FilterPredicate("tags", Query.FilterOperator.IN, tag);

    // Returns a composite filter for whether the tag is in one of the arraylists
    return new Query.CompositeFilter(
      Query.CompositeFilterOperator.OR, Arrays.asList(ingredientFilter, tagFilter)
    );
  }

  /**
   * Returns a filter for recipes which have all of the tags and ingredients in the collection
   * present.
   */
  private Query.CompositeFilter createTagsFilter() {
    // An array list of all the filters for tags/ingredients which can then be passed as a 
    // param to the composite filter constructor
    ArrayList<Query.Filter> tagsAndIngredientsFilters = new ArrayList<Query.Filter>();
    
    // Fills the arraylist with all the filters
    for (String loopTag : tagsAndIngredients) {
      tagsAndIngredientsFilters.add(
        createSingleTagFilter(loopTag)
      );
    }

    return new Query.CompositeFilter(Query.CompositeFilterOperator.AND, tagsAndIngredientsFilters);
  }

  /**
   * Returns a filter for recipes which match one of the authors present in the search
   */
  private Query.CompositeFilter createAuthorsFilter() {
    // An array list of all the filters for authors which can then be passed as a param to 
    // the composite filter constructor
    ArrayList<Query.Filter> authorsFilters = new ArrayList<Query.Filter>();
    
    // Fills the arraylist with all the filters
    for (String loopAuthor : authors) {
      authorsFilters.add(
        new Query.FilterPredicate("authorID", Query.FilterOperator.EQUAL, loopAuthor)
      );
    }
    return new Query.CompositeFilter(Query.CompositeFilterOperator.OR, authorsFilters);
  }
}