/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

import ears_project_.DB.jdbcconnect;
import java.sql.*;
import java.io.IOException;
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
    public static void changeScene(ActionEvent event,String fxmlFile,String title, String username)
    {
        Parent root = null;
        
        if(username!=null)
        {
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root= loader.load();
                HomeController homecontroller = loader.getController();
                // there is function call for him only
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try{
            root=   FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,800,600));
        stage.show();
        
    }
    
    public static boolean signUpUser(ActionEvent event, String username,String password,String contact,int designation_id,int department_id) throws ClassNotFoundException, SQLException
    {
        
        {
            Connection conn = new jdbcconnect().init();
            String query="select count(*) as count1 from ears.users where username= '"+username+"'";
            Statement st1 = conn.createStatement();
            ResultSet rs1 =st1.executeQuery(query);
            boolean useralreadyexists=false;
            if(rs1.next())
            {
                System.out.println("the value of count1 is "+rs1.getInt("count1"));
                if(rs1.getInt("count1")>=1)
                {
                    useralreadyexists=true;
                    System.out.println(" user already exists  "+useralreadyexists);
                    return false;
                    
                }
                
            }
            //insert data into user table
            if(useralreadyexists==false)
            {
                query="insert into ears.users (username,password,contact,designation_id,department_id)  values('"+username+"','"+password+"','"+contact+"',"+designation_id+","+department_id+")";
                //st1 = conn.createStatement();
                boolean p=st1.execute(query);
                System.out.println("insert query == "+p);
                
            }
            conn.close();
        }
       
        return true;
    
    }
    
    // log in function
    
    public static boolean logInUser(ActionEvent event,String username,String password) throws ClassNotFoundException, SQLException
    {     
        Connection con = new jdbcconnect().init();
        boolean p;
        
            String query="select count(*) as count1 from ears.users where username= '"+username+"' and password= '"+password+"'";
            Statement st1 = con.createStatement();
            ResultSet rs1 =st1.executeQuery(query);
            
            if(rs1.next())
            {
                if(rs1.getInt("count1")>=1)
                {
                    con.close();
                    return true;
                }
                else
                {
                    System.out.println("we can't find username and password for login validation");
                    con.close();
                    return false;
                }
            }
            
        
        return false;
    }
    
    //applicant submission function
    public static boolean Applicantuser(ActionEvent event, String username,String email,String contact,String description,int designation_id,int department_id) throws ClassNotFoundException, SQLException
    {
        
        {
            Connection conn = new jdbcconnect().init();
            String query="select count(*) as count1 from ears.applicant where email= '"+email+"'";
            Statement st1 = conn.createStatement();
            ResultSet rs1 =st1.executeQuery(query);
            boolean useralreadyexists=false;
            if(rs1.next())
            {
                System.out.println("the value of count1 is "+rs1.getInt("count1"));
                if(rs1.getInt("count1")>=1)
                {
                    useralreadyexists=true;
                    System.out.println(" user already exists  "+useralreadyexists);
                    return false;
                    
                }
                
            }
            //insert data into user table
            if(useralreadyexists==false)
            {
                query="insert into ears.applicant (username,email,contact,designation_id,department_id,description)  values('"+username+"','"+email+"','"+contact+"',"+designation_id+","+department_id+",'"+description+"')";
                //st1 = conn.createStatement();
                boolean p=st1.execute(query);
                System.out.println("insert query == "+p);
                
            }
            conn.close();
        }
       
        return true;
    
    }
    
}
