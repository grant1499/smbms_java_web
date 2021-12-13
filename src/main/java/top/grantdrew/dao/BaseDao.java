package top.grantdrew.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static{
        String config = "db.properties";
        InputStream in = BaseDao.class.getClassLoader().getResourceAsStream(config);
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }



    public static Connection getConnection() {
        Connection con = null;
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,username,password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }

    public static ResultSet execute(Connection con,String sql,PreparedStatement preparedStatement,ResultSet resultSet,Object[] params) throws SQLException {
        preparedStatement  = con.prepareStatement(sql);

        for (int i = 0;i < params.length;i ++){
            preparedStatement.setObject(i+1,params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public static int execute(Connection con,String sql,PreparedStatement preparedStatement,Object[] params) throws SQLException {
        preparedStatement = con.prepareStatement(sql);
        for (int i = 0;i < params.length;i ++){
            preparedStatement.setObject(i+1,params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    public static void release(Connection con, PreparedStatement pstm, ResultSet rs) throws SQLException {
        if (con != null){
           con.close();
        }
        if (pstm != null){
            pstm.close();
        }
        if (rs != null){
            rs.close();
        }
    }
}