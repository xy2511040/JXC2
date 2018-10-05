package cn.jsmoon.service;

import cn.jsmoon.entity.Role;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author: LTQ
 * @create: 2018-08-22 17:08
 **/
public interface RoleService {

    /**
     * 通过角色名查找用户实体
     * @param roleName
     * @return
     */
    Role findByRoleName(String roleName);

    /**
     * 通过用户ID查找角色集合
     * @param id
     * @return
     */
    List<Role> findByUserId(Integer id);

    /**
     * 通过角色ID查找角色实体
     * @param id
     * @return
     */
    Role findById(Integer id);

    /**
     * 查询全部角色
     * @return
     */
    List<Role> listAll();

    /**
     * 根据条件分页查询角色信息集合
     * @param role
     * @param page 当前页
     * @param pageSize 每页数据条数
     * @param direction 数据库排序规则
     * @param properties 根据哪个字段排序
     * @return
     */
    List<Role> list(Role role, Integer page, Integer pageSize, Sort.Direction direction, String...properties);

    /**
     * 获取角色总记录数
     * @param role
     * @return
     */
    Long getCount(Role role);

    /**
     * 添加或者修改角色信息
     * @param role
     */
    void save(Role role);

    /**
     * 根据ID删除角色信息
     * @param id
     */
    void deleteById(Integer id);
}
