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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
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
 * The AddModifyProductController class and associated methods handle logic and
 * updates to the Add/Modify product form view.
 *
 * @author Sakae Watanabe
 */
public class AddModifyProductController implements Initializable {

  //===========================================================================
  // Product Form Labels & Text Field FXIDS
  //===========================================================================

  /** Product Form label indicating either add or modify product status. */
  @FXML
  private Label productFormLabel;

  /** Product form ID text field for data collection. */
  @FXML
  private TextField productFormIDText;

  /** Product form Name text field for data collection. */
  @FXML
  private TextField productFormNameText;

  /** Product form Inventory text field for data collection. */
  @FXML
  private TextField productFormInvText;

  /** Product form Price text field for data collection. */
  @FXML
  private TextField productFormPriceText;

  /** Product form Maximum text field for data collection. */
  @FXML
  private TextField productFormMaxText;

  /** Product form Minimum text field for data collection. */
  @FXML
  private TextField productFormMinText;

  
  //===========================================================================
  // Available Part Form FXIDS
  //===========================================================================
  /** Product form text field for searching available part list. */
  @FXML
  private TextField productFormSearchText;

  /** Product form search available part button. */
  @FXML
  private Button productFormSearchButton;

  /** Product form TableView for the available parts list. */
  @FXML
  private TableView<Part> productFormAvailablePartView;

  /** Table column for available part ID field. */
  @FXML
  private TableColumn<Part, Integer> availablePartIDColumn;

  /** Table column for available part Name field. */
  @FXML
  private TableColumn<Part, String> availablePartNameColumn;

  /** Table column for available part Inventory field. */
  @FXML
  private TableColumn<Part, Integer> availablePartInvColumn;

  /** Table column for available part Price field. */
  @FXML
  private TableColumn<Part, Double> availablePartPriceColumn;

  /** Button to add part to associated parts list. */
  @FXML
  private Button productFormAddPartButton;


  //===========================================================================
  // Associated Part Table View FXIDS
  //===========================================================================

  /** Product form TableView for parts asssociated with current product. */
  @FXML
  private TableView<Part> productFormAssociatedPartView;

  /** Table column for associated part ID field. */
  @FXML
  private TableColumn<Part, Integer> associatedPartIDColumn;

  /** Table column for associated part Name field. */
  @FXML
  private TableColumn<Part, String> associatedPartNameColumn;

  /** Table column for associated part Inventory field. */
  @FXML
  private TableColumn<Part, Integer> associatedPartInvColumn;

  /** Table column for associated part Price field. */
  @FXML
  private TableColumn<Part, Double> associatedPartPriceColumn;

  /** Button to remove part from associated parts list.*/
  @FXML
  private Button removeAssociatedPartButton;


  //===========================================================================
  // Utility Buttons FXIDS
  //===========================================================================

  /** Button to save new or modified product. */
  @FXML
  private Button productFormSaveButton;

  /** Button for cancelling entry of new or modified product. */
  @FXML
  private Button productFormCancelButton;


  //===========================================================================
  // Class Members for Add/Modify Operations
  //===========================================================================
  /** The current Product is used when modifying or adding a new product. */
  private Product currentProduct;
  /** Flag to indicate if we are adding a new part. */
  private boolean addProduct;
  /** Index for currentProduct being modified in the main Inventory. */
  private int currentProductIndex;
  /** The associatedPartList holds the parts associated for current product. */
  private ObservableList<Part> associatedPartList = FXCollections.observableArrayList();



  //===========================================================================
  // Associate Part List Methods
  //===========================================================================

