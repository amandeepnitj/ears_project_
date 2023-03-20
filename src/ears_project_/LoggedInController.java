/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LoggedInController implements Initializable {
    @FXML
    private Button login_btn,signup_btn,applicant_btn;
    @FXML
    private TextField username_tf,password_tf;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                String name=username_tf.getText().trim();
                String pass = password_tf.getText().trim();
                if(name.isEmpty()==false&&pass.isEmpty()==false)
                {
                    try {
                        boolean p=  DBUtils.logInUser(event, name, pass);
//                        username_tf.clear();
//                        password_tf.clear();
                        if(p==false)
                        {
                            Alert alert =new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Can't find this credentials in DB");
                            alert.show();
                            
                            
                        }
                        else
                        {
                            DBUtils.changeScene(event, "Home.fxml", "Home", name);
                            
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LoggedInController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(LoggedInController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    System.out.println("Please fill all the Fields");
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the info first");
                    alert.show();
                }
            
               
            }
            
        });
        
        signup_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sign-up.fxml", "Sign Up", null);
            }
            
        });
        
        //applicant button reflect new window
        applicant_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            
                DBUtils.changeScene(event, "applicantprofile.fxml", "Applicant Profile", null);
            }
        });
        
        
        
    }
    
}
