/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_;

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
        stage.setScene(new Scene(root,600,400));
        stage.show();
        
    }
    
    public static void signUpUser(ActionEvent event, String username,String password)
    {
        Connection connection = null;
        PreparedStatement  userinsert = null;
        PreparedStatement checkuserexists = null;
        ResultSet rs = null;
        System.out.println("the username and password typed are ");
        System.out.println(username +"    "+password);
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EARS","root","Amandeep@17");
            checkuserexists =connection.prepareStatement("Select * from users where username = ?");
            checkuserexists.setString(1, username);
            if(rs==null)
            {
                System.out.println("rs is NULL");
            }
            rs=checkuserexists.executeQuery();
            if(rs!=null && rs.isBeforeFirst())
            {
                System.out.print("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            }
            else
            {
                userinsert = connection.prepareStatement("Insert into users (username,password) VALUES (?,?)");
                userinsert.setString(1, username);
                userinsert.setString(2, password);
                userinsert.executeUpdate();
                
                changeScene(event,"logged-in.fxml","Log In",null);
            }
            
        
        
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
           if(rs!= null)
           {
               try{
                   rs.close();
               }
               catch(SQLException e)
               {
                   e.printStackTrace();
               }
           }
           
           if(checkuserexists!= null)
           {
               try{
                   checkuserexists.close();
               }
               catch(SQLException e)
               {
                   e.printStackTrace();
               }
           }
           if(userinsert!= null)
           {
               try{
                   userinsert.close();
               }
               catch(SQLException e)
               {
                   e.printStackTrace();
               }
           }
           if(connection!= null)
           {
               try{
                   connection.close();
               }
               catch(SQLException e)
               {
                   e.printStackTrace();
               }
           }
        }
    }
    
    // log in function
    
    public static void logInUser(ActionEvent event,String username,String password)
    {
        Connection connection = null;
        
        PreparedStatement preparedstatement = null;
        ResultSet rs=null;
        
        
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EARS","root","Amandeep@17");
            preparedstatement =connection.prepareStatement("Select password from users where username = ?");
            preparedstatement.setString(1, username);
            rs=preparedstatement.executeQuery();
            if(!rs.isBeforeFirst())
            {
                System.out.print("User don't find in DB");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User don't find in DB");
                alert.show();
                
                
            }
            else
            {
                while(rs.next())
                {
                    String respassword =rs.getString("password");
                    if(respassword.equals(password))
                    {
                        changeScene(event,"Home.fxml","Home",username);
                    }
                    else
                    {
                        System.out.print("Password doesnot match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password doesnot match");
                        alert.show();
                        
                    }
                }
                
//                changeScene(event,"logged-in.fxml","Log In",null);
            }
            
        
        
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(rs!= null)
           {
               try{
                   rs.close();
               }
               catch(SQLException e)
               {
                   e.printStackTrace();
               }
           }
           
           if(preparedstatement!= null)
           {
               try{
                   preparedstatement.close();
               }
               catch(SQLException e)
               {
                   e.printStackTrace();
               }
           }
           
           if(connection!= null)
           {
               try{
                   connection.close();
               }
               catch(SQLException e)
               {
                   e.printStackTrace();
               }
           }
            
        }
        
    }
    
}
