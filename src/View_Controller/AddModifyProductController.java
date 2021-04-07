package View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
  private TableView<?> productFormAvailablePartView;

  @FXML
  private TableColumn<?, ?> availablePartIDColumn;

  @FXML
  private TableColumn<?, ?> availablePartNameColumn;

  @FXML
  private TableColumn<?, ?> availablePartInvColumn;

  @FXML
  private TableColumn<?, ?> availablePartPriceColumn;

  @FXML
  private Button productFormAddPartButton;

  @FXML
  private TableView<?> productFormAssociatedPartView;

  @FXML
  private TableColumn<?, ?> associatedPartIDColumn;

  @FXML
  private TableColumn<?, ?> associatedPartNameColumn;

  @FXML
  private TableColumn<?, ?> associatedPartInvColumn;

  @FXML
  private TableColumn<?, ?> associatedPartPriceColumn;

  @FXML
  private Button removeAssociatedPartButton;

  @FXML
  private Button productFormSaveButton;

  @FXML
  private Button productFormCancelButton;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  @FXML
  void addPartToProductPushed(ActionEvent event) {

  }

  @FXML
  void productFormCancelButtonPushed(ActionEvent event) {

  }

  @FXML
  void removeAssociatedButtonPushed(ActionEvent event) {

  }

  @FXML
  void saveProductButtonPushed(ActionEvent event) {

  }

  @FXML
  void searchProductsHandler(ActionEvent event) {

  }

}
