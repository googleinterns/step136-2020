// Helper functions for User actions (save and unsave recipes) and settings.
const RECIPE = 'Recipe'

addToCookbook = (recipeId) => {
  idToken = getIdToken();
  listName = 'cookbook';

  fetch(`/save-recipe?recipeId=${recipeId}&listName=${listName}&idToken=${idToken}`);
}

addToPlanner = (recipeId) => {
  idToken = getIdToken();
  listName = 'planner';

  fetch(`/save-recipe?recipeId=${recipeId}&listName=${listName}&idToken=${idToken}`);
}

removeFromCookbook = (recipeId) => {
  idToken = getIdToken();
  listName = 'cookbook';

  fetch(`/unsave-recipe?recipeId=${recipeId}&listName=${listName}&idToken=${idToken}`);
}

removeFromPlanner = (recipeId) => {
  idToken = getIdToken();
  listName = 'planner';

  fetch(`/unsave-recipe?recipeId=${recipeId}&listName=${listName}&idToken=${idToken}`);
}