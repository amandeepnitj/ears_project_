/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Aman
 */
public class SignUpController implements Initializable {

    @FXML
    private Button signup_btn,login_btn,applicant_btn;
    
    @FXML
    private TextField username_tf,password_tf;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        signup_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            
                String name=username_tf.getText().trim();
                String pass = password_tf.getText().trim();
                if(!name.isEmpty()&&!pass.isEmpty())
                {
                    DBUtils.signUpUser(event, name, pass);
                }
                else
                {
                    System.out.println("Please fill all the TF");
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the info first");
                    alert.show();
                }
            }
        });
        
        //login button reflect new window
        login_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            
                DBUtils.changeScene(event, "logged-in.fxml", "Log In", null);
            }
        });
    }
    
}
