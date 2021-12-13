package top.grantdrew.dao.role;

import top.grantdrew.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    // 获取角色列表
    public List<Role> getRoleList(Connection con) throws SQLException;
}
