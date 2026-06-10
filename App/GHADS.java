/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Config.JPAUtil;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class GHADS extends Application{
    public static void main(String[] args) {
        
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent p1 = FXMLLoader.load(getClass().getResource("/View/login.fxml"));

        
        Scene sc = new Scene(p1);
        stage.setScene(sc);
         sc.getStylesheets().add(getClass().getResource("/Style/login_Style.css").toExternalForm());
        stage.setTitle("GHADS Program");
        stage.show();
    }
    
    
    @Override
    public void stop()  {
           JPAUtil.CloseEMF();
    }

    
}
    
