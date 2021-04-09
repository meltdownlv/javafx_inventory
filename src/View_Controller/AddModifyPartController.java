package View_Controller;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * @author Sakae Watanabe
 */
public class AddModifyPartController implements Initializable {
  @FXML
  private Label partFormLabel;

  @FXML
  private RadioButton partFormInHouseRadio;

  @FXML
  private RadioButton partFormOutsourcedRadio;

  @FXML
  private Label partFormTypeLabel;

  @FXML
  private TextField partFormIDText;

  @FXML
  private TextField partFormNameText;

  @FXML
  private TextField partFormInvText;

  @FXML
  private TextField partFormPriceText;

  @FXML
  private TextField partFormMaxText;

  @FXML
  private TextField partFormTypeText;

  @FXML
  private TextField partFormMinText;

  @FXML
  private Button partFormSaveButton;

  @FXML
  private Button partFormCancelButton;

  /** The current Part is used when modifying or adding a part. */
  private Part currentPart;
  /** The toggle group for part type radio buttons. */
  private ToggleGroup partTypeToggleGroup;
  /** Flag to indicate if we are adding a new part. */
  private boolean addPart;
  /** Index for currentPart being modified in the main Inventory. */
  private int currentPartIndex;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    setPartTypes();
  }

  @FXML
  public void partFormCancelButtonPushed(ActionEvent event) {

  }

  /**
   * The partFormRadioClicked method watches for radio button selection from
   * the partTypeToggleGroup on the form. Updates label text for the partFormType
   * label.
   *
   * @param event Action event from radio button selection from partTypeToggleGroup.
   */
  @FXML
  public void partFormRadioClicked(ActionEvent event) {
    if(partTypeToggleGroup.getSelectedToggle().equals(partFormInHouseRadio)) {
      partFormTypeLabel.setText("Machine ID");
    }
    if (partTypeToggleGroup.getSelectedToggle().equals(partFormOutsourcedRadio)) {
      partFormTypeLabel.setText("Company Name");
    }
  }

  /**
   * The partFormSaveButtonPushed determines if we are adding or modifying the Part
   * and calls the proper method for saving the part.
   *
   * @param event Action event triggered by user pushing the partFormSaveButton.
   */
  @FXML
  public void partFormSaveButtonPushed(ActionEvent event) {
      if (addPart) {
        saveNewPart();
      } //else {
//        modifyExistingPart();
//      }
  }

  /**
   * The saveNewPart method attempts to parse the fields into the proper data types
   * in order to save a new part to the inventory. NumberFormatException handled through
   * message dialog to the user. Checks for Min < Max and Min < Inv < Max before moving
   * to save part.
   */
  public void saveNewPart() {
    boolean okToSave = true;
    boolean inHouse = partTypeToggleGroup.getSelectedToggle().equals(partFormInHouseRadio);
    int machineId = -1;
    int inv, min, max;
    double price;
    String name;
    String companyName = "";

    try {
      name = partFormNameText.getText().trim();
      price = Double.parseDouble(partFormPriceText.getText().trim());
      inv = Integer.parseInt(partFormInvText.getText().trim());
      min = Integer.parseInt(partFormMinText.getText().trim());
      max = Integer.parseInt(partFormMaxText.getText().trim());
      if (inHouse) {
        machineId = Integer.parseInt(partFormTypeText.getText().trim());
      } else {
          companyName = partFormTypeText.getText().trim();
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
    // Assign next available id from Inventory
      int id = Inventory.getNextPartID();
      if (inHouse) {
        currentPart = new InHousePart(id, name, price, inv, min, max, machineId);
      } else {
        currentPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
      }
      Inventory.addPart(currentPart);
      // TODO Return to main screen function
    } catch (NumberFormatException e) {
      okToSave = false;
      invalidPopup("Invalid Input", "Please check your input.\n" +
                    "Min, Max, and Inv fields must be whole numbers.\n" +
                    "Price field must contain a number.\n" +
                    "For InHouse parts MachineID must be a whole number.");
    }
  }


  /**
   * The initAddPart method prepares the scene for adding a new part.
   * Form label is updated to represent current process. Sets addPart
   * to true to indicate we will be adding a new part.
   */
  public void initAddPart() {
    addPart = true;
    partFormLabel.setText("Add Part");
  }

  /**
   * The setPartTypes method is used in the initialize method to setup the
   * toggle group for types of parts.
   */
  private void setPartTypes() {
    partTypeToggleGroup = new ToggleGroup();
    this.partFormInHouseRadio.setToggleGroup(partTypeToggleGroup);
    this.partFormOutsourcedRadio.setToggleGroup(partTypeToggleGroup);
    partFormInHouseRadio.setSelected(true);
    partFormTypeLabel.setText("Machine ID");
  }


  /**
   * The invalidPopup is a helper method that will popup a warning message advising
   * the type of issue along with a short message to the user.
   *
   * @param warnType The type of warning being issued to the user.
   * @param message The context of the warning message to be displayed.
   */
  private void invalidPopup(String warnType, String message) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(warnType);
    alert.setHeaderText(warnType);
    alert.setContentText(message);
    alert.showAndWait();
  }


}
