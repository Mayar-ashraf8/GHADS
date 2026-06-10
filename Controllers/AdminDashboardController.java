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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AdminDashboardController implements Initializable {

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
    private Button numOrgLabel;
    @FXML
    private Button numCoorLabel;
    @FXML
    private Button numFamiliesLabel;
    @FXML
    private Button numServFamLabel;
    @FXML
    private Button numUNserFamLabel;
    @FXML
    private MenuBar menubar;
    organizationDAO orgDAO = new organizationDAO();
    UserDAO userDAO = new UserDAO();
    FamilyDAO familyDAO = new FamilyDAO();
    AidDistributionDAO adisDAO = new AidDistributionDAO();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    numOrgLabel.setText("🏢 Total Organizations: " + orgDAO.NumOfOrganizations());
    numCoorLabel.setText("👥 Total Coordinators: " + userDAO.NumOfCoordinators());
    numFamiliesLabel.setText("👪 Total Families: " + familyDAO.NumOfFamilies());
    numServFamLabel.setText("✅ Families Served: " + adisDAO.NumOfServedFamilies());
    numUNserFamLabel.setText("❗ Families Not Served: " +adisDAO.NumOfUnservedFamilies() );
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
    private void backbtn(ActionEvent event) throws IOException {
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
    private void OrganizationManagementbtn(ActionEvent event) throws IOException {
     NewScreen("/View/OrganizationManagement.fxml", "User Management","/Style/Organization Management _Style.css");
  
    }

    @FXML
    private void UserManagementBtn(ActionEvent event) throws IOException {
         NewScreen("/View/UserManagement.fxml", "User Management","/Style/UserManagment_Style.css");
    }

    @FXML
    private void FamilyManagementbtn(ActionEvent event) throws IOException {
       NewScreen("/View/FamilyManagement.fxml", "Family Management","/Style/FamilyManagment_Style.css");
    }

    @FXML
    private void DistributionManagementBtn(ActionEvent event) throws IOException {
       NewScreen("/View/AidDistributionManagement.fxml", "Aid Distribution Management","/Style/AidManagment_Style.css");
    }
    
    
    private void NewScreen(String fxmlPath, String title,String stylefile) throws IOException {
       Parent p = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene sc= new Scene(p);
      sc.getStylesheets().add(getClass().getResource(stylefile).toExternalForm());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(sc);
        stage.show();

  
}

    @FXML
    private void PassChangeHandle(ActionEvent event) throws IOException {
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
    
       private User currentUser;
       public void setCurrentUser(User user) {
       this.currentUser = user;
}
}
