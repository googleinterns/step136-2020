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

import com.google.appengine.api.datastore.Entity;
import com.google.sps.data.Recipe;
import java.util.ArrayList;

public class TestUtil {
  /**
   * Takes in the info for a recipe and returns an entity with set properties based on the recipe object
   */
  public static Entity createRecipeEntity(String entityName, String name, String description, ArrayList<String> tags,
    ArrayList<String> ingredients, ArrayList<String> steps, int popularity) {
    Entity recipeEntity = new Entity(entityName);
    recipeEntity.setProperty("name", name);
    recipeEntity.setProperty("tags", tags);
    recipeEntity.setProperty("description", description);
    recipeEntity.setProperty("ingredients", ingredients);
    recipeEntity.setProperty("steps", steps);
    recipeEntity.setProperty("popularity", popularity);
    return recipeEntity;
   }
}