/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

import Model.ValidationApplicationModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author amandeepsingh12
 */
public class ValidationApplicationController implements Initializable {

    public int committee_id,applicant_id;
    public String chairperson_name;
    public String feedback;
    @FXML
    private TextField committee_name_second_tf;

    @FXML
    private TextField applicant_name_second_tf;

    @FXML
    private TextField designation_name_second_tf;

    @FXML
    private TextArea applicant_description_second_ta;

    @FXML
    private TextArea feedback_description_second_ta;

    @FXML
    private Button submit_second_btn;

    @FXML
    private ComboBox<String> feedback_second_cb;
    
     @FXML
    void select_feedback_cb(ActionEvent event) {
        feedback =feedback_second_cb.getSelectionModel().getSelectedItem().toString();
        
    }

    public void setData(ValidationApplicationModel vpm) {

        committee_name_second_tf.setText(vpm.getCommittee_name());
        applicant_name_second_tf.setText(vpm.getApplicant_name());
        designation_name_second_tf.setText(vpm.getDesignation_name());
        applicant_description_second_ta.setText(vpm.getApplicant_description());
        committee_id=vpm.getCommittee_id();
        applicant_id = vpm.getApplicant_id();
        chairperson_name =vpm.getChairperson_name();

    }

    public ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add(feedback);
        list.add(feedback_description_second_ta.getText());
        return list;

    }

    public <E extends Event> void addCodeHandler(EventType<E> eventType, EventHandler<E> handler) {
        submit_second_btn.addEventHandler(eventType, handler);
        //select_btn.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> feedback_list = FXCollections.observableArrayList("Selected", "Not Selected");
        feedback_second_cb.setItems(feedback_list);

    }

}
