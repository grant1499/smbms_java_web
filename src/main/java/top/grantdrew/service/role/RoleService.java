package top.grantdrew.service.role;

import top.grantdrew.pojo.Role;

import java.util.List;

public interface RoleService {
    //获取角色列表
    public abstract List<Role> getRoleList();
}
