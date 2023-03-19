/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ears_project_.DB;
import java.sql.*;
/**
 *
 * @author amandeepsingh12
 */
public class jdbcconnect {
    Connection conn;
    public jdbcconnect()
    {
        
    }
    public Connection init() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","Amandeep@17");
        return conn;
        
    }
    public void closeconn() throws SQLException
    {
        if(conn!=null)
        {
            conn.close();
        }
    }
    
}
