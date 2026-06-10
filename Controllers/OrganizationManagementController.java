/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.organizationDAO;
import Models.Organization;
import java.net.URL;
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
public class OrganizationManagementController implements Initializable {

    @FXML
    private TextField orgNameField;
    @FXML
    private TextField orgTypeField;
    @FXML
    private TextField orgContactField;
    @FXML
    private TableView<Organization> orgTable;
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
    private TableColumn<Organization,String> nameCol;
    @FXML
    private TableColumn<Organization, String> typeCol;
    @FXML
    private TableColumn<Organization,Integer> contactCol;
    organizationDAO orgDao=new organizationDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

        orgTable.getSelectionModel().selectedItemProperty().
                addListener(event -> selectedOrgRow());

        List<Organization> orgs = orgDao.ShowAllOrganizations();
        orgTable.getItems().setAll(orgs);
    }
       
 @FXML
    private void addOrgHandle(ActionEvent event) {
        Organization org = new Organization();
        org.setName(orgNameField.getText());
        org.setType(orgTypeField.getText());
        org.setContactInfo(orgContactField.getText());

        if (checkValidData(org, true)) {
            boolean added = orgDao.AddOrganization(org);
            if (added) {
                showConfirmationAlert("Done", "New Organization", "Organization added successfully!");
                List<Organization> orgs = orgDao.ShowAllOrganizations();
                orgTable.getItems().setAll(orgs);
                resetBtnHandle(null);
            } else {
                showAlert("ERROR", "Failed", "Could not add organization!");
            }
        }
    }

    @FXML
    private void editOrgHandle(ActionEvent event) {
        Organization org = orgTable.getSelectionModel().getSelectedItem();
        if (org != null) {
            org.setName(orgNameField.getText());
            org.setType(orgTypeField.getText());
            org.setContactInfo(orgContactField.getText());

            if (checkValidData(org, false)) {
                boolean updated = orgDao.UpdateOrganization(org);
                if (updated) {
                    showConfirmationAlert("Update", "Update Done", "Organization updated successfully!");
                    List<Organization> orgs = orgDao.ShowAllOrganizations();
                    orgTable.getItems().setAll(orgs);
                    resetBtnHandle(null);
                } else {
                    showAlert("ERROR", "Failed", "Could not update organization!");
                }
            }
        } else {
            showAlert("Warning", "No Selection", "Please select an organization to edit!");
        }
    }

    @FXML
    private void deleteOrgHandle(ActionEvent event) {
        Organization org = orgTable.getSelectionModel().getSelectedItem();
        if (org != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this organization?", ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm Delete");
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                boolean deleted = orgDao.DeleteOrganization(org.getOrgId());
                if (deleted) {
                    showConfirmationAlert("Done", "Delete Organization", "Organization deleted successfully!");
                    List<Organization> orgs = orgDao.ShowAllOrganizations();
                    orgTable.getItems().setAll(orgs);
                    resetBtnHandle(null);
                } else {
                    showAlert("ERROR", "Failed", "Could not delete organization!");
                }
            }
        } else {
            showAlert("ERROR", "No Selection", "Please select an organization to delete!");
        }
    }

    @FXML
    private void resetBtnHandle(ActionEvent event) {
        orgNameField.clear();
        orgTypeField.clear();
        orgContactField.clear();
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
    // Validation
    private boolean checkValidData(Organization org, boolean validOrg) {
        if (org.getName().isEmpty() || org.getType().isEmpty() || org.getContactInfo().isEmpty()) {
            showAlert("ERROR","Wrong Data", "Please fill the fields");
            return false;
        }
        if (validOrg) {
            if (orgDao.searchOrgName(org.getName()) != null) {
                showAlert("ERROR", "Exist Name", "Organization name already exists");
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
    private void selectedOrgRow() {
        Organization org = orgTable.getSelectionModel().getSelectedItem();
        if (org != null) {
            orgNameField.setText(org.getName());
            orgTypeField.setText(org.getType());
            orgContactField.setText(org.getContactInfo());
        
}
    }
}