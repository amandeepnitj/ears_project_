/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

import Model.CreateSearchModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author amandeepsingh12
 */
public class CreateSearchController implements Initializable{
    
    @FXML
    private Label id_lb;

    @FXML
    private Label username_lb;

    @FXML
    private Label designation_lb;

    @FXML
    public Button select_btn;
    
    @FXML
    private HBox createsearch_hbox;
    
    
    public void setData(CreateSearchModel csm)
    {
        
        username_lb.setText(csm.getUsername());
        id_lb.setText(csm.getId());
        designation_lb.setText(csm.getDesignation());
    }
    public void hbox_selected()
    {
        createsearch_hbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,CornerRadii.EMPTY,Insets.EMPTY)));
        select_btn.setDisable(true);
    }
    public <E extends Event> void addCodeHandler(EventType<E> eventType, EventHandler<E> handler) {
       select_btn.addEventHandler(eventType, handler);
       //select_btn.setVisible(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
    
