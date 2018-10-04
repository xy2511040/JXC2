package cn.jsmoon.service;

import cn.jsmoon.entity.RoleMenu;

/**
 * 角色菜单关联Service类
 * @author: LTQ
 * @create: 2018-09-27 12:14
 **/
public interface RoleMenuService {

    /**
     * 根据角色ID删除角色菜单关联表信息
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);

    /**
     * 添加或者修改角色菜单关联信息
     * @param roleMenu
     */
    void save(RoleMenu roleMenu);
}
