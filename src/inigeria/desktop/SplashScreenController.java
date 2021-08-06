/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inigeria.desktop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author UMAR-FARUK
 */
public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(5000), pane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(1.0);
        
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                
                Stage homeScreen = new Stage();
                Parent root = null;
                
                try{
                    root = FXMLLoader.load(getClass().getResource("/inigeria/desktop/LoginScreen.fxml"));
                    
                } catch (Exception e){
                    e.printStackTrace();
                }
            
                Stage current = (Stage)pane.getScene().getWindow();
                Scene scene = new Scene(root);
                
                homeScreen.setScene(scene);
                homeScreen.initStyle(StageStyle.TRANSPARENT);
                homeScreen.setMaximized(true);
                current.hide();
                homeScreen.show();
            }

        });
        fadeTransition.play();
    }    
    
}
