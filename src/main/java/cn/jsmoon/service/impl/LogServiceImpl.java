package cn.jsmoon.service.impl;

import cn.jsmoon.entity.Log;
import cn.jsmoon.entity.User;
import cn.jsmoon.repository.LogRepository;
import cn.jsmoon.repository.UserRepository;
import cn.jsmoon.service.LogService;
import cn.jsmoon.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * 日志service实现类
 * @author: LTQ
 * @create: 2018-10-05 09:59
 **/
@Service("logService")
public class LogServiceImpl implements LogService {

    @Resource
    private LogRepository logRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public void save(Log log) {
        log.setTime(new Date());    //设置当前操作时间
        log.setUser(userRepository.findByUserName((String) SecurityUtils.getSubject().getPrincipal())); //在Shiro中获取当前登录用户用户名
        logRepository.save(log);
    }

    @Override
    public List<Log> list(Log log, Integer page, Integer pageSize, Sort.Direction direction, String... properties) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
        Page<Log> pageUser = logRepository.findAll((Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                search(log, root, query, cb), pageable);    //调用查询条件类，通过jpa动态查询
        return pageUser.getContent();
    }

    @Override
    public Long getCount(Log log) {
        Long count = logRepository.count((Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                search(log, root, query, cb));      //调用查询条件类，通过jpa动态查询
        return count;
    }

    /**
     * 日志查询条件工具类
     * @param log
     * @param root
     * @param query
     * @param cb
     * @return
     */
    private Predicate search(Log log, Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (log != null) {
            if (StringUtil.isNotEmpty(log.getType())) {
                predicate.getExpressions().add(cb.equal(root.get("type"), log.getType()));
            }
            if (log.getUser() != null && StringUtil.isNotEmpty(log.getUser().getTrueName())) {
                predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%" + log.getUser().getTrueName() + "%"));
            }
            if (log.getBtime() != null) {
                predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("time"), log.getBtime()));
            }
            if (log.getEtime() != null) {
                predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("time"), log.getEtime()));
            }
        }
        return predicate;
    }

}

