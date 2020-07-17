const PLANNER = 'planner'
const COOKBOOK = 'cookbook'

/**
 * Provides static methods for adding and removing public and private recipes
 * of a provided id from the cookbook list of the current user.
 */
class Cookbook {
  static addPrivate = (id) => {
    const params = new URLSearchParams({
        "idToken"  : getIdToken(),
        "listName" : COOKBOOK,
        "isPublic" : false,
        "recipeId" : id
    });
    fetch("/save-recipe", { body : params });
  }

  static addPublic = (id) => {
    const params = new URLSearchParams({
        "idToken"  : getIdToken(),
        "listName" : COOKBOOK,
        "isPublic" : true,
        "recipeId" : id
    });
    fetch("/save-recipe", { body : params });
  }

  static removePrivate = (id) => {
    const params = new URLSearchParams({
        "idToken"  : getIdToken(),
        "listName" : COOKBOOK,
        "isPublic" : false,
        "recipeId" : id
    });
    fetch("/unsave-recipe", { body : params });
  }

  static removePublic = (id) => {
    const params = new URLSearchParams({
        "idToken"  : getIdToken(),
        "listName" : COOKBOOK,
        "isPublic" : true,
        "recipeId" : id
    });
    fetch("/unsave-recipe", { body : params });
  }
}

/**
 * Provides static methods for adding and removing public and private recipes
 * from the planner list of the current user.
 */
class Planner {
  static addPrivate = (id) => {
    const params = new URLSearchParams({
        "idToken"  : getIdToken(),
        "listName" : PLANNER,
        "isPublic" : false,
        "recipeId" : id
    });
    fetch("/save-recipe", { body : params });
  }

  static addPublic = (id) => {
    const params = new URLSearchParams({
        "idToken"  : getIdToken(),
        "listName" : PLANNER,
        "isPublic" : true,
        "recipeId" : id
    });
    fetch("/save-recipe", { body : params });
  }

  static removePrivate = (id) => {
    const params = new URLSearchParams({
        "idToken"  : getIdToken(),
        "listName" : PLANNER,
        "isPublic" : false,
        "recipeId" : id
    });
    fetch("/unsave-recipe", { body : params });
  }

  static removePublic = (id) => {
    const params = new URLSearchParams({
        "idToken"  : getIdToken(),
        "listName" : PLANNER,
        "isPublic" : true,
        "recipeId" : id
    });
    fetch("/unsave-recipe", { body : params });
  }
}

