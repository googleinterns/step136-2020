<!--To use this top bar in other parts of the application, use the jsp @include directive
  in another jsp file to merge this html into that file.-->
<div id="top-bar">
  <div id-"logo">
    <p>Simpler Eats</p>
  </div>

  <div id="search-bar">
    <input type="text" id="search-box" placeholder="Search Recipes">
    <input type="button" id="search-button" onclick="redirectSearchPage()" value="Search">
  </div>

  <div id="sign-in-box" class="dropdown">
    <div id="profile-photo-container">
      <img id="profile-photo" src=../images/default_profile.jpeg>
    </div>
    <div id="g-signin-container">
    </div>
    <div class="dropdown-content">
      <a href="UserPage.jsp" id="user-page-anchor"></a>
      <a href="#" onclick="signOut()">Sign out</a>
    </div>
  </div>
</div>
