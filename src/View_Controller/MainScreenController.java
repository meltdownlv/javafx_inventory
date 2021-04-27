package View_Controller;

import static View_Controller.MyUtils.confirmPopup;
import static View_Controller.MyUtils.formatPricing;
import static View_Controller.MyUtils.invalidPopup;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

  //===========================================================================
  // Part Table View FXIDS
  //===========================================================================

  /** Search text field for Parts TableView on main screen. */
  @FXML
  private TextField mainPartSearchText;

  /** Search button for Parts TableView on main screen. */
  @FXML
  private Button mainPartSearchButton;

  /** Part TableView for main screen displaying all available parts. */
  @FXML
  private TableView<Part> mainPartTableView;

  /** Table column for available part ID field.*/
  @FXML
  private TableColumn<Part, Integer> mainPartIDColumn;

  /** Table column for available part Name field.*/
  @FXML
  private TableColumn<Part, String> mainPartNameColumn;

  /** Table column for available part Inventory field.*/
  @FXML
  private TableColumn<Part, Integer> mainPartInvColumn;

  /** Table column for available part Price field.*/
  @FXML
  private TableColumn<Part, Double> mainPartPriceColumn;

  /** Button to add new part to the available inventory. */
  @FXML
  private Button mainAddPartButton;

  /** Button to modify currently selected part information. */
  @FXML
  private Button mainModPartButton;

  /** Button to delete a product from the inventory. */
  @FXML
  private Button mainDeletePartButton;

  /** Text label for user notification of cancelled delete part action. */
  @FXML
  private Label deletePartCancelLabel;

  //===========================================================================
  // Product Table View FXIDS
  //===========================================================================

  /** Search text field for Products TableView on main screen. */
  @FXML
  private TextField mainProductSearchText;

  /** Search button for Products TableView on main screen. */
  @FXML
  private Button mainProductSearchButton;

  /** Product TableView for main screen displaying all available products. */
  @FXML
  private TableView<Product> mainProductTableView;

  /** Table column for available product ID field. */
  @FXML
  private TableColumn<Product, Integer> mainProductIDColumn;

  /** Table column for available product Name field. */
  @FXML
  private TableColumn<Product, String> mainProductNameColumn;

  /** Table column for available product Inventory field. */
  @FXML
  private TableColumn<Product, Integer> mainProductInvColumn;

  /** Table column for available product Price field. */
  @FXML
  private TableColumn<Product, Double> mainProductPriceColumn;

  /** Button to add new product to the available inventory. */
  @FXML
  private Button mainAddProductButton;

  /** Button to modify product in the available inventory. */
  @FXML
  private Button mainModProductButton;

  /** Button to delete a product from the available inventory. */
  @FXML
  private Button mainDeleteProductButton;

  /** Text label for user notification of cancelled delete product action. */
  @FXML
  private Label deleteProductCancelLabel;


  //===========================================================================
  // Utility FXIDS
  //===========================================================================

  /** Button to exit the program. */
  @FXML
  private Button mainExitButton;


  /**
   * The Initialize method sets up items for both Part and Product table views.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    mainPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    mainPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    mainPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    mainPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    formatPricing(mainPartPriceColumn);

    mainPartTableView.setItems(Inventory.getAllParts());

    mainProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    mainProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    mainProductInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    mainProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    formatPricing(mainProductPriceColumn);

    mainProductTableView.setItems(Inventory.getAllProducts());

  }

  //===========================================================================
  // Part Table View Handlers
  //===========================================================================

  /**
   * The mainSearchPartHandler method responds to user input in the searchPart
   * field to either lookup a list of parts matching the given ID or part name.
   * If no results matched a popup is generated letting the user know that no
   * results were found and the original list is restored. Search text that is
   * empty or all whitespace characters will also restore the original list.
   *
   * @param event Event generated by user pushing searchPart button OR pressing
   *              Enter key after query has been typed.
   */
  @FXML
  private void mainSearchPartHandler(ActionEvent event) {
    String searchPart = mainPartSearchText.getText().trim();
    ObservableList<Part> searchPartResults = FXCollections.observableArrayList();
    try {
        int id = Integer.parseInt(searchPart);
        Part foundPart = Inventory.lookupPart(id);
        if (foundPart != null){
          searchPartResults.add(foundPart);
        }
    } catch (NumberFormatException e) {
        searchPartResults.addAll(Inventory.lookupPart(searchPart));
    }
    if (searchPartResults.isEmpty()) {
      mainPartSearchText.clear();
      invalidPopup("No Results", "No results were found matching input.");
      mainPartTableView.setItems(Inventory.getAllParts());
    } else {
      if (searchPart.equals("")) {
        mainPartSearchText.clear();
      }
      mainPartTableView.setItems(searchPartResults);
    }
  }

  /**
   * The addPartButtonPushed method handles changing the scene when the
   * addPartButton is pushed. A call is made to the AddModifyPartController
   * initAddPart method to prepare label for the scene.
   *
   * @param event Event captured when user pushes the addPartButton.
   */
  @FXML
  private void addPartButtonPushed(ActionEvent event) throws IOException {
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

  /**
   * The modPartButtonPushed method handles changing the scene when the
   * modPartButton is pushed. A call is made to the AddModifyPartController
   * initModPart method to prepare label and text fields for the modify part
   * scene. Popup dialog is generated if user has no item selected.
   *
   * @param event Event captured when user pushes the modPartButton.
   */
  @FXML
  private void modPartButtonPushed(ActionEvent event) throws IOException {
    Part selectedPart = mainPartTableView.getSelectionModel().getSelectedItem();
    int partIndex = Inventory.getAllParts().indexOf(selectedPart);

    if (selectedPart != null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/View_Controller/AddModifyPart.fxml"));
      Parent modPartParent = loader.load();
      Scene modPartScene = new Scene(modPartParent);

      AddModifyPartController controller = loader.getController();
      controller.initModPart(selectedPart, partIndex);

      Stage addPartWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
      addPartWindow.setScene(modPartScene);
      addPartWindow.show();
    } else {
      invalidPopup("No Part Selected", "Please select a part to modify.");
    }
  }

  /**
   * The deletePartButtonPushed method will delete the selected part from the
   * mainPartTableView after user confirms the action. Popup dialog is generated
   * if user has no item selected.
   *
   * @param event Event captured when user pushes the deletePartButton.
   */
  @FXML
  private void deletePartButtonPushed(ActionEvent event) {
    Part selectedPart = mainPartTableView.getSelectionModel().getSelectedItem();
    deletePartCancelLabel.setText("");
    deleteProductCancelLabel.setText("");

    if (selectedPart != null) {
        if (confirmPopup(event, "Are You Sure?",
            "Part will be permanently deleted from Inventory.")) {
          Inventory.deletePart(selectedPart);
        } else {
          deletePartCancelLabel.setText("Part delete operation cancelled.");
        }
    } else {
      invalidPopup("No Part Selected", "Please select a part to delete.");
    }
  }

  //===========================================================================
  // Product Table View Handlers
  //===========================================================================

  /**
   * The mainSearchProductHandler method responds to user input in the
   * searchProduct field to either lookup a list of matching products by given
   * ID or part name. If no results matched a popup is generated letting user
   * know and restoring the table to the original list. Search text that is
   * empty or all whitespace characters will also restore the original list.
   *
   * @param event Event generated by user pushing searchProduct button OR pressing
   *              Enter key after query has been typed.
   */
  @FXML
  private void mainSearchProductHandler(ActionEvent event) {
    String searchProduct = mainProductSearchText.getText().trim();
    ObservableList<Product> searchProductResults = FXCollections.observableArrayList();

    try {
      int id = Integer.parseInt(searchProduct);
      Product foundProduct = Inventory.lookupProduct(id);
      if (foundProduct != null) {
        searchProductResults.add(foundProduct);
      }
    } catch (NumberFormatException e) {
        searchProductResults.addAll(Inventory.lookupProduct(searchProduct));
    }
    if (searchProductResults.isEmpty()) {
      mainProductSearchText.clear();
      invalidPopup("No Results", "No results were found matching input.");
      mainProductTableView.setItems(Inventory.getAllProducts());
    } else {
      if (searchProduct.equals("")) {
        mainProductSearchText.clear();
      }
      mainProductTableView.setItems(searchProductResults);
    }
  }

  /**
   * The addProductButtonPushed handles changing the scene when the addProduct
   * button is pushed. A call is made to the AddModifyProductController to
   * initialize the scene with proper labels and flags.
   *
   * @param event Event captured when user clicks on the addProductButtonPushed
   *              button
   */
  @FXML
  private void addProductButtonPushed(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/View_Controller/AddModifyProduct.fxml"));
    Parent addProductParent = loader.load();
    Scene addProductScene = new Scene(addProductParent);

    AddModifyProductController controller = loader.getController();
    controller.initAddProduct();

    Stage addProductWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
    addProductWindow.setScene(addProductScene);
    addProductWindow.show();
  }

  /**
   * The modProductButtonPushed handles changing the scene when the modProduct
   * button is pushed. A call is made to the AddModifyProductController to
   * initialize the scene with proper labels and flags.
   *
   * @param event Event captured when user clicks on the modProductButtonPushed button.
   */
  @FXML
  private void modProductButtonPushed(ActionEvent event) throws IOException {
    Product selectedProduct = mainProductTableView.getSelectionModel().getSelectedItem();
    int productIndex = Inventory.getAllProducts().indexOf(selectedProduct);
    if (selectedProduct != null) {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/View_Controller/AddModifyProduct.fxml"));
      Parent addProductParent = loader.load();
      Scene addProductScene = new Scene(addProductParent);

      AddModifyProductController controller = loader.getController();
      controller.initModProduct(selectedProduct, productIndex);

      Stage addProductWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
      addProductWindow.setScene(addProductScene);
      addProductWindow.show();
    } else {
      invalidPopup("No Product Selected.", "Please select a product to modify.");
    }
  }
  
  // TODO: Move this into the parts section and add javadoc && Add to handlers in fxml
  @FXML
  public void partRowClicked(MouseEvent event) {
    if(event.getClickCount() == 2) {
      ActionEvent action = new ActionEvent(event.getSource(), event.getTarget());
      try {
        modPartButtonPushed(action);
      } catch(IOException e) {
        invalidPopup("Input Error", "Error when selecting part.");
      }
    }
  }

  // TODO: Add javadoc comments && Add to handlers in fxml
  @FXML
  public void productRowClicked(MouseEvent event) {
    if(event.getClickCount() == 2) {
      ActionEvent action = new ActionEvent(event.getSource(), event.getTarget());
      try {
        modProductButtonPushed(action);
      } catch(IOException e) {
        invalidPopup("Input Error", "Error when selecting product.");
      }
    }
  }
  
  /**
   * The deleteProductButtonPushed method attempts to delete the selected product
   * from the inventory. Confirmation dialog is given to user if the product is
   * selected and has no associated parts. Popup dialogs are presented to the
   * user if no product is selected or the product still has associated parts.
   *
   * @param event Event triggered by user pushing the deleteProductButton.
   */
  @FXML
  private void deleteProductButtonPushed(ActionEvent event) {
    Product selectedProduct = mainProductTableView.getSelectionModel().getSelectedItem();
    deleteProductCancelLabel.setText("");
    deletePartCancelLabel.setText("");

    if (selectedProduct != null) {
      if (selectedProduct.getAllAssociatedParts().isEmpty()) {
        if (confirmPopup(event, "Delete Product?", "Product will be deleted from inventory.")) {
            Inventory.deleteProduct(selectedProduct);
        } else {
          deleteProductCancelLabel.setText("Product delete operation cancelled.");
        }
      } else {
        invalidPopup("Associated Parts Warning",
            "You must remove all associated parts before deleting product.");
      }
    } else {
        invalidPopup("No Product Selected.", "Please select a product to delete.");
    }
  }


  //===========================================================================
  // Additional Button Handlers
  //===========================================================================

  /**
   * The exitButtonPushed method confirms if user would like to exit the program
   * and calls exit if user pushes ok.
   *
   * @param event Event triggered by user pushing exitButton.
   */
  @FXML
  private void exitButtonPushed(ActionEvent event) {
    if (confirmPopup(event, "Are you sure?", "Please confirm to close the program.")) {
      System.exit(0);
    }
  }
}
