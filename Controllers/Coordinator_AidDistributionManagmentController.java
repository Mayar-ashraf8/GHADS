/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.AidDistributionDAO;
import DAO.FamilyDAO;
import DAO.UserDAO;
import DAO.organizationDAO;
import Models.AidDistribution;
import Models.Family;
import Models.Organization;
import Models.User;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
public class Coordinator_AidDistributionManagmentController implements Initializable {

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
    private TableView<Family> familyTable;
    @FXML
    private TextField familyIdField;
    @FXML
    private TextField distributedByField;
    @FXML
    private TableColumn<Family,Integer> FamilyIdCol;
    @FXML
    private TableColumn<Family, Integer> FamNameCol;
    @FXML
    private TableColumn<Family,String> VulnerabilityCol;
    private TableColumn<Family,String> OrgCol;
    @FXML
    private TableColumn<Family,LocalDate> LastAidCol;
    @FXML
    private TableColumn<Family,LocalDate> RegistrationCol;
    @FXML
    private TextField OgField;
    @FXML
    private DatePicker distributionDateField;
    
    FamilyDAO famDao = new FamilyDAO();
    AidDistributionDAO aidDao = new AidDistributionDAO();
    organizationDAO orgDao=new organizationDAO();
    UserDAO userDao=new UserDAO();
    @FXML
    private MenuBar menubar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        FamilyIdCol.setCellValueFactory(new PropertyValueFactory<>("familyId"));
        FamNameCol.setCellValueFactory(new PropertyValueFactory<>("householdName")); 
        VulnerabilityCol.setCellValueFactory(new PropertyValueFactory<>("vulnerabilityLevel"));
        LastAidCol.setCellValueFactory(new PropertyValueFactory<>("lastAidDate"));
        RegistrationCol.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
    
    }  
     @FXML
    private void searchVulnerableFamHandle(ActionEvent event) {
        List<Family> vulnerableFamilies = famDao.getFamiliesByVulnerability("HIGH");
        familyTable.setItems(FXCollections.observableArrayList(vulnerableFamilies));
    
    }
    


    @FXML
    private void searchUnservedFamHandle(ActionEvent event) {
        List<Family> unservedFamilies = famDao.getUnservedFamilies();
    familyTable.setItems(FXCollections.observableArrayList(unservedFamilies));

    }

    @FXML
    private void saveDistributionBtnHandle(ActionEvent event) {
        
    String familyIdText = familyIdField.getText().trim();
    LocalDate selectedDate = distributionDateField.getValue();

    if (familyIdText.isEmpty() || selectedDate == null) {
        showAlert("Validation Error", "Missing Fields", "Please fill all fields before saving.");
        return;
    }
    int familyId;
    try {
        familyId = Integer.parseInt(familyIdText);
    } catch (NumberFormatException e) {
        showAlert("Validation Error", "Invalid ID", "Family ID must be a number.");
        return;
    }

    Family selectedFamily = famDao.getFamilyById(familyId);
    if (selectedFamily == null) {
        showAlert("Not Found", "Family Not Found", "No family found with ID: " + familyId);
        return;
    }
    String vulnerability = selectedFamily.getVulnerabilityLevel();
if (vulnerability.equalsIgnoreCase("MEDIUM") || vulnerability.equalsIgnoreCase("LOW")) {
    AidDistribution lastDistribution = aidDao.getLastDistributionForFamily(selectedFamily.getFamilyId());
    if (lastDistribution != null && lastDistribution.getDistributionDate() != null) {
        LocalDate lastAidLocalDate = lastDistribution.getDistributionDate().toLocalDate();
        LocalDate thirtyDays = selectedDate.minusDays(30);

        if (!lastAidLocalDate.isBefore(thirtyDays)) {
            showAlert(
                "Duplicate Distribution Rejected",
                "This family already received aid recently!",
                "Family Name: " + selectedFamily.getHouseholdName() + "\n"
                + "Vulnerability Level: " + selectedFamily.getVulnerabilityLevel() + "\n"
                + "Organization that gave aid: " + lastDistribution.getOrganization().getName() + "\n"
                + "Date aid was given: " + lastAidLocalDate.toString()
            );
            return;
        }
    }
}
    try {
        AidDistribution newDistribution = new AidDistribution();
        newDistribution.setFamily(selectedFamily);
        newDistribution.setOrganization(currentOrg);
        newDistribution.setDistributedBy(currentUser);
        newDistribution.setDistributionDate(Date.valueOf(selectedDate));

        boolean saved = aidDao.save(newDistribution);
        if (!saved) {
       showAlert("Error", "Save Failed", "Could not save distribution.");
         return;
}
        selectedFamily.setLastAidDate(Date.valueOf(selectedDate));
        famDao.UpdateFamily(selectedFamily);

        showAlert("Success", "Done!", "Aid distribution saved successfully.");

        familyTable.refresh();

    } catch (Exception e) {
        showAlert("Error", "Save Failed", "Error: " + e.getMessage());
    }
     
}



private void showAlert(String title, String msg) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
}
@FXML
   private void handleExit(ActionEvent event) {
     ((Stage) menubar.getScene().getWindow()).close();

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
    
 private User currentUser;
       public void setCurrentUser(User user) {
       this.currentUser = user;
}
      private Organization currentOrg;;
       public void setCurrentOrg(Organization org) {
    this.currentOrg = org;
}

    @FXML
    private void BackBtnHandle(ActionEvent event) {
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
   
    
}
