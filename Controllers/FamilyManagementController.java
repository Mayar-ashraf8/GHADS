/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.FamilyDAO;
import Models.Family;
import Models.User;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class FamilyManagementController implements Initializable {

    @FXML
    private MenuBar menuBar;
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
    private TextField nationalIdField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField familySizeField;
    @FXML
    private DatePicker registrationDatePicker;
    @FXML
    private DatePicker lastAidDatePicker;
    @FXML
    private TableView<Family> familyTable;
    @FXML
    private ComboBox<String> vulnerabilityComb;
    @FXML
    private TableColumn<Family, Integer> idFamilyCol;
    @FXML
    private TableColumn<Family, String> householdCol;
    @FXML
    private TableColumn<Family, Integer> NationalCol;
    @FXML
    private TableColumn<Family, String> LocationCol;
    @FXML
    private TableColumn<Family, String> phoneCol;
    @FXML
    private TableColumn<Family, Integer> FamilysizeCol;
    @FXML
    private TableColumn<Family, String> VulnerabilityCol;
    @FXML
    private TableColumn<Family,LocalDate> RegistrationCol;
    @FXML
    private TableColumn<Family,LocalDate> lastAidCol;
    FamilyDAO famDao=new FamilyDAO();
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vulnerabilityComb.getItems().addAll("HIGH", "MEDIUM", "LOW");
        idFamilyCol.setCellValueFactory(new PropertyValueFactory<>("familyId"));
        householdCol.setCellValueFactory(new PropertyValueFactory<>("householdName"));
        NationalCol.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        LocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        FamilysizeCol.setCellValueFactory(new PropertyValueFactory<>("familySize"));
        VulnerabilityCol.setCellValueFactory(new PropertyValueFactory<>("vulnerabilityLevel"));
        RegistrationCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        lastAidCol.setCellValueFactory(new PropertyValueFactory<>("lastAidDate"));

        familyTable.getSelectionModel().selectedItemProperty().
                addListener(event -> selectedFamilyRow());
        
         List<Family> families = famDao.ShowAllFamilies();
         familyTable.getItems().setAll(families);

     
    }
    
    @FXML
    private void addFamilyHandle(ActionEvent event) {
      if (familyNameField.getText().trim().isEmpty() ||
        nationalIdField.getText().trim().isEmpty() ||
        locationField.getText().trim().isEmpty() ||
        phoneField.getText().trim().isEmpty() ||
        familySizeField.getText().trim().isEmpty() ||
        vulnerabilityComb.getValue() == null ||
        registrationDatePicker.getValue() == null ||
        lastAidDatePicker.getValue() == null) {

        showAlert("ERROR", "Wrong Data", "Please fill the fields");
        return;
    }
        Family fam = new Family();
        fam.setHouseholdName(familyNameField.getText());
        fam.setNationalId(nationalIdField.getText());
        fam.setLocation(locationField.getText());
        fam.setPhone(phoneField.getText());
        fam.setFamilySize(Integer.parseInt(familySizeField.getText()));
        fam.setVulnerabilityLevel(vulnerabilityComb.getValue());
        fam.setRegistrationDate(java.sql.Date.valueOf(registrationDatePicker.getValue()));
        fam.setLastAidDate(java.sql.Date.valueOf(lastAidDatePicker.getValue()));
        
        if (checkValidData(fam, true)) {
            boolean addfam = famDao.AddFamily(fam);
            if (addfam) {
                showConfirmationAlert("Done", "New Family", "Family added successfully!");
              List<Family> families = famDao.ShowAllFamilies();
              familyTable.getItems().setAll(families);
                resetBtnHandle(null);
            } else {
                showAlert("ERROR", "Failed", "Could not add family!");
            }
        }
    }

    @FXML
    private void editFamilyHandle(ActionEvent event) {
        Family fam = familyTable.getSelectionModel().getSelectedItem();
        if (fam != null) {
            fam.setHouseholdName(familyNameField.getText());
            fam.setNationalId(nationalIdField.getText());
            fam.setLocation(locationField.getText());
            fam.setPhone(phoneField.getText());
            fam.setFamilySize(Integer.parseInt(familySizeField.getText()));
            fam.setVulnerabilityLevel(vulnerabilityComb.getValue());
             fam.setRegistrationDate(java.sql.Date.valueOf(registrationDatePicker.getValue()));
        fam.setLastAidDate(java.sql.Date.valueOf(lastAidDatePicker.getValue()));
           
            if (checkValidData(fam, false)) {
                boolean updated = famDao.UpdateFamily(fam);
                if (updated) {
                    showConfirmationAlert("Update", "Update Done", "Family updated successfully");
                       List<Family> families = famDao.ShowAllFamilies();
                        familyTable.getItems().setAll(families);
                    resetBtnHandle(null);
                } else {
                    showAlert("ERROR", "Failed", "Could not update family");
                }
            }
        } else {
            showAlert("Warning", "No Selection", "Please select a family to edit");
        }
    }

    @FXML
    private void deleteFamilyHandle(ActionEvent event) {
        Family fam = familyTable.getSelectionModel().getSelectedItem();
        if (fam != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this family?", ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm Delete");
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                boolean deleted = famDao.DeleteFamily(fam.getFamilyId());
                if (deleted) {
                    showConfirmationAlert("Done", "Delete Family", "Family deleted successfully");
                      List<Family> families = famDao.ShowAllFamilies();
                        familyTable.getItems().setAll(families);
                    resetBtnHandle(null);
                } else {
                    showAlert("ERROR", "Failed", "Could not delete family");
                }
            }
        } else {
            showAlert("ERROR", "No Selection", "Please select a family to delete");
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
         Parent p = FXMLLoader.load(getClass().getResource("/View/AdminDashboard.fxml"));
        Scene sc= new Scene(p);
        Stage stage = new Stage();
        stage.setTitle("GHADS -DashBoard");
        stage.setScene(sc);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
      
    }
    private boolean checkValidData(Family fam, boolean validFam) {
        if (fam.getHouseholdName().isEmpty() || fam.getNationalId().isEmpty() || fam.getLocation().isEmpty() ||
            fam.getPhone().isEmpty() || fam.getFamilySize() <= 0 || fam.getVulnerabilityLevel() == null ||
            fam.getRegistrationDate() == null || fam.getLastAidDate() == null) {
            showAlert("ERROR","Wrong Data", "Please fill the fields");
            return false;
        }
        if (validFam) {
            if (famDao.SearchByNationalId(fam.getNationalId()) != null) {
                showAlert("ERROR", "Exist National ID", "Family with this National ID already exists!");
                return false;
            }
        }
        return true;
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
    
     private void selectedFamilyRow() {
        Family fam = familyTable.getSelectionModel().getSelectedItem();
        if (fam != null) {
            familyNameField.setText(fam.getHouseholdName());
            nationalIdField.setText(fam.getNationalId());
            locationField.setText(fam.getLocation());
            phoneField.setText(fam.getPhone());
            familySizeField.setText(String.valueOf(fam.getFamilySize()));
            vulnerabilityComb.setValue(fam.getVulnerabilityLevel());
          registrationDatePicker.setValue(famDao.findRegistrationDate(fam.getFamilyId()));
lastAidDatePicker.setValue(famDao.findLastAidDate(fam.getFamilyId()));


        }
    }
 @FXML
    private void handleExit(ActionEvent event) {
     ((Stage)  menuBar.getScene().getWindow()).close();

    }

    @FXML
    private void arialFontHandle(ActionEvent event) {  if (arialbtn.isSelected()) {
         menuBar.getScene().getRoot().setStyle("-fx-font-family: 'Arial';");
    }
    }

    @FXML
    private void SansSerifFontHandle(ActionEvent event) {
         if (SansSerifbtn.isSelected()) {
         menuBar.getScene().getRoot().setStyle("-fx-font-family: 'Sans Serif';");
    }  
    }

    @FXML
    private void TimeNewFontHandle(ActionEvent event) {
          if (timesnewbtn.isSelected()) {
         menuBar.getScene().getRoot().setStyle("-fx-font-family: 'Times New Roman';");
    }
    }

    @FXML
    private void smallFontHandle(ActionEvent event) {
          if(smallbtn.isSelected()){
         menuBar.getScene().getRoot().setStyle("-fx-font-size: 11px;");
          }
    }

    @FXML
    private void MediamFontHandle(ActionEvent event) {
          if (mediambtn.isSelected()) {
         menuBar.getScene().getRoot().setStyle("-fx-font-size: 15px;");
    }
    }

    @FXML
    private void LargeFontHandle(ActionEvent event) {
          if (largebtn.isSelected()) {
         menuBar.getScene().getRoot().setStyle("-fx-font-size: 17px;");
    }
    }

    @FXML
    private void LightBackgroud(ActionEvent event) {
           if (lightbtn.isSelected()) {
         menuBar.getScene().getRoot().setStyle("-fx-background-color: white;");
    }
    }

    @FXML
    private void darkBackgroud(ActionEvent event) {
          if (darkbtn.isSelected()) {
         menuBar.getScene().getRoot().setStyle("-fx-background-color: #2c2c2c; -fx-text-fill: white;");
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

   
}
