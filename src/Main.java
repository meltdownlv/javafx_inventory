
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class loads and launches the main screen for the inventory
 * application.
 *
 * <p>
 *   <strong>
 *   ENHANCEMENT:
 *   Future enhancement to this application would include the integration of
 *   database connectivity using a relational database management system.
 *   Application reuse will be greatly improved by moving to a persistent
 *   database solution for the inventory of products. Search, addition, and
 *   modification methods will need to be refactored to accommodate the move
 *   to interact with the database.
 *   </strong>
 * </p>
 * <p>
 *  *   <strong>
 *  *     RUNTIME EXCEPTION:
 *        Located in the AddModifyProductController class for
 *        searchAvailablePartsHandler.
 *  *   </strong>
 *  * </p>
 * <p>
 *   <strong>
 *     JAVADOCS: LOCATED IN TOP LEVEL OF PROJECT DIRECTORY IN FOLDER JAVADOCS
 *   </strong>
 * </p>
 * @author Sakae Watanabe
 */
public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }
}
