package cn.jsmoon.service.impl;

import cn.jsmoon.entity.RoleMenu;
import cn.jsmoon.repository.RoleMenuRepository;
import cn.jsmoon.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 角色菜单关联Service实现类
 * @author: LTQ
 * @create: 2018-09-27 12:15
 **/
@Service("roleMenuService")
@Transactional
public class RoleMenuServiceImlp implements RoleMenuService{

    @Resource
    private RoleMenuRepository roleMenuRepository;

    @Override
    public void deleteByRoleId(Integer roleId) {
        roleMenuRepository.deleteByRoleId(roleId);
    }

    @Override
    public void save(RoleMenu roleMenu) {
        roleMenuRepository.save(roleMenu);
    }
}
