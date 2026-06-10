/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.FamilyDAO;
import Models.Family;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class Coordinator_FamilyManagmentController implements Initializable {

    @FXML
    private MenuBar menubar;
    @FXML
    private RadioMenuItem arialbtn;
    @FXML
    private RadioMenuItem SansSerifbtn;
    @FXML
    private RadioMenuItem timesnewbtn;
    @FXML
    private RadioMenuItem smallbtn;
    @FXML
    private RadioMenuItem mediambtn;
    @FXML
    private RadioMenuItem largebtn;
    @FXML
    private RadioMenuItem lightbtn;
    @FXML
    private RadioMenuItem darkbtn;
    @FXML
    private TextField familyNameField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField familySizeField;
    @FXML
    private ComboBox<String> vulnerabilityComb;
    @FXML
    private TextField nationalIdField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker registrationDatePicker;
    @FXML
    private DatePicker lastAidDatePicker;
    private TableView<Family> familyTable;
    FamilyDAO famDao=new FamilyDAO();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
  vulnerabilityComb.getItems().addAll("HIGH", "MEDIUM", "LOW");

    }    

    @FXML
    private void addFamilyHandle(ActionEvent event) {
    if (familyNameField.getText().isEmpty() ||
        nationalIdField.getText().isEmpty() ||
        locationField.getText().isEmpty() ||
        phoneField.getText().isEmpty() ||
        familySizeField.getText().isEmpty() ||
        vulnerabilityComb.getValue() == null ||
        registrationDatePicker.getValue() == null) {

        showAlert("Validation Error", "Missing Data", "Please fill in all required fields");
        return;
    }

    Family family = new Family();
    family.setHouseholdName(familyNameField.getText());
    family.setNationalId(nationalIdField.getText());
    family.setLocation(locationField.getText());
    family.setPhone(phoneField.getText());
    family.setFamilySize(Integer.parseInt(familySizeField.getText()));
    family.setVulnerabilityLevel(vulnerabilityComb.getValue());
     family.setRegistrationDate(java.sql.Date.valueOf(registrationDatePicker.getValue()));

    // last Aid Date(Optional)
    if (lastAidDatePicker.getValue() != null) {
        family.setLastAidDate(java.sql.Date.valueOf(lastAidDatePicker.getValue()));
    } else {
        family.setLastAidDate(null); 
    }
             
    Family existingfam = famDao.SearchByNationalId(family.getNationalId());
    if (existingfam != null) {
        showAlert("Duplicate Error", "Family Exists",
                  "A family with this National ID already exists!");
        return;
    }

   boolean added = famDao.AddFamily(family);
    if (added) {
    showConfirmationAlert("Success", "Family Added", "Family registered successfully!");
    resetBtnHandle(null);
       } else {
    showAlert("Error", "Failed", "Could not add family!");
}
    }

    @FXML
    private void resetBtnHandle(ActionEvent event) {
           familyNameField.clear();
        nationalIdField.clear();
        locationField.clear();
        phoneField.clear();
        familySizeField.clear();
        vulnerabilityComb.getSelectionModel().clearSelection();
        registrationDatePicker.setValue(null);
        lastAidDatePicker.setValue(null);
        
    }

    @FXML
    private void backBtnHandle(ActionEvent event) {
     try {
         Parent p = FXMLLoader.load(getClass().getResource("/View/CoordinatorDashboard.fxml"));
        Scene sc= new Scene(p);
        Stage stage = new Stage();
        stage.setTitle("GHADS -Coordinator Dashboard");
        stage.setScene(sc);
        stage.show();
    } catch (Exception e) {

    }
    }
    
    @FXML
    private void handleExit(ActionEvent event) {
     ((Stage)  menubar.getScene().getWindow()).close();

    }

    @FXML
    private void arialFontHandle(ActionEvent event) { 
        if (arialbtn.isSelected()) {
         menubar.getScene().getRoot().setStyle("-fx-font-family: 'Arial';");
    }
    }

    @FXML
    private void SansSerifFontHandle(ActionEvent event) {
         if (SansSerifbtn.isSelected()) {
         menubar.getScene().getRoot().setStyle("-fx-font-family: 'Sans Serif';");
    }  
    }

    @FXML
    private void TimeNewFontHandle(ActionEvent event) {
          if (timesnewbtn.isSelected()) {
         menubar.getScene().getRoot().setStyle("-fx-font-family: 'Times New Roman';");
    }
    }

    @FXML
    private void smallFontHandle(ActionEvent event) {
          if(smallbtn.isSelected()){
         menubar.getScene().getRoot().setStyle("-fx-font-size: 11px;");
          }
    }

    @FXML
    private void MediamFontHandle(ActionEvent event) {
          if (mediambtn.isSelected()) {
         menubar.getScene().getRoot().setStyle("-fx-font-size: 15px;");
    }
    }

    @FXML
    private void LargeFontHandle(ActionEvent event) {
          if (largebtn.isSelected()) {
         menubar.getScene().getRoot().setStyle("-fx-font-size: 17px;");
    }
    }

    @FXML
    private void LightBackgroud(ActionEvent event) {
           if (lightbtn.isSelected()) {
         menubar.getScene().getRoot().setStyle("-fx-background-color: white;");
    }
    }

    @FXML
    private void darkBackgroud(ActionEvent event) {
          if (darkbtn.isSelected()) {
         menubar.getScene().getRoot().setStyle("-fx-background-color: #2c2c2c; -fx-text-fill: white;");
        }
    }

    @FXML
    private void aboutHandle(ActionEvent event) {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("About GHADS");
    alert.setHeaderText("Gaza Humanitarian Aid Distribution System");
    alert.setContentText("Developed by Mayar Abo Alajeen\nVersion 1\nThis app helps manage aid distribution fairly.");
    alert.showAndWait();

    }
     private void showAlert(String title,String header,String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); 
    }

    private boolean showConfirmationAlert(String title,String header,String content){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    
}
