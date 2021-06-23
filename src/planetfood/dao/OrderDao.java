/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dao;

import java.sql.Connection;
import java.util.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Order;
import planetfood.pojo.OrderDetail;

/**
 
 * @author pawan
 */
public class OrderDao {
    public static ArrayList<Order> getOrdersByDate(Date startDate,Date endDate)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from orders where order_date between ? and ?");
        long ms1=startDate.getTime();
        long ms2=endDate.getTime();
        java.sql.Date sdate=new java.sql.Date(ms1);
        java.sql.Date edate=new java.sql.Date(ms2);
        ps.setDate(1,sdate);
        ps.setDate(2,edate);
        ResultSet rs=ps.executeQuery();
        ArrayList<Order> orderList=new ArrayList<>();
        while(rs.next())
        {
            Order obj=new Order();
            obj.setOrdId(rs.getString("order_id"));
            java.sql.Date d=rs.getDate("order_date");
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
            String dateStr=sdf.format(d);
            obj.setOrdDate(dateStr);
            obj.setOrdAmount(rs.getDouble("order_amount"));
            obj.setGst(rs.getDouble("gst"));
            obj.setGrandTotal(rs.getDouble("grand_total"));
            obj.setDiscount(rs.getDouble("gst_discount"));
            obj.setUserId(rs.getString("user_id"));
            orderList.add(obj);
        }
        return orderList;
    }
    public static String getNewID()throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select count(*) from orders");
        int id=101;
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            id=id+rs.getInt(1);
        }
        return "OD"+id;
    }
    public static boolean addOrder(Order order,ArrayList<OrderDetail>orderList)throws SQLException,ParseException{
       Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("insert into orders values(?,?,?,?,?,?,?,?)");
        ps.setString(1, order.getOrdId());
        String dateStr=order.getOrdDate();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date d1=sdf.parse(dateStr);
        java.sql.Date d2=new java.sql.Date(d1.getTime());
        ps.setDate(2, d2);
        ps.setDouble(3, order.getGst());
        ps.setDouble(4, order.getGstAmount());
        ps.setDouble(5, order.getDiscount());
        ps.setDouble(6, order.getGrandTotal());
        ps.setString(7, order.getUserId());
        ps.setDouble(8, order.getOrdAmount());
        int x=ps.executeUpdate();
        PreparedStatement ps2=conn.prepareStatement("insert into order_details values(?,?,?,?)");
        int count=0,y;
        for(OrderDetail detail:orderList){
            ps2.setString(1, detail.getOrdId());
            ps2.setString(2, detail.getProdId());
            ps2.setDouble(3, detail.getQuantity());
            ps2.setDouble(4, detail.getCost());
            y=ps2.executeUpdate();
            count=count+y;
        }
        if(x>0&&count==orderList.size())
            return true;
        else 
            return false;
    }
    public static ArrayList<Order> getAllOrders()throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("select * from orders");
        ArrayList<Order>allOrders=new ArrayList<>();
        while(rs.next())
        {
            Order o=new Order();
            o.setOrdId(rs.getString("order_id"));
            java.util.Date d=rs.getDate("order_date");
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
            String dateStr=sdf.format(d);
            o.setOrdDate(dateStr);
            o.setGst(rs.getDouble("gst"));
            o.setGstAmount(rs.getDouble("gst_amount"));
            o.setDiscount(rs.getDouble("gst_discount"));
            o.setGrandTotal(rs.getDouble("grand_total"));
            o.setUserId(rs.getString("user_id"));
            o.setOrdAmount(rs.getDouble("order_amount"));
            allOrders.add(o);
        }
        return allOrders;
    }
     public static ArrayList<Order> getAllOrdersByCash(String userId)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("select * from orders where user_id=?");
        ps.setString(1, userId);
        ResultSet rs=ps.executeQuery();
        
        ArrayList<Order>allOrders=new ArrayList<>();
        while(rs.next())
        {
            Order o=new Order();
            o.setOrdId(rs.getString("order_id"));
            java.util.Date d=rs.getDate("order_date");
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
            String dateStr=sdf.format(d);
            o.setOrdDate(dateStr);
            o.setGst(rs.getDouble("gst"));
            o.setGstAmount(rs.getDouble("gst_amount"));
            o.setDiscount(rs.getDouble("gst_discount"));
            o.setGrandTotal(rs.getDouble("grand_total"));
            o.setUserId(rs.getString("user_id"));
            o.setOrdAmount(rs.getDouble("order_amount"));
            allOrders.add(o);
        }
        return allOrders;
    }
     public static ArrayList<Order>getTransactions(String userId)throws SQLException{
         Connection conn=DBConnection.getConnection();
         ArrayList<Order>transactions=new ArrayList<>();
         PreparedStatement ps=conn.prepareStatement("select order_id,order_date,grand_total from orders where user_id=?");
         ps.setString(1, userId);
         ResultSet rs=ps.executeQuery();
         while(rs.next())
         {
             Order o=new Order();
             o.setOrdId(rs.getString("order_id"));
             java.util.Date d=rs.getDate("order_date");
             SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
             String date=sdf.format(d);
             o.setOrdDate(date);
             o.setGrandTotal(rs.getDouble("grand_total"));
             transactions.add(o);
         }
         return transactions;
     }
}
