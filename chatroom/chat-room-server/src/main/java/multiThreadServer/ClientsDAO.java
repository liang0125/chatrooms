package multiThreadServer;

import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

/**
 * Author:lorrie
 * Create:2019/2/26
 */


//对数据库的操作
public class ClientsDAO {

    //按照姓名查找客户端
    public String search(String username){
        Connection conn=null;
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        try{
            conn = JDBCUtils.getConnection();
            String sql = "select * from allclients where allclients.name=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                String clientName=rs.getString("name");
                return clientName;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtils.closed(conn,rs,preparedStatement);
        }
        return null;
    }

    public String searchPassword(String userName){
        Connection conn=null;
        PreparedStatement preparedStatement=null;
        ResultSet rs=null;
        try{
            conn = JDBCUtils.getConnection();
            String sql = "select * from allclients where allclients.name=?;";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                String clientPassword=rs.getString("password");
                return clientPassword;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtils.closed(conn,rs,preparedStatement);
        }
        return null;
    }


    //添加客户端
    public void add(String username,String password){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try{
            connection = JDBCUtils.getConnection();
            String sql = "INSERT INTO allclients (name,password) VALUES(?,?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            int num = preparedStatement.executeUpdate();
            if(num>0){
                System.out.println("注册成功!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtils.closed(connection,resultSet,preparedStatement);
        }

    }

}
