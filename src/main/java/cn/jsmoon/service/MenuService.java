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

    /**
     * 根据角色Id查询菜单集合
     * @param roleId
     * @return
     */
    List<Menu> findByRoleId(int roleId);

    /**
     * 根据父节点查找所有子节点
     * @param parentId
     * @return
     */
    List<Menu> findByParentId(int parentId);

    /**
     * 根据ID查找Menu实体
     * @param id
     * @return
     */
    Menu findById(Integer id);
}
