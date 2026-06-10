/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.UserDAO;
import Models.Organization;
import Models.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author hp
 */
public class LoginController implements Initializable {
    @FXML
    private TextField usernameFeild;
    @FXML
    private PasswordField passFeild;
    @FXML
    private Label messageLabel;
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
        UserDAO userdao = new UserDAO();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleLogin_btn(ActionEvent event) throws IOException {
        String username = usernameFeild.getText();
        String password = passFeild.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please Fill the Fields");
            return;
        }
        User user=userdao.checkPassandUsername(username, password);
         if (user != null) {
    messageLabel.setText("Welcome " + user.getFullName() + " (" + user.getRole() + ")");
    //-------------------------------------
     currentUser = userdao.getUserById(user.getUserId());    
        currentOrg = currentUser.getOrganization();           
     //-------------------------------------------------
    switch (user.getRole()) {
        case "Admin":
           AdminDashboard(user);
            break;
        case "Coordinator":
            CoordinatorDashboard(user);
            break;
        default:
            messageLabel.setText("invalid data");
    }
} else {
    messageLabel.setText("Invalid username or password!");
}

    }
    @FXML
    private void handleResetbtn(ActionEvent event) {
         usernameFeild.clear();
        passFeild.clear();
        messageLabel.setText("");
    }

    private void AdminDashboard(User user) {
         try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminDashboard.fxml"));
        Parent root = loader.load();
        //to pass the user
        AdminDashboardController controller = loader.getController();
        controller.setCurrentUser(user); 
        Scene sc= new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("GHADS - Admin Dashboard");
        stage.setScene(sc);
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    private void CoordinatorDashboard(User user) throws IOException {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CoordinatorDashboard.fxml"));
        Parent root = loader.load();
        //to pass the user
       CoordinatorDashboardController controller = loader.getController();
        controller.setCurrentUser(user); 
        Scene sc= new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("GHADS -Coordinator Dashboard ");
        stage.setScene(sc);
        stage.show();   
               
    }
    @FXML
    private void handleExit(ActionEvent event) {
     ((Stage) menuBar.getScene().getWindow()).close();

    }

    @FXML
    private void arialFontHandle(ActionEvent event) {
          if (arialbtn.isSelected()) {
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
     private User currentUser;
       public void setCurrentUser(User user) {
       this.currentUser = user;
}
        private Organization currentOrg;;
       public void setCurrentOrg(Organization org) {
    this.currentOrg = org;
}
   

    }


    
    

