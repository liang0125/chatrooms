package multiThreadServer;

import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        Statement stmt=null;
        ResultSet rs=null;
        try{
            conn = JDBCUtils.getConnection();
            stmt = conn.createStatement();
            String sql = "select * from allclients where allclients.name='"+username+"';";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                String clientName=rs.getString("name");
                return clientName;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtils.closed(conn,rs,stmt);
        }
        return null;
    }

    public String searchPassword(String userName){
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        try{
            conn = JDBCUtils.getConnection();
            stmt = conn.createStatement();
            String sql = "select * from allclients where allclients.name='"+userName+"';";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                String clientPassword=rs.getString("password");
                return clientPassword;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtils.closed(conn,rs,stmt);
        }
        return null;
    }


    //添加客户端
    public void add(String username,String password){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try{
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();
            String sql = "INSERT INTO allclients (name,password) VALUES('"+username+"','"+password+"');";
            int num = statement.executeUpdate(sql);
            if(num>0){
                System.out.println("send successful!");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtils.closed(connection,resultSet,statement);
        }

    }

}
