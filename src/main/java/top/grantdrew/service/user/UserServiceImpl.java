package top.grantdrew.service.user;

import org.junit.Test;
import top.grantdrew.dao.BaseDao;
import top.grantdrew.dao.user.UserDao;
import top.grantdrew.dao.user.UserDaoImpl;
import top.grantdrew.pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserServiceImpl implements UserService{
    private UserDao userDao;

    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String userCode, String password) {
        Connection con = null;
        User user = null;

        con = BaseDao.getConnection();
        user = userDao.getLoginUser(con,userCode,password);

        try {
            BaseDao.release(con,null,null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  user;
    }

    @Override
    public boolean getUser(String userCode) {
        boolean flag = false;

        if (userCode == null || (userCode.length() == 0)){
            return true;
        }

        Connection con = BaseDao.getConnection();

        if (con != null){
            User user = userDao.getLoginUser(con,userCode);
            if (user != null) flag = true;

            try {
                BaseDao.release(con,null,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return flag;
    }

    @Override
    public boolean updatePwd(int id, String password) {
        Connection con = BaseDao.getConnection();
        boolean flag = false;

        if (con != null){
            if (userDao.updatePwd(con,id,password) > 0){
                flag = true;
            }
        }

        try {
            BaseDao.release(con,null,null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    @Override
    public int getUserCount(String username,int userRole) {
        int count = 0;
        Connection con = BaseDao.getConnection();

        if (con != null){
            count = userDao.getUserCount(con,username,userRole);
        }

        try {
            BaseDao.release(con,null,null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    @Override
    public List<User> getUserList(String username, int userRole, int currentPageNo, int pageSize) {
        List<User> userList = new ArrayList<>();
        Connection con = null;

        con = BaseDao.getConnection();

        if (con != null){
            try {
                userList = userDao.getUserList(con,username,userRole,currentPageNo,pageSize);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    BaseDao.release(con,null,null);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return userList;
    }

    @Override
    public boolean addUser(User user) {
        Connection conn = null;
        boolean flag = false;
        try {
            //获取数据库连接
            conn = BaseDao.getConnection();
            //开启JDBC事务管理
            conn.setAutoCommit(false);
            //Service层调用dao层的方法添加用户
            int updateRows = userDao.addUser(conn, user);
            conn.commit();
            if(updateRows > 0){
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }finally {
            //释放连接
            //try {
            //    BaseDao.release(conn, null, null);
            //} catch (SQLException throwables) {
            //    throwables.printStackTrace();
            //}
            return flag;
        }
    }

    @Override
    public boolean deleUser(int userId) {
        boolean flag = false;
        Connection con  = BaseDao.getConnection();

        try {
            //开启JDBC事务管理
            con.setAutoCommit(false);
            //Service层调用dao层的方法添加用户
            int updateRows = userDao.deleUser(con, userId);
            con.commit();
            if(updateRows > 0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return flag;
    }

    @Override
    public boolean modifyUser(User user) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            if(userDao.modifyUser(connection,user) > 0)
                flag = true;
            connection.commit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }


    @Override
    public User getUserById(String id) throws SQLException {
        User user = null;
        Connection connection = null;
        try{
            connection = BaseDao.getConnection();
            user = userDao.getUserById(connection,id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            user = null;
        }finally{
            BaseDao.release(connection, null, null);
        }
        return user;
    }

    @Test
    public void test(){

    }
}
