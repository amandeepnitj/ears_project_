/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

import Model.ComputedFeedbackDataModel;
import Model.CreateSearchModel;
import Model.ResultOfMemberModel;
import Model.UserProfileModel;
import Model.ValidationApplicationModel;
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
                //status - pending, selected ,not selected 
                query = "insert into ears.applicant (username,email,contact,designation_id,department_id,description,status)  values('" + username + "','" + email + "','" + contact + "'," + designation_id + "," + department_id + ",'" + description + "','pending')";
                //st1 = conn.createStatement();
                boolean p = st1.execute(query);
                System.out.println("insert query == " + p);
                   
            }
            //getting ID of applicant from applicant table to be used to insert data into ears.committee_applicant_transition_table
            query = "select id from ears.applicant where email= '" + email + "'";
            //st1 = conn.createStatement();
            rs1 = st1.executeQuery(query);
            
            int applicant_id=1;
            if(rs1.next()){
                applicant_id= rs1.getInt(1);
            }
            System.out.println("Applicant id === "+applicant_id);

            //insert data in ears.committee_applicant_transition_table if applicant come under any formed committee
            //department_id,designation_id
            query ="insert into ears.committee_applicant_transition_table select committee_id,"+applicant_id+" as applicant_id from ears.committee_table where department_id="+department_id+" and designation_id="+designation_id;
            boolean p = st1.execute(query);
            System.out.println("insert query for committee_applicant_transition_table== " + p);
                
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
    //this is for getting all the employees for creating a search
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

    //this is for saving the create search data into DB
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
        query = "insert into ears.committee_table (title,department_id,chairperson_name,designation_id,total_members)  values('" + committee_name + "'," + department_id + ",'" + chairperson + "'," + designation_id + ","+search_list.size()+")";
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
        //insert data of commitee and applicants into committee_applicant_transition_table table
        //department_id
        //designation_id
        //committee_id
            query = "insert into ears.committee_applicant_transition_table select "+committee_id+", id from ears.applicant where designation_id="+designation_id+" and department_id="+department_id+" and status='pending';";
            p = st1.execute(query);
            System.out.println("insert query of committee_applicant_transition_table == " + p);
        
        
        
        return true;
    }
    
    public static List<ValidationApplicationModel> ApplicationList(String username) throws SQLException, ClassNotFoundException
    {
        Connection conn = new jdbcconnect().init();
        

        //getting data of all applications come under particular employee-username;
        String query = "select c.title,a.username ,description,d.designation_name,c.committee_id,a.id,c.chairperson_name from ears.committee_applicant_transition_table t inner join ears.applicant a on a.id=t.applicant_id inner join ears.committee_table c on t.committee_id=c.committee_id left join ears.designation_table d on a.designation_id=d.designation_id where t.committee_id in (select committee_id from ears.committee_member_transition_table where username ='"+username+"') ";
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(query);

        List<ValidationApplicationModel> list = new ArrayList();
        ValidationApplicationModel vam;
  
        while (rs1.next()) {
            System.out.println(rs1.getString(1));
            System.out.println(rs1.getString(2));
            System.out.println(rs1.getString(4));
            System.out.println(rs1.getString(3));
            System.out.println(rs1.getString(5));
            System.out.println(rs1.getString(6));
            System.out.println(rs1.getString(7));
            System.out.println("------------------------------------");

            
        vam= new ValidationApplicationModel();
        vam.setCommittee_name(rs1.getString(1));
        vam.setApplicant_name(rs1.getString(2));
        vam.setDesignation_name(rs1.getString(4));
        vam.setApplicant_description(rs1.getString(3));
        vam.setCommittee_id(rs1.getInt(5));
        vam.setApplicant_id(rs1.getInt(6));
        vam.setChairperson_name(rs1.getString(7));
        list.add(vam);
        }

        conn.close();
        return list; 
    }
    
    //add applicationlist data of each application -> written in two tables
    public static void addfeedbackdata(int committee_id,int applicant_id, String chairperson_name,String feedback_code,String feedback_description,String username) throws SQLException, ClassNotFoundException
    {
        //to check if there is already data of particular applicant in feedback_table;
        Connection conn = new jdbcconnect().init();
        String query = "select count(applicant_id) as count1  from ears.feedback_table where applicant_id= " +applicant_id;
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(query);
        boolean needtoaddinfirsttable=true;
        if (rs1.next()) {
            if(rs1.getInt("count1")>=1)
            {
                //data already there in first table
                needtoaddinfirsttable=false;
            }
        }
        if(needtoaddinfirsttable==true)
        {
            //add data in first table- > feedback_table
        query = "insert into ears.feedback_table (committee_id,applicant_id,chairperson_name,chairperson_feedback)  values(" + committee_id + "," + applicant_id + ",'" + chairperson_name + "','null')";
        boolean p = st1.execute(query);
        System.out.println("insert query of feedback_table == " + p);
            
        }
        //get feedback id from feedback_table of previous added row.
        query = "select feedback_id  from ears.feedback_table where applicant_id= " +applicant_id;
        st1 = conn.createStatement();
        rs1 = st1.executeQuery(query);
        int feedback_id=1;
        if (rs1.next()) {
            feedback_id=rs1.getInt(1);
        }
        //add data into second table -> ears.feedback_transition_table
        query = "insert into ears.feedback_transition_table (feedback_id,username,feedback_code,feedback_description)  values(" + feedback_id + ",'" + username + "','" + feedback_code + "','"+feedback_description+"')";
        boolean p = st1.execute(query);
        System.out.println("insert query of feedback_transition_table == " + p);
    }
    
    public static ArrayList<ComputedFeedbackDataModel> getComputedFeedbackData(String username) throws ClassNotFoundException, SQLException
    {
        
        Connection conn = new jdbcconnect().init();
        String query = "select f.feedback_id,a.username,c.title,d.designation_name,c.total_members,temp.validated_member,a.description from ears.feedback_table f left join ears.applicant a on a.id =f.applicant_id left join  ears.committee_table c on f.committee_id=c.committee_id left join ears.designation_table d on a.designation_id= d.designation_id left join (select feedback_id,count(*) as validated_member from ears.feedback_transition_table group by feedback_id) temp on temp.feedback_id=f.feedback_id where f.chairperson_name='"+username+"'";
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(query);
        ArrayList<ComputedFeedbackDataModel> list = new ArrayList<ComputedFeedbackDataModel>();
        ComputedFeedbackDataModel cfdm;
        while(rs1.next()) {
            cfdm = new ComputedFeedbackDataModel();
            cfdm.setFeedback_id(rs1.getInt(1));
            cfdm.setApplicant_name(rs1.getString(2));
            cfdm.setCommittee_name(rs1.getString(3));
            cfdm.setDesignation_name(rs1.getString(4));
            cfdm.setTotal_members(rs1.getInt(5));
            cfdm.setValidated_members(rs1.getInt(6));
            cfdm.setApplicant_description(rs1.getString(7));
            list.add(cfdm);
            
            
        }
        conn.close();
        return list;
    }
    
    public static ArrayList<ResultOfMemberModel> getFeedbackData(int  feedback_id) throws ClassNotFoundException, SQLException
    {
        
        Connection conn = new jdbcconnect().init();
        String query = "select username,feedback_code, feedback_description from ears.feedback_transition_table where feedback_id="+feedback_id;
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(query);
        ArrayList<ResultOfMemberModel> list = new ArrayList<ResultOfMemberModel>();
        ResultOfMemberModel cfdm;
        while(rs1.next()) {
            cfdm = new ResultOfMemberModel();
            cfdm.setUsername(rs1.getString(1));
            cfdm.setFeedback_code(rs1.getString(2));
            cfdm.setFeedback_description(rs1.getString(3));
            list.add(cfdm);
            
            
        }
        conn.close();
        return list;
    }
    
    public static void updatefinalresult(String applicant_name,String final_decision) throws ClassNotFoundException, SQLException
    {
        Connection conn = new jdbcconnect().init();
        String query = "select id from ears.applicant where username='"+applicant_name+"'";
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(query);
        int applicant_id=1;
        if(rs1.next())
        {
            applicant_id=rs1.getInt(1);
        }
        
       
        query = "update ears.feedback_table set chairperson_feedback='"+final_decision+"' where applicant_id="+applicant_id;
        st1 = conn.createStatement();
        st1.executeUpdate(query);
        
        
        conn.close();
        
    }
    public static UserProfileModel getuserprofiledata(String username) throws ClassNotFoundException, SQLException
    {
        Connection conn = new jdbcconnect().init();
        String query = "select username,password,contact,ds.designation_name,dp.department_name from ears.users u left join ears.department_table dp on u.department_id=dp.department_id left join ears.designation_table ds on u.designation_id=ds.designation_id where u.username='"+username+"'";
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(query);
        
        UserProfileModel upm= new UserProfileModel();
        if(rs1.next()) {
            
            upm.setUsername(rs1.getString(1));
            upm.setPassword(rs1.getString(2));
            upm.setContact(rs1.getString(3));
            upm.setDesignation_name(rs1.getString(4));
            upm.setDepartment_name(rs1.getString(5));
            
            
            
        }
        conn.close();
        return upm;
    }
    
    //update user profile data
    public static void updateuserprofiledata(String password,String contact,String username) throws ClassNotFoundException, SQLException
    {
        Connection conn = new jdbcconnect().init();
        String query= "update ears.users set password='"+password+"', contact = '"+contact+"' where username='"+username+"'";
        Statement st1 ;
        st1 = conn.createStatement();
        st1.executeUpdate(query);
        
        conn.close();
    }

}
