/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.UserDAO;
import DAO.organizationDAO;
import Models.Organization;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
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
public class UserManagementController implements Initializable {

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
    private TextField fullNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TableView<User> userTable;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ComboBox<String> roleComb;
    @FXML
    private ComboBox<Organization> orgComb;
    @FXML
    private TableColumn<User,String> fullnameCol;
    @FXML
    private TableColumn<User,String> usernameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User,String> roleCol;
    @FXML
    private TableColumn<User,Organization> OrgCol;
    organizationDAO orgDao=new organizationDAO();
    UserDAO userDao=new UserDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        roleComb.getItems().addAll("Admin", "Coordinator");
        orgComb.setItems(FXCollections.observableArrayList(orgDao.ShowAllOrganizations()));
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        OrgCol.setCellValueFactory(new PropertyValueFactory<>("organization"));
      
         userTable.getSelectionModel().selectedItemProperty().addListener
        (event-> selectedUserRow());
        
        
         List<User> users = userDao.ShowAllUsers();
         userTable.getItems().setAll(users);
    }    

    @FXML
    private void adduserBtnHandle(ActionEvent event) {
        User newuser = new User();
        newuser.setFullName(fullNameField.getText());
        newuser.setUsername(usernameField.getText());
        newuser.setPassword(passwordField.getText());
        newuser.setEmail(emailField.getText());
        newuser.setRole(roleComb.getValue());
        newuser.setOrganization(orgComb.getValue());

        if (CheckValidData(newuser, true)){
            boolean validData = userDao.AddUser(newuser);
            if (validData) {
                ShowConfirmationAlert("Done", "New User", "User added successfully!");
         List<User> users = userDao.ShowAllUsers();
        userTable.getItems().setAll(users);
                ResetBtnHandle(null);
            } else {
                ShowAlert("ERROR", "Unvalid Data", "Failed to add user!");
            }
        }
    }
   
    @FXML
    private void edituserBtnHandle(ActionEvent event) {
       User chosenuser = userTable.getSelectionModel().getSelectedItem();
        if (chosenuser != null) {
            chosenuser.setFullName(fullNameField.getText());
            chosenuser.setUsername(usernameField.getText());
            chosenuser.setPassword(passwordField.getText());
            chosenuser.setEmail(emailField.getText());
            chosenuser.setRole(roleComb.getValue());
            chosenuser.setOrganization(orgComb.getValue());

            if (CheckValidData(chosenuser, false)) {
                boolean updateduser = userDao.UpdateUser(chosenuser);
                if (updateduser) {
                    ShowConfirmationAlert("New Data", "Update Done", "User updated successfully!");
                      List<User> users = userDao.ShowAllUsers();
                     userTable.getItems().setAll(users);
                    ResetBtnHandle(null);
                } else {
                    ShowAlert("ERROR", "Unvalid Operation", "Failed to update user!");
                }
            }
        } else {
            ShowAlert("Warning", "No Selection", "Please select a user to edit!");
        }  
    }
    

    @FXML
    private void deleteuserBtnHandle(ActionEvent event) {
        User deleteuser = userTable.getSelectionModel().getSelectedItem();
        if (deleteuser != null) {
            Alert confirmdelete = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO);
            confirmdelete.setTitle("Confirm Delete");
            confirmdelete.showAndWait();

            if (confirmdelete.getResult() == ButtonType.YES) {
                boolean deleted = userDao.DeleteUser(deleteuser.getUserId());
                if (deleted ) {
                    ShowConfirmationAlert("Done", "Delete User", "User deleted successfully!");
                   List<User> users = userDao.ShowAllUsers();
                     userTable.getItems().setAll(users);
                    ResetBtnHandle(null);
                } else {
                    ShowAlert("ERROR", "Not Accepted", "Failed to delete user!");
                }
            }
        } else {
            ShowAlert("ERROR", "No Selection", "Please select a user to delete!");
        }
    
    }

    @FXML
    private void ResetBtnHandle(ActionEvent event) {
     fullNameField.clear();
     usernameField.clear();
     passwordField.clear();
     emailField.clear();
     roleComb.getSelectionModel().clearSelection();
    orgComb.getSelectionModel().clearSelection();
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
        stage.setTitle("GHADS -GHADS -DashBoard");
        stage.setScene(sc);
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    //-------------------------------------
    private boolean CheckValidData(User user, boolean validuser) {
        if (user.getFullName().isEmpty() || user.getUsername().isEmpty() ||
            user.getPassword().isEmpty() || user.getEmail().isEmpty() ||
            user.getRole() == null || user.getOrganization() == null) {
            ShowAlert("ERROR","Wrong Datat", "please fill the fields");
            return false;
        }

        if (user.getPassword().length() < 8) {
            ShowAlert("ERROR", "Unvalid Password", "Password must be at least 8 characters!");
            return false;
        }
        if(validuser) {
            if (userDao.searchByUsername(user.getUsername()) != null) {
                ShowAlert("ERROR", "Exist username", "Username already exists");
                return false;
            }
            // Email uniqueness check (using Streams)
           List<User> existemail = userDao.ShowAllUsers().stream()
        .filter(u -> u.getEmail().equalsIgnoreCase(user.getEmail()))
        .collect(Collectors.toList());

           if (!existemail.isEmpty()) {
         ShowAlert("ERROR", "Exist Email", "Email already exists");
           return false;
}
            }
        
        return true;
    }
     public void ShowAlert(String Title,String Header,String content){
     Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(content);
        alert.showAndWait(); 
    }
      private boolean ShowConfirmationAlert(String Title,String Header,String content){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
         alert.setHeaderText(Header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
       if(result.isPresent() && result.get() == ButtonType.OK){
           return true;
       }
        return false;
    
}
      private void selectedUserRow() {
    User user = userTable.getSelectionModel().getSelectedItem();
    if (user != null) {
        fullNameField.setText(user.getFullName());
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        emailField.setText(user.getEmail());
        roleComb.setValue(user.getRole());
        orgComb.setValue(user.getOrganization());
    }
}
}