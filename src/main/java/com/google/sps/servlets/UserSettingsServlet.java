package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/settings")
public class UserSettingsServlet extends HttpServlet {
  // This regex checks that user names are alphanumeric and (5 - 25) characters.
  // ([^\s\d]( ?){1}){5,25}

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.println("Settings fetch\n\n");
    response.setContentType("application/json");
    String idToken = (String) request.getParameter("idToken");
    User user = new User(idToken);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.println("Settings post\n\n");
    String idToken = (String) request.getParameter("idToken");
    if (idToken == null ) {
        System.out.println("idtoken was null\n");
        return;
    }

    User user = new User(idToken);

    String nameInput = (String) request.getParameter("name");
    String blobKey = (String) request.getParameter("blobKey");

    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("imageFile");

    if (blobKeys == null || blobKeys.isEmpty()) {
      response.sendRedirect("/");
    } else {
      response.sendRedirect("/serve?blobkey=" + blobKeys.get(0).getKeyString());
    }
  }
}
