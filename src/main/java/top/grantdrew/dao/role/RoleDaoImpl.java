package top.grantdrew.dao.role;

import top.grantdrew.dao.BaseDao;
import top.grantdrew.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {

    @Override
    public List<Role> getRoleList(Connection con) throws SQLException {
        List<Role> roleList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        if (con != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select * from smbms_role");
            Object[] params = {};
            rs = BaseDao.execute(con, sql.toString(), preparedStatement, rs,params);
            while(rs.next()){
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleName(rs.getString("roleName"));
                role.setRoleCode(rs.getString("roleCode"));
                roleList.add(role);
            }
            BaseDao.release(con,preparedStatement,rs);
        }

        return roleList;
    }
}
