package top.grantdrew.dao.user;

import top.grantdrew.pojo.Role;
import top.grantdrew.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public User getLoginUser(Connection con,String userCode,String userPassword);

    public User getLoginUser(Connection con,String userCode);

    // 修改密码
    public int updatePwd(Connection con,int id,String password);

    // 根据用户名或角色号查询用户总数，最难的SQL，涉及到SQL语句的拼接
    public int getUserCount(Connection con,String username,int userRole);

    // 获取用户列表
    public List<User> getUserList(Connection con,String username,int userRole,int currentPageNo,int pageSize) throws SQLException;

    // 添加用户
    public int addUser(Connection con,User user);

    // 删除用户
    public int deleUser(Connection con,int userId);

    // 修改用户
    public int modifyUser(Connection conn,User user)throws SQLException;

    public User getUserById(Connection con, String id)throws Exception;
}
