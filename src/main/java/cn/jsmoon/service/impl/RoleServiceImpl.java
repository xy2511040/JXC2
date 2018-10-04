package cn.jsmoon.service.impl;


import cn.jsmoon.entity.Role;
import cn.jsmoon.entity.Role;
import cn.jsmoon.repository.RoleRepository;
import cn.jsmoon.service.RoleService;
import cn.jsmoon.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 角色service实现类
 * @author: LTQ
 * @create: 2018-08-22 17:09
 **/
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public List<Role> findByUserId(Integer id) {
        return roleRepository.findByUserId(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> list(Role role, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Role> pageRole = roleRepository.findAll((Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            if (role != null) {
                if (StringUtil.isNotEmpty(role.getName())) {
                    predicate.getExpressions().add(cb.like(root.get("name"), "%" + role.getName() + "%"));
                }
                predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
            }
            return predicate;
        }, pageable);
        return pageRole.getContent();
    }

    @Override
    public Long getCount(Role role) {
        Long count = roleRepository.count((Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            if (role != null) {
                if (StringUtil.isNotEmpty(role.getName())) {
                    predicate.getExpressions().add(cb.like(root.get("name"), "%" + role.getName() + "%"));
                }
                predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
            }
            return predicate;
        });
        return count;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

}
