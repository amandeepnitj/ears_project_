/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ears_project_;

import Model.CreateSearchModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private Button submit_first_btn, cancel_first_btn, submit_four_btn, logout_four_btn;

    @FXML
    private TextField committee_first_tf, username_four_tf, password_four_tf, contact_four_tf;

    private int first_designation = 3;
    private String first_chairperson = "";
    @FXML
    private ComboBox designation_first_cb, chairperson_first_cb, department_four_cb, designation_four_cb;

    @FXML
    private TabPane tabpane_tp;

    @FXML
    private Tab tab1, tab2, tab3, tab4;

    @FXML
    private Label access_lb;

    @FXML
    void tabSelected_1() {
        System.out.println(1);
        System.out.println(this.username);
    }

    @FXML
    void tabSelected_2() {
        System.out.println(2);
    }

    @FXML
    void tabSelected_3() {
        System.out.println(3);
    }

    @FXML
    void tabSelected_4() {
        System.out.println(4);
    }

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
        search_list = new ArrayList<String>();
        search_list.clear();
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
                    search_list.add(usrname);

                });
                committe_member_vbox.getChildren().add(hbox);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        //adding list to chairperson CB
        chairperson_first_cb.setItems(cp_list);

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
                            alert.setContentText("Error: Committee already exists");
                            alert.show();

                        }
                        else
                        {
                            //Confirmation
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("Committee with name :"+committee_name+" has been created, having "+search_list.size()+" members");
                            alert.show();
                            
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

    public void storedata(String username) {
        this.username = username;

        try {
            System.out.println("username is " + this.username);
            boolean p = DBUtils.designation_check(this.username);

            if (p == false) {
                access_lb.setVisible(true);
                submit_first_btn.setDisable(true);
                cancel_first_btn.setDisable(true);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<CreateSearchModel> SearchList() {
        List<CreateSearchModel> list = new ArrayList();
        CreateSearchModel csm = new CreateSearchModel();
        csm.setId("1");
        csm.setUsername("Aman");
        csm.setDesignation("Head of department");

        list.add(csm);

        csm = new CreateSearchModel();
        csm.setId("2");
        csm.setUsername("Prof1");
        csm.setDesignation("Head of department");

        list.add(csm);

        csm = new CreateSearchModel();
        csm.setId("3");
        csm.setUsername("Prof3");
        csm.setDesignation("Head of department");

        list.add(csm);

        csm = new CreateSearchModel();
        csm.setId("4");
        csm.setUsername("Prof4");
        csm.setDesignation("Head of department");

        list.add(csm);

        csm = new CreateSearchModel();
        csm.setId("5");
        csm.setUsername("Prof5");
        csm.setDesignation("Head of department");

        list.add(csm);

        csm = new CreateSearchModel();
        csm.setId("6");
        csm.setUsername("Prof6");
        csm.setDesignation("Head of department");

        list.add(csm);

        csm = new CreateSearchModel();
        csm.setId("7");
        csm.setUsername("Prof7");
        csm.setDesignation("Head of department");

        list.add(csm);

        return list;
    }

}
