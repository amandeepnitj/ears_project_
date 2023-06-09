/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ears_project_;

import Model.ApplicantModel;
import Model.ComputedFeedbackDataModel;
import Model.CreateSearchModel;
import Model.ResultOfMemberModel;
import Model.UserProfileModel;
import Model.ValidationApplicationModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Aman
 */
public class HomeController implements Initializable {

    private String username;
    private ArrayList<String> search_list;

    @FXML
    private TextField designation_four_tf, department_four_tf;
    @FXML
    private HBox dynamic_first_hbox;
    @FXML
    private Button submit_first_btn, cancel_first_btn, submit_four_btn, logout_four_btn;

    @FXML
    private TextField committee_first_tf, username_four_tf, contact_four_tf;

    @FXML
    private PasswordField password_four_tf;

    @FXML
    private HBox createsearchheading_hbox;
    private int first_designation = 3;
    private String first_chairperson = "";
    @FXML
    private ComboBox designation_first_cb, chairperson_first_cb;

    @FXML
    private TabPane tabpane_tp;

    @FXML
    private Tab tab1, tab2, tab3, tab4;
    //third window componenets declared---------------------
    @FXML
    private ComboBox<String> candidate_third_cb;

    @FXML
    private TextField applicant_third_tf;

    @FXML
    private TextField committee_third_tf;

    @FXML
    private TextField designation_third_tf;

    @FXML
    private TextArea applicant_description_third_ta;

    @FXML
    private VBox resultofmemeber_vbox;

    @FXML
    private TextField finished_validation_third_tf;

    @FXML
    private TextField total_validation_third_tf;

    @FXML
    private ComboBox<String> final_decision_third_cb;

    @FXML
    private Button submit_third_btn;
    // ----------------------finished third window---------
    @FXML
    private Label access_lb;

    @FXML
    void tabSelected_1() {
        System.out.println(1);
        System.out.println(this.username);
        if (this.username != null) {
            createsearchpopulate();
        }
    }
//----------------tab second-------------

