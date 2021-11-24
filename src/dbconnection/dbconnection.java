package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Snorlax
 */
public class dbconnection {
    public Connection getConnection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String URL ="jdbc:sqlserver://localhost:1433;Database=PlayfairEncrypted;user=sa;password=123";
            Connection con = DriverManager.getConnection(URL);
            return con;
        }
        catch(Exception e){
            return null;
        }
    } 
}
