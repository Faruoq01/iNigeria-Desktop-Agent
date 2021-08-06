/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inigeria.desktop;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author UMAR-FARUK
 */
public class HomeScreenController implements Initializable {

    private AnchorPane pane_1;
    @FXML
    private Line lineOne;
    @FXML
    private Line lineTwo;
    @FXML
    private Line lineThree;
    @FXML
    private AnchorPane WebviewPane;
    private AnchorPane SearchPane;
    @FXML
    private JFXTextField mobileNumber;
    @FXML
    private JFXComboBox<String> category;
    @FXML
    private WebView webView;
    @FXML
    private ProgressBar webProgress;
    
    WebEngine webEngine;
    @FXML
    private Label userName;
    @FXML
    private Label Phone;
    @FXML
    private Label address;
    @FXML
    private Label state;
    @FXML
    private Label dob;
    @FXML
    private Label uniqueId;
    @FXML
    private JFXButton minimize;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        category.getItems().addAll("calmerc", "NIN", "License", "Passport");
        
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
    }    

    @FXML
    private void goBack(ActionEvent event) {
        
        Stage home = new Stage();
        Parent root= null;

                try {
                    root = FXMLLoader.load(getClass().getResource("/inigeria/desktop/LoginScreen.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Stage current = (Stage)lineOne.getScene().getWindow();
                Scene scene = new Scene(root);

                home.setScene(scene);
                home.initStyle(StageStyle.TRANSPARENT);
                home.setMaximized(true);
                current.hide();
                home.show();
    }

    @FXML
    private void exitSetting(MouseEvent event) {
        lineOne.setVisible(true);
        lineTwo.setVisible(false);
        lineThree.setVisible(false);
    }

    @FXML
    private void enteredSettings(MouseEvent event) {
        lineOne.setVisible(true);
        lineTwo.setVisible(false);
        lineThree.setVisible(false);
    }

    @FXML
    private void exitUser(MouseEvent event) {
        lineOne.setVisible(false);
        lineTwo.setVisible(true);
        lineThree.setVisible(false);
    }

    @FXML
    private void enteredUser(MouseEvent event) {
        
        lineTwo.setVisible(true);
        lineOne.setVisible(false);
        lineThree.setVisible(false);
    }

    @FXML
    private void exitKeyboard(MouseEvent event) {
        lineOne.setVisible(false);
        lineTwo.setVisible(false);
        lineThree.setVisible(true);
    }

    @FXML
    private void enteredKyboard(MouseEvent event) {
        
        lineThree.setVisible(true);
        lineOne.setVisible(false);
        lineTwo.setVisible(false);
    }

    @FXML
    private void GoToNIN(MouseEvent event) {
        WebviewPane.setVisible(true);
    }

    private void GoToSearch(MouseEvent event) {
        SearchPane.setVisible(true);
    }

    @FXML
    private void BackFromWebview(ActionEvent event) {
        WebviewPane.setVisible(false);
    }

    private void BackFromSearch(ActionEvent event) {
        SearchPane.setVisible(false);
    }
    
    String token = LoginScreenController.token;

    @FXML
    private void searchUser(ActionEvent event) throws UnsupportedEncodingException, IOException, ParseException {
        
        webProgress.setVisible(true);
        
        URL url = new URL("https://calmerc-inigeria.herokuapp.com/agents/user/details");
        Map<String, Object> params = new LinkedHashMap<>();
        
        if(mobileNumber.getText().equals("")){
            Image image = new Image("/images/delete.png");
                Notifications notification = Notifications.create()
                    .title("Error")
                    .text("Invalid mobile number")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_LEFT)
                    .graphic(new ImageView(image));
                notification.darkStyle();
                notification.show();
        }else if(!"calmerc".equals(category.getSelectionModel().getSelectedItem())){
            Image image = new Image("/images/delete.png");
                Notifications notification = Notifications.create()
                    .title("Error")
                    .text("invalid category")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_LEFT)
                    .graphic(new ImageView(image));
                notification.darkStyle();
                notification.show();
        }else{
            params.put("mobile_number", mobileNumber.getText());
            params.put("service", category.getSelectionModel().getSelectedItem());
                
            StringBuilder postData = new StringBuilder();
            for(Map.Entry<String, Object> param: params.entrySet()){
                   
                if(postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                   
            }
                
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);
            connection.getOutputStream().write(postDataBytes);
                
            String statusCode = String.valueOf(connection.getResponseCode());
            
            if(statusCode.equals("202")){
                Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                
                StringBuilder sb = new StringBuilder();
                
                JSONParser parser = new JSONParser();
                
                for(int c; (c = in.read()) >= 0;)
                    sb.append((char)c);
                
                String response = sb.toString();
                JSONObject json = (JSONObject) parser.parse(response);
                JSONObject data = (JSONObject)json.get("data");
                
                JSONObject user = (JSONObject)data.get("user");
                JSONObject personalInfo = (JSONObject)user.get("personalInfo");
                JSONObject contactInfo = (JSONObject)user.get("contactInfo");
                
                String phone_number = String.valueOf(user.get("mobile_number"));
                String nameOfUser = String.valueOf(personalInfo.get("firstName")+" "+personalInfo.get("lastName"));
                String add = String.valueOf(contactInfo.get("addressOfResidence"));
                
                userName.setText(nameOfUser);
                Phone.setText(phone_number);
                address.setText(add);
                state.setText(String.valueOf(contactInfo.get("stateOfOrigin")));
                dob.setText(String.valueOf(personalInfo.get("dateOfBirth")));
                uniqueId.setText(String.valueOf(contactInfo.get("_id")));
                
                JSONObject dom = (JSONObject)data.get("dom");
                
                String domUrl = String.valueOf(dom.get("url"));
                webEngine.loadContent(domUrl);
                
                webProgress.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
                
                webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>(){
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                        switch(newValue){
                            case RUNNING:
                                System.out.println("running");
                                break;
                            case SUCCEEDED:
                                webProgress.setVisible(false);
                                Image image = new Image("/images/mooo.png");
                                Notifications notification = Notifications.create()
                                    .title("Success")
                                    .text("Page loaded successfully!")
                                    .hideAfter(Duration.seconds(3))
                                    .position(Pos.BOTTOM_LEFT)
                                    .graphic(new ImageView(image));
                                notification.darkStyle();
                                notification.show();
                                break;
                        }
                    }
                });
                
                mobileNumber.setText("");
                category.setValue("");
                
            }else{
                Image image = new Image("/images/delete.png");
                Notifications notification = Notifications.create()
                    .title("Error")
                    .text("Server error, check details again")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_LEFT)
                    .graphic(new ImageView(image));
                notification.darkStyle();
                notification.show();
            }
        }
    }
    
    @FXML
    void minimize(ActionEvent event) {
        Stage current = (Stage)mobileNumber.getScene().getWindow();
        current.setIconified(true);
    }
    
}
