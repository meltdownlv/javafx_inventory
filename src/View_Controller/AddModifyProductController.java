package View_Controller;

import static View_Controller.MyUtils.confirmPopup;
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
 * TODO Write Javadoc comments for class header and all @FXML fields
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
  private Button productFormAddPartButton;

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
   * The Initialize method for Add/Modify Product screen loads the available parts inventory
   * and associated parts list into table views.
   *
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

  //===========================================================================
  // Associate Part List Methods
  //===========================================================================

  /**
   * The searchAvailablePartsHandler attempts to filter parts matching the given query.
   * If no parts are found a popup dialog is generated letting the user know.If no
   * results matched a popup is generated letting the user know that no results were found
   * and the original list is restored. Search text that is empty or all whitespace characters
   * will also restore the original list.
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
   * The addAssociatedPartPushed method adds the selected part from the productFormAvailablePartView
   * to the associatedPartList. If no part is selected user is notified with a popup dialog.
   *
   * @param event Event captured when user pushes the productFormAddPart button.
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
   * The removeAssociatedButtonPushed method will remove the selected part from the associated
   * part list and table view. Popup dialog is generated if user has no item selected.
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
   * The saveProductButtonPushed method determines if we are adding or modifying the
   * product and attempts to parse fields into proper data types to save a new product
   * to the inventory. NumberFormatException handled through message dialog to the user.
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
   * The productFormCancelButtonPushed method has the user confirm they would like
   * to leave the AddModifyProduct screen and return to the main screen. A confirmation
   * dialog is shown to the user.
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
   * The goToMainScreen helper method is called when product has been added or modified.
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
   * The getAssociatedPartCost method calculates the sum of the associated parts list
   * prices.
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
