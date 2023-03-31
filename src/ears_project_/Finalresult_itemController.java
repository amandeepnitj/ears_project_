/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ears_project_;

import Model.ApplicantModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author amandeepsingh12
 */
public class Finalresult_itemController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private Label username;

    @FXML
    private Label email;

    @FXML
    private Label contact;

    @FXML
    private Label position;

    @FXML
    private Label status;
    
    public void setdata(ApplicantModel ap)
    {
        username.setText(ap.getUsername());
        email.setText(ap.getEmail());
        contact.setText(ap.getContact());
        position.setText(ap.getPosition());
        status.setText(ap.getStatus());
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
