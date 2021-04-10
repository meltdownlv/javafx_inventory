package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author Sakae Watanabe
 */
public class AddModifyProductController implements Initializable {

  @FXML
  private Label productFormLabel;

  @FXML
  private TextField productFormIDText;

  @FXML
  private TextField productFormNameText;

  @FXML
  private TextField productFormInvText;

  @FXML
  private TextField productFormPriceText;

  @FXML
  private TextField productFormMaxText;

  @FXML
  private TextField productFormMinText;

  @FXML
  private TextField productFormSearchText;

  @FXML
  private Button productFormSearchButton;

  @FXML
  private TableView<Part> productFormAvailablePartView;

  @FXML
  private TableColumn<Part, Integer> availablePartIDColumn;

  @FXML
  private TableColumn<Part, String> availablePartNameColumn;

  @FXML
  private TableColumn<Part, Integer> availablePartInvColumn;

  @FXML
  private TableColumn<Part, Double> availablePartPriceColumn;

  @FXML
  private Button productFormAddPartButton;

  @FXML
  private TableView<Part> productFormAssociatedPartView;

  @FXML
  private TableColumn<Part, Integer> associatedPartIDColumn;

  @FXML
  private TableColumn<Part, String> associatedPartNameColumn;

  @FXML
  private TableColumn<Part, Integer> associatedPartInvColumn;

  @FXML
  private TableColumn<Part, String> associatedPartPriceColumn;

  @FXML
  private Button removeAssociatedPartButton;

  @FXML
  private Button productFormSaveButton;

  @FXML
  private Button productFormCancelButton;

  /** The current Product is used when modifying or adding a new product. */
  private Product currentProduct;
  /** Flag to indicate if we are adding a new part. */
  private boolean addProduct;
  /** Index for currentProduct being modified in the main Inventory. */
  private int currentProductIndex;
  /** The associatedPartList holds the parts associated with the current product. */
  private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

  /**
   * The Initialize method sets up items for both available parts list and associated
   * part list table views.
   * TODO: update javadoc for Main Screen initialize method.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Setup available inventory parts table
    availablePartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    availablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    availablePartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    availablePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    productFormAvailablePartView.setItems(Inventory.getAllParts());

    // Setup associated parts table
    associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    associatedPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    productFormAssociatedPartView.setItems(associatedPartList);
  }

  @FXML
  private void addPartToProductPushed(ActionEvent event) {

  }

  @FXML
  private void productFormCancelButtonPushed(ActionEvent event) {

  }

  @FXML
  private void removeAssociatedButtonPushed(ActionEvent event) {

  }

  @FXML
  private void saveProductButtonPushed(ActionEvent event) {

  }

  @FXML
  private void searchProductsHandler(ActionEvent event) {

  }

  //===========================================================================
  // Scene Initialization Helper Methods
  //===========================================================================
  public void initAddProduct() {
    addProduct = true;

  }
}
