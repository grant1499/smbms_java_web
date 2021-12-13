package top.grantdrew.service.role;

import org.junit.Test;
import top.grantdrew.dao.BaseDao;
import top.grantdrew.dao.role.RoleDao;
import top.grantdrew.dao.role.RoleDaoImpl;
import top.grantdrew.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService{
    //业务层调用持久层
    private RoleDao roleDao = null;
    public RoleServiceImpl(){
        this.roleDao =new RoleDaoImpl();
    }

    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = new ArrayList<>();
        Connection con = null;

        con = BaseDao.getConnection();

        if (con !=  null){
            try {
                roleList = roleDao.getRoleList(con);
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

        return roleList;
    }

    @Test
    public void test(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = new ArrayList<>();
        roleList = roleService.getRoleList();
        for (Role v : roleList){
            System.out.println(v.getRoleName());
        }
    }
}
