/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import java.sql.*;
/**
 *
 * @author Snorlax
 */
public class dbaccess {

    /**
     * @param args the command line arguments
     */
    private Connection con;
    private Statement stm;
    private PreparedStatement pstm;
    public dbaccess() throws SQLException{
        try{
            dbconnection dbcon  = new dbconnection();
            con = dbcon.getConnection();
            stm = con.createStatement();
        }
        catch(Exception ex){
            
        }
    }
    public ResultSet getAll(String select){
        try{
            pstm = con.prepareStatement(select);
            ResultSet rs = pstm.executeQuery();
            return rs;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    public int update(String update){
        try{
            int i = stm.executeUpdate(update);
            return i;
        }
        catch(Exception ex){
            return -1;
        }
    }
    
    public ResultSet query(String sql){
        try{
            ResultSet rs = stm.executeQuery(sql);
            return rs;
        }
        catch(Exception ex){
            return null;
        }
    }
}
