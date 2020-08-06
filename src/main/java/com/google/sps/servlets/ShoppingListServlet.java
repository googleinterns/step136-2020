import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.sps.data.Recipe;
import com.google.sps.data.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/shopping-list")
public class ShoppinListServlet extends HttpServlet {

  @Override
  @SuppressWarnings("unchecked") // Compiler cannot verify cast to ArrayList
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String idToken = request.getParameter("idToken");
    User user = new User(idToken);
    List<Key> plannerKeys = user.getPlannerList();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    // Avoid repeating ingredients.
    HashSet<String> shoppingList = new HashSet<>();

    plannerKeys.forEach(key -> {
      try {
        Entity entity = datastore.get(key);
        ArrayList<String> ingredients = (ArrayList<String>) entity.getProperty("ingredients");
        ingredients.forEach(ingredient -> shoppingList.add(ingredient));

      } catch(EntityNotFoundException e) {
        System.out.prinln("Ignoring invalid key");
      }
    });

    Gson gson = new Gson();
    response.setContentType("application/json");
    response.getWriter().print(gson.toJson(shoppingList));
  }
}