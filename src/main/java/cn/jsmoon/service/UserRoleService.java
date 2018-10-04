package cn.jsmoon.service;

import cn.jsmoon.entity.UserRole;

/**
 * 用户角色关联Service类
 * @author: LTQ
 * @create: 2018-09-17 14:13
 **/
public interface UserRoleService {

    /**
     * 根据用户ID删除用户角色关联表信息
     * @param userId
     */
    void deleteByUserId(Integer userId);

    /**
     * 添加或者修改用户角色关联信息
     * @param userRole
     */
    void save(UserRole userRole);

    /**
     * 根据角色ID删除用户角色关联表信息
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);
}
