package top.grantdrew.dao.user;

import top.grantdrew.dao.BaseDao;
import top.grantdrew.pojo.User;
import top.grantdrew.service.user.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    @Override
    public User getLoginUser(Connection con, String userCode,String userPassword) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        User user = null;

        if (con != null){
            String sql = "select * from smbms_user where userCode=? and userPassword=?";
            Object[] params = {userCode,userPassword};

            try {
                rs = BaseDao.execute(con,sql,preparedStatement,rs,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        try {
            if (rs.next()) {
                user = new User();
                try {
                    user.setId(rs.getInt("id"));
                    user.setUserCode(rs.getString("userCode"));
                    user.setUserName(rs.getString("userName"));
                    user.setUserPassword(rs.getString("userPassword"));
                    user.setGender(rs.getInt("gender"));
                    user.setBirthday(rs.getDate("birthday"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setUserRole(rs.getInt("userRole"));
                    user.setCreatedBy(rs.getInt("createdBy"));
                    user.setCreationDate(rs.getTimestamp("creationDate"));
                    user.setModifyBy(rs.getInt("modifyBy"));
                    user.setModifyDate(rs.getTimestamp("modifyDate"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            BaseDao.release(null,preparedStatement,rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public User getLoginUser(Connection con, String userCode) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        User user = null;

        if (con != null){
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};

            try {
                rs = BaseDao.execute(con,sql,preparedStatement,rs,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (rs.next()){
                    user = new User();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public int updatePwd(Connection con, int id, String password) {
        PreparedStatement preparedStatement = null;

        int rs = 0;
        if (con != null){
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {password,id};
            try {
                rs = BaseDao.execute(con,sql,preparedStatement,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                BaseDao.release(con,preparedStatement,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return rs;
    }

    @Override
    public int getUserCount(Connection con, String username, int userRole) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int count = 0;

        if (con != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1)  as count from smbms_user U,smbms_role R  where U.userRole = R.id");
            // 前面用Object数组存放占位参数，这里用ArrayList存放
            List<Object> list = new ArrayList<>();

            if (username != null && username.length() != 0){
                sql.append(" and U.userName like ?");
                list.add("'%" + username + "%'");
            }
            if (userRole > 0 && userRole < 4){
                sql.append(" and U.userRole = ?");
                list.add(userRole);
            }

            Object[] params = list.toArray();
            System.out.println("UserDaoImpl --> getUserCount : " + sql.toString());
            try {
                rs = BaseDao.execute(con,sql.toString(),preparedStatement,rs,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (rs.next()){
                    count  = rs.getInt("count");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                BaseDao.release(con,preparedStatement,rs);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return count;
    }

    @Override
    public List<User> getUserList(Connection con, String username, int userRole, int currentPageNo, int pageSize) throws SQLException {
        List<User> userList  = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        if (con != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");

            //建一个集合来存储参数
            List<Object> list = new ArrayList<>();
            if(username!=null){
                sql.append(" and u.userName like ?");
                list.add("%"+username+"%");//默认下标为0
            }
            if(userRole>0 & userRole<4){
                sql.append(" and u.userRole = ?");
                list.add(userRole);//默认下标为1
            }

            //在数据库中 分页使用limit startIndex,pageSize 总数
            //当前页 = (当前页-1)*页面大小
            sql.append(" order by u.creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("getUserList的语句"+sql.toString());

            try {
                rs =  BaseDao.execute(con,sql.toString(),preparedStatement,rs,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setUserRoleName(rs.getString("userRoleName"));
                user.setUserRole(rs.getInt("userRole"));

                userList.add(user);
            }

            try {
                BaseDao.release(con,preparedStatement,rs);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return userList;
    }

    @Override
    public int addUser(Connection con, User user) {
        int count = 0;
        PreparedStatement preparedStatement = null;

        if (con != null){
            String sql = "insert into smbms_user (userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate)values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params ={user.getUserCode(),user.getUserName(),user.getUserPassword(),user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),user.getUserRole(),user.getCreatedBy(),user.getCreationDate()};

            try {
                count = BaseDao.execute(con,sql,preparedStatement,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                //try {
                //    BaseDao.release(con,preparedStatement,null);
                //} catch (SQLException throwables) {
                //    throwables.printStackTrace();
                //}
            }
        }

        return count;
    }

    @Override
    public int deleUser(Connection con, int userId) {
        int count = 0;
        PreparedStatement preparedStatement = null;

        if (con != null){
            String sql = "delete from smbms_user where id = ?";
            Object[] params = {userId};

            try {
                count = BaseDao.execute(con,sql,preparedStatement,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int modifyUser(Connection conn, User user) throws SQLException {
        int count = 0;
        PreparedStatement preparedStatement = null;

        if (conn != null){
            String sql = "update smbms_user set userName=?,"+
                    "gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {user.getUserName(),user.getGender(),user.getBirthday(),
                    user.getPhone(),user.getAddress(),user.getUserRole(),user.getModifyBy(),
                    user.getModifyDate(),user.getId()};

            try {
                count = BaseDao.execute(conn,sql,preparedStatement,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public User getUserById(Connection con, String id) throws Exception {
        User user = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != con){
            String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
            Object[] params = {id};
            rs = BaseDao.execute(con, sql, pstm, rs, params);
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
                user.setUserRoleName(rs.getString("userRoleName"));
            }
            BaseDao.release(null, pstm, rs);
        }
        return user;
    }


}
