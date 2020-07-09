/**
 * TODO(matwsuaz): update with a new method to return refereces to live HTML DOM
 * elements to reduce redundancy and code length.
 * TODO(matwsuaz): update HTMl injection test to provide test result description
 * and inform which test failed specifically.
 */
runTests = () => {
  testRecipeCard();
}

/**
 * Makes a call to create a recipe card and makes sure the result is what is
 * expected.
 */
testRecipeCard = () => {
  // The div in the test file for rendering the comment card test results
  const testDiv = "recipe-card-test";

  // The class names for all the html elements that were established in 
  // RecipeRendering.js; used to standarize div name verification
  const cardDivClass = "recipe-card";
  const cardNameClass = "recipe-card-name";
  const cardDescriptionClass = "recipe-card-description";
    
  // Everytime a recipe card is created, the counter goes up by one so the node
  // can be retrieved correctly
  let cardCounter = -1;
    
  /**
   * Finds the correct recipe card on the body and updates the test status.
   * Takes a boolean parameter, and a string detailing which tests failed which
   * defaults to an empty string.
   */
  updateTestResults = (testStatus, testsFailed = "") => {
    // retrives the DOM elements from the page
    let recipeTestCard =
      document.getElementById(testDiv).getElementsByClassName(cardDivClass)[cardCounter];
    let recipeCardName =
      recipeTestCard.getElementsByClassName(cardNameClass)[0];
    let recipeCardDescription =
      recipeTestCard.getElementsByClassName(cardDescriptionClass)[0];

    if (testStatus) {
      recipeCardName.innerHTML = "Tests Passed!"
    } else {
      recipeCardName.innerHTML = "Tests Failed!"
      recipeCardDescription.innerHTML = recipeCardDescription.innerHTML + "\n" + testsFailed;
    }
      return;
  }

  /**
  * All tests begin with creating a recipe card and makig sure that no errors were
  * propogated from the RecipeRendering file during card creation. 
  * Returns true if the card was created correctly and otherwise returns false and 
  * shows the error.
  * Takes the same parameters as createRecipeCard, plus the test name which is a string
  */
  firstCardRenderCheck = (testDivId, testObj, testName) => {
    try {
      createRecipeCard(testDivId, testObj);
      return true;
    } catch (e) {
      console.error(
        "Could not get to " + testName + ", encountered errors.\n" +
        e["error"]
      );
      return false;
    }
  }

  /**
   * Series of tests meant to test recipe rendering work under notmal use case
   */
  basicRenderingTests = () => {
    let sampleObject = {
      "name": "If you can see me there is a logic error.",
      "description": "Basic recipe rendering test." 
    };

    // tries to create the recipe card, but if it returns false then the tests
    // cannot be run
    if (!firstCardRenderCheck(testDiv, sampleObject, "basic rendering test")) {
      return;
    }
    cardCounter = cardCounter + 1;
    updateTestResults(true);
    return;
  }

  /**
   * Series of tests meant to test HTML injection
   */
  htmlInjecttionTests = () => {
    /**
     * Takes a reference to an element of HTML DOM, and the nodeType
     * the child should be, using the node type integer value.
     * Since the current implementation is that elements of the recipe card
     * only have one child node, the function calls the first element on its
     * own.
     * returns true if there is only one child node and the nodetype matches
     */
    nodeCorrect = (referenceDiv, nodeType) => {
      return referenceDiv.childNodes[0].nodeType === nodeType && referenceDiv.childNodes.length === 1;
    } 

    let injectionObject = {
      "name": "<p>If you can see me there is a logic error.</p>", 
      "description": "<div></p>HTML injection test.</p></div>"
    };
    
    // tries to create the recipe card, but if it returns false then the tests
    // cannot be run
    if (!firstCardRenderCheck(testDiv, injectionObject, "HTML injection test")) {
      return;
    }
    cardCounter = cardCounter + 1;

    // The HTML DOM elements of the recipe card
    let recipeTestCard =
      document.getElementById(testDiv).getElementsByClassName(cardDivClass)[cardCounter];
    let recipeCardName =
      recipeTestCard.getElementsByClassName(cardNameClass)[0];
    let recipeCardDescription =
      recipeTestCard.getElementsByClassName(cardDescriptionClass)[0];

    // runs all the tests and puts the result in an array
    let testChecks = [
      nodeCorrect(recipeCardDescription, 3),
      nodeCorrect(recipeCardName, 3)
    ];

    if (testChecks.includes(false)) {
      updateTestResults(false);
    } else {
      updateTestResults(true);
    }
    return;
  }

  // Run all the comment card tests
  basicRenderingTests();
  htmlInjecttionTests();
  return;
}

/**
 * Test for confirmUser method in user-auth.js
 * This function is called onclick of <sign in required service> button in test.jsp
 */
function userSpecificService() {
    confirmUser().then((googleUser) => {
        outputPlace = document.getElementById('test-output');
        outputPlace.innerText = 'current user: ' + auth2.currentUser.get().getBasicProfile().getName();
    });
}