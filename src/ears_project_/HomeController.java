/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ears_project_;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Aman
 */
public class HomeController implements Initializable {

    @FXML
    private Button submit_first_btn,cancel_first_btn,submit_four_btn,logout_four_btn;
    
    @FXML
    private TextField committee_first_tf,username_four_tf,password_four_tf,contact_four_tf;
    
    @FXML 
    private ComboBox designation_first_cb,chairperson_first_cb,department_four_cb,designation_four_cb;
    
    @FXML
    private TabPane tabpane_tp;
    
    @FXML
    private Tab tab1,tab2,tab3,tab4;
    
    @FXML
    void tabSelected_1()
    {
        System.out.println(1);
    }
    
    
    @FXML
    void tabSelected_2()
    {
        System.out.println(2);
    }
    
    @FXML
    void tabSelected_3()
    {
        System.out.println(3);
    }
    
    @FXML
    void tabSelected_4()
    {
        System.out.println(4);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        System.out.println(tabpane_tp.getSelectionModel().getSelectedItem());
        // TODO
    }    
    
}
