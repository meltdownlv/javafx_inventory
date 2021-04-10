package View_Controller;

import static View_Controller.MyUtils.confirmPopup;
import static View_Controller.MyUtils.invalidPopup;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * TODO Write Javadoc comments for class header and all @FXML fields
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

  }

  //===========================================================================
  // Button Handler Methods
  //===========================================================================

  /**
   * The partFormRadioClicked method watches for radio button selection from
   * the partTypeToggleGroup on the form. Updates label text for the partFormType
   * label.
   *
   * @param event Action event from radio button selection from partTypeToggleGroup.
   */
  @FXML
  private void partFormRadioClicked(ActionEvent event) {
    if(partTypeToggleGroup.getSelectedToggle().equals(partFormInHouseRadio)) {
      partFormTypeLabel.setText("Machine ID");
    }
    if (partTypeToggleGroup.getSelectedToggle().equals(partFormOutsourcedRadio)) {
      partFormTypeLabel.setText("Company Name");
    }
  }

  /**
   * The partFormSaveButtonPushed determines if we are adding or modifying the Part
   * and attempts to parse the fields into the proper data types to save a new part to
   * the inventory. NumberFormatException handled through message dialog to the user.
   * <p>
   * <ul>
   *   <li>Constraints for all Parts</li>
   *   <ul>
   *     <li>Inv, Min, Max must be integers.</li>
   *     <li>Price must be a number.</li>
   *     <li>Min < Max</li>
   *     <li>Min < Inv < Max</li>
   *     <li>Name must be filled in.</li>
   *   </ul>
   *   <em><li>For InHouseParts</li></em>
   *   <ul>
   *     <li>MachineID must be an integer.</li>
   *   </ul>
   *   <em><li>For OutsourcedParts</li></em>
   *    <ul>
   *      <li>CompanyName must be filled in.</li>
   *    </ul>
   * </ul>
   * </p>
   *
   * @param event Event triggered by user pushing the partFormSaveButton.
   */
  @FXML
  private void partFormSaveButtonPushed(ActionEvent event) {
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
        if (machineId == 0) {
          invalidPopup("Machine ID Error", "MachineID must be larger than 0.");
          return;
        }
      } else {
        companyName = partFormTypeText.getText().trim();
        if (companyName.equals("")) {
          invalidPopup("Company Name Error", "Company name must be filled in.");
          return;
        }
      }
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
      // Add new part to inventory or update part being modified.
      if (addPart) {
        // Assign next available id from Inventory for a new part
        int id = Inventory.getNextPartID();
        if (inHouse) {
          currentPart = new InHousePart(id, name, price, inv, min, max, machineId);
        } else {
          currentPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
        }
        Inventory.addPart(currentPart);
        goToMainScreen(event);  // Return to main screen exit point 1 of 2.
        return;
      } else if (!addPart && inHouse) {
        int id = Integer.parseInt(partFormIDText.getText());
        currentPart = new InHousePart(id, name, price, inv, min, max, machineId);
      } else {
        int id = Integer.parseInt(partFormIDText.getText());
        currentPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
      }
      Inventory.updatePart(currentPartIndex, currentPart);
      goToMainScreen(event); // Return to main screen exit point 2 of 2.
    } catch (NumberFormatException e) {
      invalidPopup("Invalid Input", "Please check your input.\n" +
          "Min, Max, and Inv fields must be whole numbers.\n" +
          "Price field must contain a number.\n" +
          "For InHouse parts MachineID must be a whole number.");
    }
  }

  /**
   * The partFormCancelButtonPushed method has the user confirm they would like
   * to leave the AddModifyPart screen and return to the main screen. A confirmation
   * dialog is shown to the user.
   *
   * @param event Event triggered by user pushing cancel button.
   */
  @FXML
  private void partFormCancelButtonPushed(ActionEvent event) {
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
   * The initAddPart method prepares the scene for adding a new part.
   * Form label is updated to represent current process. Sets addPart
   * to true to indicate we will be adding a new part.
   */
  public void initAddPart() {
    addPart = true;
    setPartTypes();
    partFormLabel.setText("Add Part");
    partFormInHouseRadio.setSelected(true);
    partFormTypeLabel.setText("Machine ID");
  }

  /**
   * The initModPart method prepares the scene for modifying a part and
   * updating the inventory. Text fields are set for all shared member fields before
   * checking which sub-type the part belongs to for the final field.
   */
  public void initModPart(Part part, int partIndex) {
    addPart = false;
    currentPartIndex = partIndex;
    setPartTypes();
    partFormLabel.setText("Modify Part");
    partFormOutsourcedRadio.setSelected(true);
    partFormTypeLabel.setText("Company Name");

    // Members shared by all Part classes.
    partFormIDText.setText( String.valueOf(part.getId()) );
    partFormNameText.setText( part.getName() );
    partFormInvText.setText( String.valueOf(part.getStock()) );
    partFormPriceText.setText( String.valueOf(part.getPrice()) );
    partFormMaxText.setText( String.valueOf(part.getMax()) );
    partFormMinText.setText( String.valueOf(part.getMin()) );
    // Check for subclass member.
    if (part instanceof InHousePart) {
      partFormTypeText.setText( String.valueOf( ((InHousePart) part).getMachineID()) );
    } else {
      partFormTypeText.setText( ((OutsourcedPart)part).getCompanyName() );
    }
  }

  /**
   * The setPartTypes method is used in the initialize method to setup the
   * toggle group for types of parts.
   */
  private void setPartTypes() {
    partTypeToggleGroup = new ToggleGroup();
    this.partFormInHouseRadio.setToggleGroup(partTypeToggleGroup);
    this.partFormOutsourcedRadio.setToggleGroup(partTypeToggleGroup);
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
}
