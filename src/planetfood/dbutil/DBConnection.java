/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dbutil;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author pawan
 */
public class DBConnection {
    private static Connection conn;
    private static final int LOCALHOST = 3306;
    private static final String DB_NAME = "planetfood";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pawan";
    
    static
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
          conn=DriverManager.getConnection("jdbc:mysql://localhost:"+LOCALHOST+"/"+DB_NAME+"?useSSL=false&autoReconnect=true&characterEncoding=latin1&useConfigs=maxPerformance&allowPublicKeyRetrieval=true",USERNAME,PASSWORD);
           System.out.println("Connection openend!"); 
        }catch(ClassNotFoundException cnfe)
       {
       	 System.out.println("Cannot Load Driver");
           cnfe.printStackTrace();
           System.out.println("driver problem "+cnfe.getMessage());
       }
        catch(Exception e)
        {
           JOptionPane.showMessageDialog(null,"DB Error in opening connection in DBconnection","Error!",JOptionPane.ERROR_MESSAGE);
           e.printStackTrace();
        }
        
    }
    public static Connection getConnection()
    {
        return conn;
        
    }
    public static void  closeConnection()
    {
        try
        {
            conn.close();
            System.out.println("Connection close!");
        }
        catch(SQLException s)
            {
                JOptionPane.showMessageDialog(null,"some problem in DataBase","Error!",JOptionPane.ERROR_MESSAGE);
                        s.getStackTrace();
            }
    }
}