    @FXML
    void tabSelected_2() throws SQLException, ClassNotFoundException {
        //clear vbox
        validation_application_vbox.getChildren().clear();

        //populating data of validation applications
        //ApplicationList(username)
        List<ValidationApplicationModel> list = new ArrayList<>(DBUtils.ApplicationList(username));

        for (int i = 0; i < list.size(); i++) {
            //System.out.println(csm.get(i).getId() + "   " + csm.get(i).getUsername() + "   " + csm.get(i).getDesignation());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("validationapplication_item.fxml"));

            try {
                AnchorPane apane = fxmlLoader.load();
                ValidationApplicationController vac = fxmlLoader.getController();
                vac.setData(list.get(i));

                validation_application_vbox.getChildren().add(apane);

                vac.addCodeHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    System.out.println("clicked");

                    ArrayList<String> feedback_list = vac.getData();
                    System.out.println(feedback_list.get(0) + "    " + feedback_list.get(1));
                    try {
                        //to store the submission to DB
                        DBUtils.addfeedbackdata(vac.committee_id, vac.applicant_id, vac.chairperson_name, feedback_list.get(0), feedback_list.get(1), username);
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        System.out.println(2);
    }
    ArrayList<ComputedFeedbackDataModel> cfdm_list = new ArrayList<ComputedFeedbackDataModel>();

    @FXML
    void tabSelected_3() throws ClassNotFoundException, SQLException {
        System.out.println(3);
        candidate_third_cb.getItems().clear();
        cfdm_list.clear();
        //getting data for tab-3
        cfdm_list = DBUtils.getComputedFeedbackData(username);
        ObservableList<String> app_list = FXCollections.observableArrayList();
        for (int i = 0; i < cfdm_list.size(); i++) {
            app_list.add(cfdm_list.get(i).getApplicant_name());
        }
        candidate_third_cb.setItems(app_list);

    }

    //------------------third-----
    @FXML
    void Select_candidate(ActionEvent event) throws ClassNotFoundException, SQLException {
        resultofmemeber_vbox.getChildren().clear();
        String applicant_name = candidate_third_cb.getSelectionModel().getSelectedItem().toString();
        ComputedFeedbackDataModel cfdm = new ComputedFeedbackDataModel();
        for (int i = 0; i < cfdm_list.size(); i++) {
            if (cfdm_list.get(i).getApplicant_name() == applicant_name) {
                cfdm = cfdm_list.get(i);
            }
        }
        applicant_third_tf.setText(cfdm.getApplicant_name());
        committee_third_tf.setText(cfdm.getCommittee_name());
        designation_third_tf.setText(cfdm.getDesignation_name());
        finished_validation_third_tf.setText(Integer.toString(cfdm.getValidated_members()));
        total_validation_third_tf.setText(Integer.toString(cfdm.getTotal_members()));
        applicant_description_third_ta.setText(cfdm.getApplicant_description());
        int feedback_id = cfdm.getFeedback_id();
        //getting data for 3rd tab of all dynamic hboxes
        ArrayList<ResultOfMemberModel> list = DBUtils.getFeedbackData(feedback_id);
        for (int i = 0; i < list.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("resultofmember_item.fxml"));
            try {
                HBox hbox = fxmlLoader.load();
                Resultofmember_Controller rmc = fxmlLoader.getController();
                rmc.setData(list.get(i));
                resultofmemeber_vbox.getChildren().add(hbox);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //for selected or not selected options
        ObservableList<String> select_list = FXCollections.observableArrayList("Selected", "Not Selected");
        final_decision_third_cb.setItems(select_list);

    }

    //when chairperson submit his/her final decision 
    @FXML
    void submit_third(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (candidate_third_cb.getSelectionModel().getSelectedItem() == null || final_decision_third_cb.getSelectionModel().getSelectedItem() == null) {
            //select valid options first.....
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select valid options first");
            alert.show();

        } else {
            String applicant_name = candidate_third_cb.getSelectionModel().getSelectedItem().toString();
            String final_decision = final_decision_third_cb.getSelectionModel().getSelectedItem().toString();
            //update final decision in feedback table
            DBUtils.updatefinalresult(applicant_name, final_decision);
            applicant_third_tf.clear();
            committee_third_tf.clear();
            designation_third_tf.clear();
            finished_validation_third_tf.clear();
            total_validation_third_tf.clear();
            applicant_description_third_ta.clear();
            tabpane_tp.getSelectionModel().select(3);

        }
    }

    @FXML
    void tabSelected_4() throws ClassNotFoundException, SQLException {
        System.out.println(4);
        UserProfileModel upm = DBUtils.getuserprofiledata(username);
        username_four_tf.setText(upm.getUsername());
        password_four_tf.setText(upm.getPassword());
        contact_four_tf.setText(upm.getContact());
        designation_four_tf.setText(upm.getDesignation_name());
        department_four_tf.setText(upm.getDepartment_name());

    }

    @FXML
    void submit_four_func(ActionEvent event) throws ClassNotFoundException, SQLException {
        String password = password_four_tf.getText().trim();
        String contact = contact_four_tf.getText().trim();
        String us = username_four_tf.getText();

        if (password.isEmpty() == true || contact.isEmpty() == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please add all the information first");
            alert.show();
        } else {
            DBUtils.updateuserprofiledata(password, contact, us);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("user profile data updated sucessfully");
            alert.show();
        }

    }

    @FXML
    void logout_func(ActionEvent event) {
        DBUtils.changeScene(event, "logged-in.fxml", "Log In", null);

    }

    @FXML
    void tabSelected_5() throws ClassNotFoundException, SQLException {
        System.out.println(5);
        if (DBUtils.designation_check(username)) {
            finalresult_vbox.getChildren().clear();
            ArrayList<ApplicantModel> ap_list = DBUtils.getfinalapplicationdata(username);

            for (int i = 0; i < ap_list.size(); i++) {
                //System.out.println(csm.get(i).getId() + "   " + csm.get(i).getUsername() + "   " + csm.get(i).getDesignation());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("finalresult_item.fxml"));

                try {
                    HBox apane = fxmlLoader.load();
                    Finalresult_itemController vac = fxmlLoader.getController();
                    vac.setdata(ap_list.get(i));

                    finalresult_vbox.getChildren().add(apane);

                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

    @FXML
    private VBox validation_application_vbox, finalresult_vbox;

    //for chairpeerson CB selection
    @FXML
    void select_chairperson_first(ActionEvent event) {
        System.out.println(chairperson_first_cb.getSelectionModel().getSelectedItem());
        first_chairperson = chairperson_first_cb.getSelectionModel().getSelectedItem().toString();

    }

    //for designation CB of applicant
    @FXML
    void select_designation_first(ActionEvent event) {
        System.out.println(designation_first_cb.getSelectionModel().getSelectedIndex());
        first_designation = designation_first_cb.getSelectionModel().getSelectedIndex() + 3;
    }

    @FXML
    private VBox committe_member_vbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //calling function to populate all the data on create search tab
        createsearchpopulate();

        //Submitting all the data of committee to DB
        submit_first_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String committee_name = committee_first_tf.getText().trim();
                int designation_id = first_designation;
                String chairperson = first_chairperson;
                for (int i = 0; i < search_list.size(); i++) {
                    System.out.println(search_list.get(i));
                }
                search_list.add(chairperson);
                if (committee_name.isEmpty() == true || search_list.size() == 1) {
                    //please add the info
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please add all the information");
                    alert.show();
                } else {

                    try {
                        boolean p = DBUtils.addsearchlistdata(username, committee_name, designation_id, chairperson, search_list);
                        if (p == false) {
                            //Committee already exists
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Error: Committee already exists with same or different name");
                            alert.show();

                        } else {
                            //Confirmation
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("Committee with name :" + committee_name + " has been created, having " + search_list.size() + " members");
                            alert.show();
                            createsearchpopulate();

                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                committee_first_tf.clear();
                search_list.clear();

            }
        });
    }

    public void createsearchpopulate() {
        committe_member_vbox.getChildren().clear();
        search_list = new ArrayList<String>();
        search_list.clear();
        committee_first_tf.clear();
        System.out.println(this.username);
        //combo box chairperson populating data 
        ObservableList<String> cp_list = FXCollections.observableArrayList();

        //combo box designation populating data 
        ObservableList<String> dst_list = FXCollections.observableArrayList("Senior Lecturer", "Associate Professor", "PhD Scholar");
        designation_first_cb.setItems(dst_list);

        // populating search data into the scrollpane's vBox
        List<CreateSearchModel> csm = new ArrayList<>();

        try {
            System.out.println("this.username is " + this.username);
            csm = new ArrayList<>(DBUtils.SearchList(this.username));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < csm.size(); i++) {
            cp_list.add(i, csm.get(i).getUsername());
            System.out.println(csm.get(i).getId() + "   " + csm.get(i).getUsername() + "   " + csm.get(i).getDesignation());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("createsearch_item.fxml"));
            String usrname = csm.get(i).getUsername();
            try {
                HBox hbox = fxmlLoader.load();
                CreateSearchController csc = fxmlLoader.getController();
                csc.setData(csm.get(i));
                csc.addCodeHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    System.out.println("clicked");
                    csc.hbox_selected();

                    search_list.add(usrname);

                });
                committe_member_vbox.getChildren().add(hbox);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        //adding list to chairperson CB
        chairperson_first_cb.setItems(cp_list);

    }

    public void storedata(String username) {
        this.username = username;

        try {
            System.out.println("username is " + this.username);
            boolean p = DBUtils.designation_check(this.username);

            if (p == false) {
                access_lb.setVisible(true);
                submit_first_btn.setDisable(true);
                cancel_first_btn.setDisable(true);
                dynamic_first_hbox.setVisible(false);
                createsearchheading_hbox.setVisible(false);
            } else {
                access_lb.setVisible(false);
                submit_first_btn.setDisable(false);
                cancel_first_btn.setDisable(false);
                dynamic_first_hbox.setVisible(true);
                createsearchheading_hbox.setVisible(true);

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<ValidationApplicationModel> ApplicationList() {
        List<ValidationApplicationModel> list = new ArrayList();
        ValidationApplicationModel vam;
        vam = new ValidationApplicationModel();
        vam.setCommittee_name("com 1");
        vam.setApplicant_name("Jashan");
        vam.setDesignation_name("PhD Scholar");
        vam.setApplicant_description("Asi tenu pyar nhi krde, mohhobatt krde aan");
        list.add(vam);

        vam = new ValidationApplicationModel();
        vam.setCommittee_name("com 1");
        vam.setApplicant_name("Jashan");
        vam.setDesignation_name("PhD Scholar");
        vam.setApplicant_description("Asi tenu pyar nhi krde, mohhobatt krde aan");
        list.add(vam);

        vam = new ValidationApplicationModel();
        vam.setCommittee_name("com 1");
        vam.setApplicant_name("Jashan");
        vam.setDesignation_name("PhD Scholar");
        vam.setApplicant_description("Asi tenu pyar nhi krde, mohhobatt krde aan");
        list.add(vam);

        vam = new ValidationApplicationModel();
        vam.setCommittee_name("com 1");
        vam.setApplicant_name("Jashan");
        vam.setDesignation_name("PhD Scholar");
        vam.setApplicant_description("Asi tenu pyar nhi krde, mohhobatt krde aan");
        list.add(vam);

        vam = new ValidationApplicationModel();
        vam.setCommittee_name("com 1");
        vam.setApplicant_name("Jashan");
        vam.setDesignation_name("PhD Scholar");
        vam.setApplicant_description("Asi tenu pyar nhi krde, mohhobatt krde aan");
        list.add(vam);

        return list;
    }

}
