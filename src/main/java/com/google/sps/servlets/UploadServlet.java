import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import javax.servlet.annotation.WebServlet;

// To serve a blob in your application, put a special header in the response containing the blob key
// App Engine replaces the body of the response with the content of the blob.
@WebServlet("/serve")
public class UploadServlet extends HttpServlet {
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    BlobKey blobKey = new BlobKey(req.getParameter("blobkey"));
    blobstoreService.serve(blobKey, res);
  }
}