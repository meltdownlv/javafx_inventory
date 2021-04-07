package View_Controller;

import Model.Part;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

  /** The current Part is used when modifying an existing part. */
  private Part currentPart;
  /** The toggle group for part type radio buttons. */
  private ToggleGroup partTypeToggleGroup;

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
    if(this.partTypeToggleGroup.getSelectedToggle().equals(this.partFormInHouseRadio)) {
      partFormTypeLabel.setText("Machine ID");
    }
    if (this.partTypeToggleGroup.getSelectedToggle().equals(this.partFormOutsourcedRadio)) {
      partFormTypeLabel.setText("Company Name");
    }
  }

  @FXML
  public void partFormSaveButtonPushed(ActionEvent event) {
  }

  /**
   * The initAddPart method prepares the scene for adding a new part.
   * Form label is updated to represent current process.
   *
   */
  public void initAddPart() {
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

  public void sillyThings() {
    System.out.println("Yes this just happened.");
  }
}
