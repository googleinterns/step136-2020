runTests = () => {
    testCommentCard();
}

/**
 * Makes a call to create a recipe card and makes sure the result is what is
 * expected.
 */
testCommentCard = () => {
    /**
     * Series of tests meant to test HTML injection 
     */
    htmlInjecttionTests = () => {
        let injectionObject = {
            "name": "If you can see me there is a logic error.", 
            "description": "<div></p>HTML injection test.</p></div>"
            };
        
        injectionObject["description"] = removeHTML(injectionObject["description"]);
    
        // The string with html entity names instead of actual tag symbols
        const expectedText = "&ltdiv&gt&lt/p&gtHTML injection test.&lt/p&gt&lt/div&gt";

        console.log(
            "Output for HTML injection test:\n" +
            "--> The object string is:\n--> " + injectionObject["description"] + "\n " +
            "--> The expected string is:\n--> " + expectedText);

        if (injectionObject["description"] === expectedText) {
            injectionObject["name"] = "Test Passed";
        } else {
            injectionObject["name"] = "Test Failed";
        }
        createRecipeCard("recipe-card-test", injectionObject);
        return;
    }

    // Run all the comment card tests
    htmlInjecttionTests();
}