  /**
   * The searchAvailablePartsHandler attempts to filter parts matching the given
   * query. If no parts are found a popup dialog is generated letting the user
   * know. If no results matched a popup is generated letting the user know that
   * no results were found and the original list is restored. Search text that
   * is empty or all whitespace characters will also restore the original list.
   *
   * <p>
   *    <strong>
   *    RUNTIME EXCEPTION:
   *    Needed to correct InvocationTargetException that was encountered when
   *    implementing this method. Method name was updated during the process to
   *    clarify part list being searched. SetOnAction was updated for the search
   *    button associated with the text field but had not been updated for the
   *    text field itself. Scene would throw an InvocationTargetException when
   *    attempting to load the fxml. SetOnAction method updated for the text
   *    field resolved the issue.
   *    </strong>
   * </p>
   *
   *
   * @param event Event generated when user clicks the productFormSearch button.
   */
  @FXML
  private void searchAvailablePartsHandler(ActionEvent event) {
    String searchPart = productFormSearchText.getText().trim();
    ObservableList<Part> searchPartResults = FXCollections.observableArrayList();
    try {
      int id = Integer.parseInt(searchPart);
      Part foundPart = Inventory.lookupPart(id);
      if (foundPart != null) {
        searchPartResults.add(foundPart);
      }
    } catch (NumberFormatException e) {
      searchPartResults.addAll(Inventory.lookupPart(searchPart));
    }
    if (searchPartResults.isEmpty()) {
      productFormSearchText.clear();
      invalidPopup("No Results", "No results were found matching input.");
      productFormAvailablePartView.setItems(Inventory.getAllParts());
    } else {
      if (searchPart.equals("")) {
        productFormSearchText.clear();
      }
      productFormAvailablePartView.setItems(searchPartResults);
    }
  }

  /**
   * The addAssociatedPartPushed method adds the selected part from the
   * productFormAvailablePartView to the associatedPartList. If no part is
   * selected user is notified with a popup dialog.
   *
   * @param event Event captured when user pushes the productFormAddPart button.
   *
   * <p><strong>Runtime Error</strong></p>
   */
  @FXML
  private void addAssociatedPartPushed(ActionEvent event) {
    Part selectedPart = productFormAvailablePartView.getSelectionModel().getSelectedItem();
    if (selectedPart != null) {
      associatedPartList.add(selectedPart);
    } else {
        invalidPopup("No Part Selected", "A part must be selected to add associated part.");
    }
  }

  /**
   * The removeAssociatedButtonPushed method will remove the selected part from
   * the associated part list and table view. Popup dialog is generated if user
   * has no item selected.
   *
   * @param event Event captured when user pushes the removeAssociatedPartButton.
   */
  @FXML
  private void removeAssociatedButtonPushed(ActionEvent event) {
    Part selectedPart = productFormAssociatedPartView.getSelectionModel().getSelectedItem();
    if (selectedPart != null && (confirmPopup(event, "Remove Part?",
        "Part will be removed from associated parts."))) {
          associatedPartList.remove(selectedPart);
    } else {
      invalidPopup("No Part Selected", "You must select a part to remove.");
    }
  }


  //===========================================================================
  // Save & Cancel Button Methods
  //===========================================================================

  /**
   * The saveProductButtonPushed method determines if we are adding or modifying
   * the product and attempts to parse fields into proper data types to save a
   * new product to the inventory. NumberFormatException handled through message
   * dialog to the user.
   *
   * <p>
   * <ul>
   *   <li>Constraints for all Products</li>
   *   <ul>
   *     <li>Inv, Min, Max must be integers.</li>
   *     <li>Price must be a number.</li>
   *     <li>Min < Max</li>
   *     <li>Min < Inv < Max</li>
   *     <li>Name must be filled in.</li>
   *   </ul>
   * </ul>
   * </p>
   *
   *
   * @param event Event triggered by user pushing the saveProductButton.
   */
  @FXML
  private void saveProductButtonPushed(ActionEvent event) {
    int inv, min, max;
    double price;
    String name;

    try {
      name = productFormNameText.getText().trim();
      price = Double.parseDouble(productFormPriceText.getText().trim());
      inv = Integer.parseInt(productFormInvText.getText().trim());
      min = Integer.parseInt(productFormMinText.getText().trim());
      max = Integer.parseInt(productFormMaxText.getText().trim());
      // Products must have names.
      if (name.equals("")) {
        invalidPopup("Part Name Error", "Part name must be filled in.");
        return;
      }
      // Min value constraint check. Min < Max must be true.
      if (min >= max) {
        invalidPopup("Min Value Error", "Min must be less than Max");
        return;
      }
      // Inv value constraint check. Min < Inv < Max must be true.
      if ((inv <= min) || (inv >= max)) {
        invalidPopup("Inv Value Error", "Inv must be between Min and Max");
        return;
      }
      if (price < getAssociatedPartCost()) {
        invalidPopup("Pricing Error", "Product price should be at least the sum of it's parts.");
        return;
      }
      // Constraints passed -> save new product OR update product at index.
      if (addProduct) {
        int id = Inventory.getNextProductID();
        currentProduct = new Product(associatedPartList,id, name, price, inv, min, max);
        Inventory.addProduct(currentProduct);
        goToMainScreen(event);
      } else {
          int id = currentProduct.getId();
          currentProduct = new Product(associatedPartList, id, name, price, inv, min, max);
          Inventory.updateProduct(currentProductIndex, currentProduct);
          goToMainScreen(event);
      }
     } catch (NumberFormatException e) {
        invalidPopup("Invalid Input", "Please check your input.\n" +
          "Min, Max, and Inv fields must be whole numbers.\n" +
          "Price field must contain a number.");
    }
  }

