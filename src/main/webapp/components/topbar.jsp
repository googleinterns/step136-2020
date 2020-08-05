<!--To use this top bar in other parts of the application, use the jsp @include directive
  in another jsp file to merge this html into that file.-->
<div id="top-bar">
  <div id="color-bar">
    <div id="logo">
        <p>Simpler Eats</p>
    </div>

    <div id="search-bar">
      <div id="search-box-flex-container">
        <input type="text" id="search-box" placeholder="Search Recipes">
      </div>
        <input type="button" id="search-button" onclick="search()" >
    </div>

    <input type="button" onclick="window.location.replace('MainPage.jsp')" id="home-button">
  
    <div class="dropdown">
      <div id="sign-in-box">
        <div id="profile-photo-container">
          <img id="profile-photo" src=../images/default_profile.jpeg alt="chkn">
        </div>
        <div id="g-signin-container">
        </div>
        <div class="dropdown-content">
          <a href="UserPage.jsp" id="user-page-anchor"></a>
          <a href="UserSettings.jsp">Settings</a>
          <a href="#" onclick="signOut()">Sign out</a>
        </div>
      </div>
    </div>
  </div>
</div>
