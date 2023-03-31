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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Aman
 */
public class SignUpController implements Initializable {
    int designation_id =1;
    int department_id =1;

    @FXML
    private Button signup_btn,login_btn,applicant_btn;
    
    @FXML
    private TextField username_tf,contact_tf;
    @FXML
    private PasswordField password_tf;
    @FXML
    private ComboBox<String> designation_cb;
    @FXML
    private ComboBox department_cb ;
    @FXML
    void Select_department(ActionEvent event)
    {
        System.out.println(department_cb.getSelectionModel().getSelectedIndex());
        department_id =department_cb.getSelectionModel().getSelectedIndex()+1;
    }
    
    @FXML
    void Select_designation(ActionEvent event)
    {
        System.out.println(designation_cb.getSelectionModel().getSelectedIndex());
        designation_id =designation_cb.getSelectionModel().getSelectedIndex()+1;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // this is for dpt list
        ObservableList<String> dpt_list =FXCollections.observableArrayList("CSE","ECE","Mechanical","Civil","Chemical","Biotechnology");
        department_cb.setItems(dpt_list);
        //this is for department list
        ObservableList<String> dst_list =FXCollections.observableArrayList("Dean","HOD","Senior Lecturer","Associate Professor","PhD Scholar");
        designation_cb.setItems(dst_list);
        signup_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            
                String name=username_tf.getText().trim();
                String pass = password_tf.getText().trim();
                String contact = contact_tf.getText().trim();
                System.out.println(name+" and " + pass +" and contact = "+contact);
                System.out.println("name is empty == "+name.isEmpty());
                
                if(name.isEmpty()==false&&pass.isEmpty()==false&&contact.isEmpty()==false)
                {
                    try {
                        boolean p = DBUtils.signUpUser(event, name, pass,contact,designation_id,department_id);
                        username_tf.clear();
                        password_tf.clear();
                        contact_tf.clear();
                        if(p==false)
                        {
                            Alert alert =new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Username is already taken");
                            alert.show();
                            
                        }
                        else
                        {
                            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("User profile has been created");
                            alert.show();
                            DBUtils.changeScene(event, "logged-in.fxml", "Log In", null);
                            
                            
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                    }                 
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
        
        //applicant button reflect new window
        applicant_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            
                DBUtils.changeScene(event, "applicantprofile.fxml", "Applicant Profile", null);
            }
        });
    }
    
     
    
}
