/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ears_project_;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Aman
 */
public class ApplicantprofileController implements Initializable {

    private int department_id=1,designation_id=3;
    @FXML
    private TextField username_tf,email_tf,contact_tf;
    
    @FXML
    private TextArea description_ta;
    
    @FXML
    private ComboBox designation_cb,department_cb;
    
    @FXML 
    private Button cancel_btn,submit_btn;
    
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
        //here I used +3 because first 2 designation will not available for applicants
        designation_id =designation_cb.getSelectionModel().getSelectedIndex()+3;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // this is for dpt list
        ObservableList<String> dpt_list =FXCollections.observableArrayList("CSE","ECE","Mechanical","Civil","Chemical","Biotechnology");
        department_cb.setItems(dpt_list);
        //this is for department list
        ObservableList<String> dst_list =FXCollections.observableArrayList("Senior Lecturer","Associate Professor","PhD Scholar");
        designation_cb.setItems(dst_list);
        
        //submit button action
        submit_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            
                String name=username_tf.getText().trim();
                String email = email_tf.getText().trim();
                String contact = contact_tf.getText().trim();
                String description = description_ta.getText().trim();
                
                if(name.isEmpty()==false&&email.isEmpty()==false&&contact.isEmpty()==false)
                {
                    try {
                        boolean p = DBUtils.Applicantuser(event, name, email,contact,description,designation_id,department_id);
                        if(p==false)
                        {
                            System.out.println("Email already exists, Please use another one");
                            Alert alert =new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Email already exists, Please use another one");
                            alert.show();
                        }
                        else
                        {
                            System.out.println("Submission Completed, All the best");
                            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("Submission Completed, All the best");
                            alert.show();
                            
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ApplicantprofileController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ApplicantprofileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                else
                {
                    
                    System.out.println("Please fill all the information first");
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill all the info first");
                    alert.show();
                }
            }
        });
        
        
        
        
        //cancel btn render login screen
        cancel_btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
            
                DBUtils.changeScene(event, "logged-in.fxml", "Log In", null);
            }
        });
        
        
       }    
    
}
