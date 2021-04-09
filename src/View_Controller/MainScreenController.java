package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * The MainScreenController class controls the logic for the Main Screen
 * of the inventory application.
 *
 * @author Sakae Watanabe
 */
public class MainScreenController implements Initializable {

  @FXML
  private TextField mainPartSearchText;

  @FXML
  private Button mainPartSearchButton;

  @FXML
  private TableView<Part> mainPartTableView;

  @FXML
  private TableColumn<Part, Integer> mainPartIDColumn;

  @FXML
  private TableColumn<Part, String> mainPartNameColumn;

  @FXML
  private TableColumn<Part, Integer> mainPartInvColumn;

  @FXML
  private TableColumn<Part, Double> mainPartPriceColumn;

  @FXML
  private Button mainAddPartButton;

  @FXML
  private Button mainModPartButton;

  @FXML
  private Button mainDeletePartButton;

  @FXML
  private TextField mainProductSearchText;

  @FXML
  private Button mainProductSearchButton;

  @FXML
  private TableView<Product> mainProductTableView;

  @FXML
  private TableColumn<Product, Integer> mainProductIDColumn;

  @FXML
  private TableColumn<Product, String> mainProductNameColumn;

  @FXML
  private TableColumn<Product, Integer> mainProductInvColumn;

  @FXML
  private TableColumn<Product, Double> mainProductPriceColumn;

  @FXML
  private Button mainAddProductButton;

  @FXML
  private Button mainModProductButton;

  @FXML
  private Button mainDeleteProductButton;

  @FXML
  private Button mainExitButton;

  /**
   * The Initialize method sets cell value factories for both Part and Product
   * table views.
   * TODO: Do you even need a javadoc for the initialize method?
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Setup parts table and load full list from Inventory.
    mainPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    mainPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    mainPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    mainPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    mainPartTableView.setItems(Inventory.getAllParts());

    // Setup products table and load full list from Inventory.
    mainProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    mainProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    mainProductInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    mainProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    mainProductTableView.setItems(Inventory.getAllProducts());

  }

  /**
   * The addPartButtonPushed method handles changing the scene when the addPartButton
   * is pushed. A call is made to the AddModifyPartController initAddPart method to prepare
   * label for the scene.
   *
   * @param event Event captured when user pushes the addPartButton.
   */
  @FXML
  public void addPartButtonPushed(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/View_Controller/AddModifyPart.fxml"));
    Parent addPartParent = loader.load();
    Scene addPartScene = new Scene(addPartParent);

    AddModifyPartController controller = loader.getController();
    controller.initAddPart();

    Stage addPartWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
    addPartWindow.setScene(addPartScene);
    addPartWindow.show();
  }

  @FXML
  public void addProductButtonPushed(ActionEvent event) {

  }

  @FXML
  public void deletePartButtonPushed(ActionEvent event) {

  }

  @FXML
  public void deleteProductButtonPushed(ActionEvent event) {

  }

  @FXML
  public void exitButtonPushed(ActionEvent event) {

  }

  @FXML
  public void mainSearchPartHandler(ActionEvent event) {

  }

  @FXML
  public void mainSearchProductHandler(ActionEvent event) {

  }

  @FXML
  public void modPartButtonPushed(ActionEvent event) {

  }

  @FXML
  public void modProductButtonPushed(ActionEvent event) {

  }
}
