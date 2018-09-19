package cn.jsmoon.service;

import cn.jsmoon.entity.Menu;

import java.util.List;

/**
 * 菜单Service类
 * @author: LTQ
 * @create: 2018-09-05 11:27
 **/
public interface MenuService {

    /**
     * 根据父节点Id和当前登录用户角色Id查询菜单集合
     * @param parentId
     * @param roleId
     * @return
     */
    List<Menu> findByParentIdAndRoleId(int parentId, int roleId);
}
