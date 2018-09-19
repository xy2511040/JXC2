package cn.jsmoon.service;

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
}
