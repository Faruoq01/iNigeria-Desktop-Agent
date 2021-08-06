/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inigeria.desktop;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
public class LoginScreenController implements Initializable {

    @FXML
    private AnchorPane pane_1;
    @FXML
    private AnchorPane pane_2;
    @FXML
    private AnchorPane pane_3;
    @FXML
    private AnchorPane pane_4;
    @FXML
    private JFXTextField Email;
    @FXML
    private JFXPasswordField Password;
    @FXML
    private StackPane stackpane;
    @FXML
    private Pane LoginPane;
    @FXML
    private Pane signUpPane;
    @FXML
    private JFXTextField signUpName;
    @FXML
    private JFXTextField signupEmail;
    @FXML
    private JFXPasswordField signupPassword;
    @FXML
    private JFXPasswordField confirmPassword;
    @FXML
    private JFXButton minimize;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        pane_1.setStyle("-fx-background-image: url(/images/1.png)");
        pane_2.setStyle("-fx-background-image: url(/images/2.png)");
        pane_3.setStyle("-fx-background-image: url(/images/3.png)");
        pane_4.setStyle("-fx-background-image: url(/images/4.png)");
        
        addAnimation();
    }    

    private void addAnimation() {
        
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3),pane_4);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
       
        fadeTransition.setOnFinished(event ->{
           
            FadeTransition fade1 = new FadeTransition(Duration.seconds(3),pane_3);
            fade1.setFromValue(1);
            fade1.setToValue(0);
            fade1.play();
           
            fade1.setOnFinished(event1 ->{

                FadeTransition fade2 = new FadeTransition(Duration.seconds(3),pane_2);
                fade2.setFromValue(1);
                fade2.setToValue(0);
                fade2.play();
                                   
                fade2.setOnFinished(event3 ->{
                    
                        FadeTransition fade4 = new FadeTransition(Duration.seconds(3), pane_2);
                        fade4.setFromValue(0);
                        fade4.setToValue(1);
                        fade4.play();
                        
                        fade4.setOnFinished(event4 ->{
                    
                            FadeTransition fade5 = new FadeTransition(Duration.seconds(3), pane_3);
                            fade5.setFromValue(0);
                            fade5.setToValue(1);
                            fade5.play();
                            
                            fade5.setOnFinished(event5 ->{
                    
                                FadeTransition fade6 = new FadeTransition(Duration.seconds(3), pane_4);
                                fade6.setFromValue(0);
                                fade6.setToValue(1);
                                fade6.play();
                                
                                fade6.setOnFinished(event6 ->{
                    
                                    addAnimation();
                                
                                });
                                
                            });

                         });        

                     });    
                    
                });

            });
          
    }

    @FXML
    private void Exit(ActionEvent event) {
        
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("Close"));
        dialogLayout.setBody(new Text("Do you want to exit !"));
        
        JFXButton ok = new JFXButton("Ok");
        ok.setPrefHeight(30);
        ok.setPrefWidth(70);
        ok.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        ok.setButtonType(JFXButton.ButtonType.RAISED);
        
        JFXButton Cancel = new JFXButton("Cancel");
        Cancel.setPrefHeight(30);
        Cancel.setPrefWidth(70);
        Cancel.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        Cancel.setButtonType(JFXButton.ButtonType.RAISED);
        
        JFXDialog dialog = new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        
        ok.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent Event){
                System.exit(0);
            }
        });
        
        Cancel.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent Event){
                dialog.close();
            }
        });
        
        dialogLayout.setActions(ok,Cancel);
        dialog.show();
    }
    
    public static String name = null;
    public static String email = null;
    public static String token = null;

    @FXML
    private void Login(ActionEvent event) throws MalformedURLException, ProtocolException, ParseException, IOException {
        
        URL url = new URL("https://calmerc-inigeria.herokuapp.com/agents/login");
        Map<String, Object> params = new LinkedHashMap<>();
        
        if(Email.getText().equals("")){
            Image image = new Image("/images/delete.png");
                Notifications notification = Notifications.create()
                    .title("Error")
                    .text("Invalid email address")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_LEFT)
                    .graphic(new ImageView(image));
                notification.darkStyle();
                notification.show();
        }else if(Password.getText().equals("")){
            Image image = new Image("/images/delete.png");
                Notifications notification = Notifications.create()
                    .title("Error")
                    .text("Invalid password")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_LEFT)
                    .graphic(new ImageView(image));
                notification.darkStyle();
                notification.show();
        }else{
            
                params.put("email", Email.getText());
                params.put("password", Password.getText());
                
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
                    name = String.valueOf(data.get("name"));
                    email = String.valueOf(data.get("email"));
                    token = String.valueOf(data.get("token"));
                    
                        Stage home = new Stage();
                        Parent root= null;
                        
                        try {
                            root = FXMLLoader.load(getClass().getResource("/inigeria/desktop/HomeScreen.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Stage current = (Stage)pane_1.getScene().getWindow();
                        Scene scene = new Scene(root);

                        home.setScene(scene);
                        home.initStyle(StageStyle.TRANSPARENT);
                        home.setMaximized(true);
                        current.hide();
                        home.show();
                        
                        Image image = new Image("/images/mooo.png");
                        Notifications notification = Notifications.create()
                                .title("Success")
                                .text("Login successfully!")
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.BOTTOM_LEFT)
                                .graphic(new ImageView(image));
                        notification.darkStyle();
                        notification.show();
                    
                }else if(statusCode.equals("401")){
                    Image image = new Image("/images/delete.png");
                        Notifications notification = Notifications.create()
                                .title("Error")
                                .text("Wrong password")
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.BOTTOM_LEFT)
                                .graphic(new ImageView(image));
                        notification.darkStyle();
                        notification.show();
                }else if(statusCode.equals("422")){
                    Image image = new Image("/images/delete.png");
                        Notifications notification = Notifications.create()
                                .title("Error")
                                .text("Wrong email")
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.BOTTOM_LEFT)
                                .graphic(new ImageView(image));
                        notification.darkStyle();
                        notification.show();
                }
                
                /*Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                
                StringBuilder sb = new StringBuilder();
                
                JSONParser parser = new JSONParser();
                
                for(int c; (c = in.read()) >= 0;)
                    sb.append((char)c);
                
                String response = sb.toString();
                System.out.println(response);
                //JSONObject json = (JSONObject) parser.parse(response);
                //String code = String.valueOf(json.get("code"));
                
                //JSONObject data = (JSONObject)json.get("data");
                //String name = String.valueOf(data.get("name"));
                //String email = String.valueOf(data.get("email"));
                //String token = String.valueOf(data.get("token"));
                
                //System.out.println(code);
                //System.out.println(name);
                //System.out.println(email);
                //System.out.println(token);
                
                /*if(code.equals("202")){
                    if(Email.getText().equals(email) && !"".equals(token)){
                        Stage home = new Stage();
                        Parent root= null;
                        
                        try {
                            root = FXMLLoader.load(getClass().getResource("/inigeria/desktop/HomeScreen.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Stage current = (Stage)pane_1.getScene().getWindow();
                        Scene scene = new Scene(root);

                        home.setScene(scene);
                        home.initStyle(StageStyle.TRANSPARENT);
                        home.setMaximized(true);
                        current.hide();
                        home.show();
                        
                        Image image = new Image("/images/mooo.png");
                        Notifications notification = Notifications.create()
                                .title("Success")
                                .text("Login successfully!")
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.BOTTOM_LEFT)
                                .graphic(new ImageView(image));
                        notification.darkStyle();
                        notification.show();
                    }else{
                        Image image = new Image("/images/delete.png");
                        Notifications notification = Notifications.create()
                                .title("Error")
                                .text("Invalid credentials")
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.BOTTOM_LEFT)
                                .graphic(new ImageView(image));
                        notification.darkStyle();
                        notification.show();
                    }
                }else{
                    Image image = new Image("/images/delete.png");
                    Notifications notification = Notifications.create()
                            .title("Error")
                            .text("Invalid credentials")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.BOTTOM_LEFT)
                            .graphic(new ImageView(image));
                    notification.darkStyle();
                    notification.show();
                }*/
        }
    }

    @FXML
    private void Signup(ActionEvent event) {
         LoginPane.setVisible(false);
         signUpPane.setVisible(true);
                       
    }

    @FXML
    private void signupLogin(ActionEvent event) {
        signUpPane.setVisible(false);
        LoginPane.setVisible(true);
    }

    @FXML
    private void signupAction(ActionEvent event) throws MalformedURLException, IOException {
        
        if(signUpName.getText().equals("")){
            Image image = new Image("/images/delete.png");
            Notifications notification = Notifications.create()
                .title("Error")
                .text("name field cannot be empty")
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_LEFT)
                .graphic(new ImageView(image));
            notification.darkStyle();
            notification.show();
            
        }else if(signupEmail.getText().equals("")){
            
            Image image = new Image("/images/delete.png");
            Notifications notification = Notifications.create()
                .title("Error")
                .text("email field cannot be empty")
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_LEFT)
                .graphic(new ImageView(image));
            notification.darkStyle();
            notification.show();
            
        }else if(signupPassword.getText().equals("")){
            
            Image image = new Image("/images/delete.png");
            Notifications notification = Notifications.create()
                .title("Error")
                .text("password field cannot be empty")
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_LEFT)
                .graphic(new ImageView(image));
            notification.darkStyle();
            notification.show();
        }else if(confirmPassword.getText().equals("")){
            
            Image image = new Image("/images/delete.png");
            Notifications notification = Notifications.create()
                .title("Error")
                .text("confirm password field cannot be empty")
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_LEFT)
                .graphic(new ImageView(image));
            notification.darkStyle();
            notification.show();
        }else{
            
            URL url = new URL("https://calmerc-inigeria.herokuapp.com/agents/register");
            URLConnection connection = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) connection;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            Map<String, String> arguments = new HashMap<>();
            arguments.put("name", signUpName.getText());
            arguments.put("email", signupEmail.getText());
            arguments.put("password", signupPassword.getText());
            arguments.put("confirm_password", confirmPassword.getText());

            StringJoiner sj = new StringJoiner("&");

            for(Map.Entry<String, String> entry: arguments.entrySet()){

                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                + URLEncoder.encode(entry.getValue(), "UTF-8"));

            }

            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset = UTF-8");
            http.connect();

            try(OutputStream os = http.getOutputStream()){
                os.write(out);
                
            }
            int code = http.getResponseCode();
            System.out.println(code);
            
            if(code == 200 || code == 201 || code == 202){
                Image image = new Image("/images/mooo.png");
                Notifications notification = Notifications.create()
                    .title("Success")
                    .text("sign up successful")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_LEFT)
                    .graphic(new ImageView(image));
                notification.darkStyle();
                notification.show();
            }else{
                Image image = new Image("/images/delete.png");
                Notifications notification = Notifications.create()
                    .title("Error")
                    .text("signup was not successful, try again")
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
        Stage current = (Stage)signUpName.getScene().getWindow();
        current.setIconified(true);
    }
    
}
