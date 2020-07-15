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

import com.google.sps.util.SearchUtils;
import java.util.ArrayList;

/**
 * Represents the separated user query data which can be used to run different Datastore
 * query requests.
 */
public class UserQuery {
  private ArrayList<String> tagsAndIngredients;
  private ArrayList<String> authors;
  private String recipeName;

  /**
   * This constructor takes in a raw user query string, with special query symbols, and initializes
   * instance variables using methods in SearchUtils to handle the query.
   * TODO: Implement regex and make calls to initialize authors, and tagsAndIngredients using regex functions
   */
  public UserQuery(String query) {
    String standardizedQuery= SearchUtils.standardize(query);
    this.tagsAndIngredients = new ArrayList<String>();
    this.authors = new ArrayList<String>();
    this.recipeName = SearchUtils.parseRecipeName(standardizedQuery);
  }

  /**
   * Takes a new tag or ingeredient to search for and adds it to the tagsAndIngredients attribute.
   * TODO: Implement ability to avoid duplicates
   */
  public void addTag(String tag) {
    this.tagsAndIngredients.add(tag);
  }

  public ArrayList<String> getTagsAndIngreds() {
      return this.tagsAndIngredients;
  }

  public ArrayList<String> getAuthors() {
      return this.authors;
  }

  public String getName() {
      return this.recipeName;
  }
}