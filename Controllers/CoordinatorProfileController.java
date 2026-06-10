/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.UserDAO;
import Models.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class CoordinatorProfileController implements Initializable {

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
    private TextField nameField;
    @FXML
    private TextField fullnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField orgField;
    @FXML
    private TextField roleField;
    User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    public void setCurrentUser(User user) {
        this.currentUser = user;
      
        if (currentUser != null) {
            nameField.setText(currentUser.getUsername());
            fullnameField.setText(currentUser.getFullName());
            emailField.setText(currentUser.getEmail());
            orgField.setText(currentUser.getOrganization().getName());
            roleField.setText(currentUser.getRole());
        }
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
    

    @FXML
    private void PassChangeHandle(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ChangePassword.fxml"));
        Parent root = loader.load();

        ChangePasswordController controller = loader.getController();
        //pass the current User
        controller.setCurrentUser(currentUser); 
          Scene sc= new Scene(root);
      sc.getStylesheets().add(getClass().getResource("/Style/ChangePassword_Style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("Change Password");
        stage.setScene(sc);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void logoutHandle(ActionEvent event) {
         try {
         Parent p = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        Scene sc= new Scene(p);
       sc.getStylesheets().add(getClass().getResource("/Style/login_Style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle("GHADS - Login");
        stage.setScene(sc);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void editProBtnHandle(ActionEvent event) {
    if (nameField.getText().isEmpty() ||
        fullnameField.getText().isEmpty() ||
        emailField.getText().isEmpty() ||
        orgField.getText().isEmpty() ||
        roleField.getText().isEmpty()) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all fields before saving!");
        alert.showAndWait();
        return; 
    }

    try {
        currentUser.setUsername(nameField.getText());
        currentUser.setFullName(fullnameField.getText());
        currentUser.setEmail(emailField.getText());

        UserDAO userDao = new UserDAO();
        userDao.UpdateUser(currentUser);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profile Saved");
        alert.setHeaderText(null);
        alert.setContentText("Profile updated successfully!");
        alert.showAndWait();

        nameField.setEditable(true);
        fullnameField.setEditable(true);
        emailField.setEditable(true);

    } catch (Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Save Failed");
        alert.setContentText("Could not save profile changes.");
        alert.showAndWait();
    }
    }

    @FXML
    private void resetProBtnHandle(ActionEvent event) {
      nameField.clear();
    fullnameField.clear();
     emailField.clear();
    
        
    }

    @FXML
    private void BackBtnHandle(ActionEvent event) {
         try {
         Parent p = FXMLLoader.load(getClass().getResource("/View/CoordinatorDashboard.fxml"));
        Scene sc= new Scene(p);
        Stage stage = new Stage();
        stage.setTitle("GHADS - Login");
        stage.setScene(sc);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }
    
}
