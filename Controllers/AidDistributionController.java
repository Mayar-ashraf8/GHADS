/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.AidDistributionDAO;
import DAO.organizationDAO;
import Models.AidDistribution;
import Models.Family;
import Models.Organization;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class AidDistributionController implements Initializable {

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
    private ComboBox<Organization> orgComb;
    @FXML
    private TableView<AidDistribution> AidTable;
    @FXML
    private TableColumn<AidDistribution,Integer> DistributionIdCol;
    @FXML
    private TableColumn<AidDistribution,Date> DistDateCol;
    @FXML
    private TableColumn<AidDistribution,Integer> famIdCol;
    @FXML
    private TableColumn<AidDistribution,Integer> OrgIdCol;
    @FXML
    private TableColumn<AidDistribution,Integer> DistributedByCol;
    private AidDistributionDAO aidDao = new AidDistributionDAO();
    organizationDAO orgDao=new organizationDAO();
        List<Organization> org = orgDao.ShowAllOrganizations();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       orgComb.getItems().addAll(org);
      DistributionIdCol.setCellValueFactory(new PropertyValueFactory<>("distributionId"));
        DistDateCol.setCellValueFactory(new PropertyValueFactory<>("distributionDate"));
       famIdCol.setCellValueFactory(new PropertyValueFactory<>("family"));
       OrgIdCol.setCellValueFactory(new PropertyValueFactory<>("organization"));
       DistributedByCol.setCellValueFactory(new PropertyValueFactory<>("distributedBy"));

         List<AidDistribution> Aids = aidDao.ShowAidDistributions();
         AidTable.getItems().setAll(Aids);
     
    }

    @FXML
    private void searchBtnHandle(ActionEvent event) {
        Organization Org = orgComb.getValue();
        if (Org != null) {
            List<AidDistribution> selectOrg = aidDao.searchByOrganization(Org.getOrgId());
            AidTable.setItems(FXCollections.observableArrayList(selectOrg));
        }
    }

    @FXML
    private void showBtnHandle(ActionEvent event) {
         List<AidDistribution> Aids = aidDao.ShowAidDistributions();
         AidTable.getItems().setAll(Aids);
    
    } 
    @FXML
    private void handleExit(ActionEvent event) {
     ((Stage) menuBar.getScene().getWindow()).close();

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

  
}

  
    

