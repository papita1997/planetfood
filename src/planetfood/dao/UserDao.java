/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import planetfood.dbutil.DBConnection;
import planetfood.pojo.Users;

/**
 *
 * @author pawan
 */
public class UserDao {
    public static String validateUser(Users user)throws SQLException
         {
        
        Connection conn=DBConnection.getConnection();
        String qry="select user_name from users where user_id=? and password=? and user_type=?";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1,user.getUserId());
        ps.setString(2,user.getPassword());
        ps.setString(3,user.getUserType());
        ResultSet rs=ps.executeQuery();
        String username=null;
        if(rs.next()){
         username=rs.getString(1);       
        }
        return username;
    } 
    public static boolean RegisterUser(Users u)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into users values(?,?,?,?,?)");
        ps.setString(1,u.getUserId() );
        ps.setString(2, u.getUserName());
        ps.setString(3, u.getEmpId());
        ps.setString(4, u.getPassword());
        ps.setString(5, u.getUserType());
        int result=ps.executeUpdate();
        return (result==1);
    }
    public static ArrayList<String>getUserId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ArrayList<String>userId=new ArrayList<>();
        ResultSet rs=st.executeQuery("select user_id from users where user_type='Cashier'");
        while(rs.next()){
            String uId=rs.getString("user_id");
            userId.add(uId);
        }
        return userId;
    }
    public static HashMap<String,Users>getDataByUserId(String userId)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select emp_id,user_name from users where user_id=?");
        ps.setString(1, userId);
        ResultSet rs=ps.executeQuery();
        HashMap<String,Users>userData=new HashMap<>();
        while(rs.next()){
            Users u=new Users();
            u.setUserId(userId);
            u.setEmpId(rs.getString("emp_id"));
            u.setUserName(rs.getString("user_name"));
            userData.put(userId, u);
        }
        return userData;
    }
    public static boolean removeCashier(String userId)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("delete from users where user_id=?");
        ps.setString(1, userId);
        int result=ps.executeUpdate();
        return (result==1);
    }
    public static ArrayList<String>getCashEmpId()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ArrayList<String>empId=new ArrayList<>();
        ResultSet rs=st.executeQuery("select emp_id from employees where job='Cashier'");
        while(rs.next())
        {
            String empid=rs.getString("emp_id");
            empId.add(empid);
        }
        return empId;
    }
}