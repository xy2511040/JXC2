package cn.jsmoon.service.impl;


import cn.jsmoon.entity.Role;
import cn.jsmoon.repository.RoleRepository;
import cn.jsmoon.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色service实现类
 * @author: LTQ
 * @create: 2018-08-22 17:09
 **/
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository repository;

    @Override
    public List<Role> findByUserId(Integer id) {
        return repository.findByUserId(id);
    }

    @Override
    public Role findById(Integer id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Role> listAll() {
        return repository.findAll();
    }

}
