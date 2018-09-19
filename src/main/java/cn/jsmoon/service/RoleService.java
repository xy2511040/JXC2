package cn.jsmoon.service;

import cn.jsmoon.entity.Role;

import java.util.List;

/**
 * @author: LTQ
 * @create: 2018-08-22 17:08
 **/
public interface RoleService {

    /**
     * 通过用户ID查找角色集合
     * @param id
     * @return
     */
    List<Role> fingByUserId(Integer id);

    /**
     * 通过角色ID查找角色实体
     * @param id
     * @return
     */
    Role findById(Integer id);
}
