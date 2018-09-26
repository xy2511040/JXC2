package cn.jsmoon.service.impl;

import cn.jsmoon.entity.User;
import cn.jsmoon.repository.UserRepository;
import cn.jsmoon.service.UserService;
import cn.jsmoon.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 用户service实现类
 *
 * @author: LTQ
 * @create: 2018-08-22 12:05
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> list(User user, Integer page, Integer pageSize, Direction direction, String... properties) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<User> pageUser = userRepository.findAll((Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            if (user != null) {
                if (StringUtil.isNotEmpty(user.getUserName())) {
                    predicate.getExpressions().add(cb.like(root.get("userName"), "%" + user.getUserName() + "%"));
                }
                predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
            }
            return predicate;
        }, pageable);
        return pageUser.getContent();
    }

    @Override
    public Long getCount(User user) {
        Long count = userRepository.count((Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();
            if (user != null) {
                if (StringUtil.isNotEmpty(user.getUserName())) {
                    predicate.getExpressions().add(cb.like(root.get("userName"), "%" + user.getUserName() + "%"));
                }
                predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
            }
            return predicate;
        });
        return count;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }


}