  /**
   * The productFormCancelButtonPushed method has the user confirm they would
   * like to leave the AddModifyProduct screen and return to the main screen.
   * A confirmation dialog is shown to the user before exiting scene.
   *
   * @param event Event triggered by user pushing the cancel button.
   */
  @FXML
  private void productFormCancelButtonPushed(ActionEvent event) {
    String header = "All changes will be lost.";
    String content = "ARE YOU SURE YOU WANT TO CONTINUE?";

    if (confirmPopup(event, header, content)) {
      goToMainScreen(event);
    }
  }


  //===========================================================================
  // Scene Initialization & Helper Methods
  //===========================================================================

  /**
   * The Initialize method for Add/Modify Product screen loads the available
   * parts inventory and associated parts list into table views.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // Setup available inventory parts table
    availablePartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    availablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    availablePartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    availablePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    formatPricing(availablePartPriceColumn);
    productFormAvailablePartView.setItems(Inventory.getAllParts());

    // Setup associated parts table
    associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    associatedPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    formatPricing(associatedPartPriceColumn);
    productFormAssociatedPartView.setItems(associatedPartList);
  }

  /**
   * The initAddProduct helper method sets flag in the controller indicating we
   * are adding a product and sets the appropriate text on the form label.
   */
  public void initAddProduct() {
    addProduct = true;
    productFormLabel.setText("Add Product");
  }

  /**
   * The initAddProduct helper method sets flag in the controller indicating we
   * are modifying a product and sets the appropriate text on the form label.
   * Associated part list table view is set to the parts of current product.
   */
  public void initModProduct(Product product, int productIndex) {
    addProduct = false;
    currentProduct = product;
    currentProductIndex = productIndex;
    // Set product information fields.
    productFormIDText.setText( String.valueOf(product.getId()) );
    productFormNameText.setText( product.getName() );
    productFormPriceText.setText( String.valueOf(product.getPrice()) );
    productFormInvText.setText( String.valueOf(product.getStock()) );
    productFormMaxText.setText( String.valueOf(product.getMax()) );
    productFormMinText.setText( String.valueOf(product.getMin()) );
    // Set the list for associated parts in the view.
    associatedPartList.setAll( currentProduct.getAllAssociatedParts() );

    productFormLabel.setText("Modify Product");
  }

  /**
   * The goToMainScreen helper method is called when product has been added or
   * modified.
   *
   * @param event Action event passed from the partFormSaveButtonPushed method.
   */
  private void goToMainScreen(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/View_Controller/MainScreen.fxml"));
      Parent mainScreenParent = loader.load();
      Scene mainScreenScene = new Scene(mainScreenParent);

      Stage mainScreenWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
      mainScreenWindow.setScene(mainScreenScene);
      mainScreenWindow.show();
    } catch (IOException e) {
      invalidPopup("IOException", e.getMessage());
    }
  }

  /**
   * The getAssociatedPartCost method calculates the sum of the associated parts
   * list prices.
   *
   * @return Double value representing the sum of the associated parts prices.
   */
  private double getAssociatedPartCost() {
    double sumOfParts = 0;
    for (Part p : associatedPartList) {
      sumOfParts += p.getPrice();
    }
    return sumOfParts;
  }
}
