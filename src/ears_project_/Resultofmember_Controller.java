/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ears_project_;

import Model.ResultOfMemberModel;
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
public class Resultofmember_Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Label username_third_lb;

    @FXML
    private Label decision_third_lb;

    @FXML
    private Label description_third_lb;
    
    public void setData(ResultOfMemberModel rmm)
    {
        username_third_lb.setText(rmm.getUsername());
        decision_third_lb.setText(rmm.getFeedback_code());
        description_third_lb.setText(rmm.getFeedback_description());
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
