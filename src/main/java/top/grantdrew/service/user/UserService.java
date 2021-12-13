package top.grantdrew.service.user;

import top.grantdrew.pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public User login(String userCode,String password);

    public boolean getUser(String userCode);

    public boolean updatePwd(int id,String password);

    public int getUserCount(String username,int userRole);

    public List<User> getUserList(String username, int userRole, int currentPageNo, int pageSize);

    public boolean addUser(User user);

    public boolean deleUser(int userId);

    public boolean modifyUser(User user);

    public User getUserById(String id) throws SQLException;

}
