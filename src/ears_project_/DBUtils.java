/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

import Model.CreateSearchModel;
import ears_project_.DB.jdbcconnect;
import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Aman
 */
public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                HomeController homecontroller = loader.getController();
                // there is function call for him only
                homecontroller.storedata(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

    }

    public static boolean signUpUser(ActionEvent event, String username, String password, String contact, int designation_id, int department_id) throws ClassNotFoundException, SQLException {

        {
            Connection conn = new jdbcconnect().init();
            String query = "select count(*) as count1 from ears.users where username= '" + username + "'";
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(query);
            boolean useralreadyexists = false;
            if (rs1.next()) {
                System.out.println("the value of count1 is " + rs1.getInt("count1"));
                if (rs1.getInt("count1") >= 1) {
                    useralreadyexists = true;
                    System.out.println(" user already exists  " + useralreadyexists);
                    return false;

                }

            }
            //insert data into user table
            if (useralreadyexists == false) {
                query = "insert into ears.users (username,password,contact,designation_id,department_id)  values('" + username + "','" + password + "','" + contact + "'," + designation_id + "," + department_id + ")";
                //st1 = conn.createStatement();
                boolean p = st1.execute(query);
                System.out.println("insert query == " + p);

            }
            conn.close();
        }

        return true;

    }

    // log in function
    public static boolean logInUser(ActionEvent event, String username, String password) throws ClassNotFoundException, SQLException {
        Connection con = new jdbcconnect().init();
        boolean p;

        String query = "select count(*) as count1 from ears.users where username= '" + username + "' and password= '" + password + "'";
        Statement st1 = con.createStatement();
        ResultSet rs1 = st1.executeQuery(query);

        if (rs1.next()) {
            if (rs1.getInt("count1") >= 1) {
                con.close();
                return true;
            } else {
                System.out.println("we can't find username and password for login validation");
                con.close();
                return false;
            }
        }

        return false;
    }

    //applicant submission function
    public static boolean Applicantuser(ActionEvent event, String username, String email, String contact, String description, int designation_id, int department_id) throws ClassNotFoundException, SQLException {

        {
            Connection conn = new jdbcconnect().init();
            String query = "select count(*) as count1 from ears.applicant where email= '" + email + "'";
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(query);
            boolean useralreadyexists = false;
            if (rs1.next()) {
                System.out.println("the value of count1 is " + rs1.getInt("count1"));
                if (rs1.getInt("count1") >= 1) {
                    useralreadyexists = true;
                    System.out.println(" user already exists  " + useralreadyexists);
                    return false;

                }

            }
            //insert data into user table
            if (useralreadyexists == false) {
                query = "insert into ears.applicant (username,email,contact,designation_id,department_id,description)  values('" + username + "','" + email + "','" + contact + "'," + designation_id + "," + department_id + ",'" + description + "')";
                //st1 = conn.createStatement();
                boolean p = st1.execute(query);
                System.out.println("insert query == " + p);

            }
            conn.close();
        }

        return true;

    }

    //check if user is HOD, Dean or not
    public static boolean designation_check(String username) throws ClassNotFoundException, SQLException {
        //return true->if <=2 else false
        {
            Connection conn = new jdbcconnect().init();
            String query = "select designation_id as count1  from ears.users where username= '" + username + "'";
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(query);

            if (rs1.next()) {
                System.out.println("the value of count1 is " + rs1.getInt("count1"));
                if (rs1.getInt("count1") <= 2) {
                    return true;

                } else {
                    return false;
                }

            }

            conn.close();
        }

        return false;

    }

    public static List<CreateSearchModel> SearchList(String username) throws ClassNotFoundException, SQLException {
        int department_id = 1;
        Connection conn = new jdbcconnect().init();
        String query = "select department_id as count1  from ears.users where username= '" + username + "'";
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(query);

        if (rs1.next()) {
            System.out.println("the value of department_id is " + rs1.getInt("count1"));
            department_id = rs1.getInt("count1");

        }

        //getting data of all employees of same department with rank>=2;
        query = "select username,designation_name from ears.users  u left join ears.designation_table d on u.designation_id=d.designation_id where department_id=" + department_id + " and u.designation_id>2;";
        st1 = conn.createStatement();
        rs1 = st1.executeQuery(query);

        List<CreateSearchModel> list = new ArrayList();
        CreateSearchModel csm;
        int count = 1;
        while (rs1.next()) {
            System.out.println(rs1.getString(1));
            System.out.println(rs1.getString(2));

            csm = new CreateSearchModel();
            csm.setId(Integer.toString(count));
            csm.setUsername(rs1.getString(1));
            csm.setDesignation(rs1.getString(2));

            list.add(csm);
            count++;

        }

        conn.close();
        return list;

    }

    public static boolean addsearchlistdata(String username, String committee_name, int designation_id, String chairperson, ArrayList<String> search_list) throws ClassNotFoundException, SQLException {
        //to find department id of current user
        Connection conn = new jdbcconnect().init();
        String query = "select department_id as count1  from ears.users where username= '" + username + "'";
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(query);
        
        int department_id=1;

        if (rs1.next()) {
            System.out.println("the value of department_id is " + rs1.getInt("count1"));
            department_id = rs1.getInt("count1");

        }
        
        //check if committee with same name exists or not
        query = "select count(committee_id) as count1  from ears.committee_table where title = '" + committee_name + "'";
        //st1 = conn.createStatement();
        rs1 = st1.executeQuery(query);
        
        if (rs1.next()) {
            System.out.println("the value of department_id is " + rs1.getInt("count1"));
            if(rs1.getInt("count1")>=1)
            {
                return false;
            }
            

        }
        
        
        
        
        //add data to committee_table
        query = "insert into ears.committee_table (title,department_id,chairperson_name,designation_id)  values('" + committee_name + "'," + department_id + ",'" + chairperson + "'," + designation_id + ")";
        boolean p = st1.execute(query);
        System.out.println("insert query of committee_table == " + p);
        
        //get the committee_id from same table to insert data into intermediate table
        query = "select committee_id as count1  from ears.committee_table where title= '" + committee_name + "'";
        //st1 = conn.createStatement();
        rs1 = st1.executeQuery(query);
        int committee_id=1;
        if (rs1.next()) {
            System.out.println("the value of department_id is " + rs1.getInt("count1"));
            committee_id= rs1.getInt("count1");
        }
        
        //insert committee_members data into committee_member_transition_table
        for(int i=0;i<search_list.size();i++)
        {
            query = "insert into ears.committee_member_transition_table (committee_id,username)  values(" + committee_id + ",'" + search_list.get(i) + "')";
            p = st1.execute(query);
            System.out.println("insert query of committee_member_transition_table == " + p);
            
            
        }
        
        return true;
    }

}
