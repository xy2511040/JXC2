package cn.jsmoon.service.impl;

import cn.jsmoon.entity.UserRole;
import cn.jsmoon.repository.UserRoleRepository;
import cn.jsmoon.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户角色关联Service实现类
 * @author: LTQ
 * @create: 2018-09-17 14:26
 **/
@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

    @Resource
    private UserRoleRepository userRoleRepository;

    @Override
    public void deleteByUserId(Integer userId) {
        userRoleRepository.deleteByUserId(userId);
    }

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }
}
