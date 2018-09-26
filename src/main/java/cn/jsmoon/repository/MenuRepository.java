package cn.jsmoon.repository;

import cn.jsmoon.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 菜单Repository类
 * @author: LTQ
 * @create: 2018-09-03 10:17
 **/
public interface MenuRepository extends JpaRepository<Menu,Integer>{

    /**
     * 根据角色Id查询菜单集合
     * @param roleId
     * @return
     */
    @Query(value = "SELECT m.* FROM t_role r,t_role_menu rm,t_menu m WHERE r.id=rm.role_id AND rm.menu_id=m.id AND r.id=?1",nativeQuery = true)
    List<Menu> findByRoleId(int roleId);

    /**
     * 根据父节点Id和当前登录用户角色Id查询菜单集合
     * @param parentId
     * @param roleId
     * @return
     */
    @Query(value = "SELECT * FROM t_menu WHERE p_id=?1 AND id IN (SELECT menu_id FROM t_role_menu WHERE role_id=?2)",nativeQuery = true)
    List<Menu> findByParentIdAndRoleId(int parentId,int roleId);
}
