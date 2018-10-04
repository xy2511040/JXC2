package cn.jsmoon.repository;

import cn.jsmoon.entity.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 角色菜单Repository类
 * @author: LTQ
 * @create: 2018-09-27 12:07
 **/
public interface RoleMenuRepository extends JpaRepository<RoleMenu,Integer>,JpaSpecificationExecutor<RoleMenu>{

    /**
     * 根据角色ID删除角色菜单关联表信息
     * @param roleId
     */
    @Query(value = "delete from t_role_menu where role_id=?1",nativeQuery = true)
    @Modifying
    void deleteByRoleId(Integer roleId);
}
