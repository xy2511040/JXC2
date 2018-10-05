package cn.jsmoon.service;

import cn.jsmoon.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 用户service接口
 * @author: LTQ
 * @create: 2018-08-21 10:50
 **/
public interface UserService {
    /**
     * 通过用户名查找用户实体
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 根据条件分页查询用户信息集合
     * @param user
     * @param page 当前页
     * @param pageSize 每页数据条数
     * @param direction 数据库排序规则
     * @param properties 根据哪个字段排序
     * @return
     */
    List<User> list(User user, Integer page, Integer pageSize, Sort.Direction direction,String...properties);

    /**
     * 获取用户总记录数
     * @param user
     * @return
     */
    Long getCount(User user);

    /**
     * 添加或者修改用户信息
     * @param user
     */
    void save(User user);

    /**
     * 根据ID删除用户信息
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    User findById(Integer id);

}
