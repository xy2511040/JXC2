package cn.jsmoon.service;

import cn.jsmoon.entity.Log;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 日志service接口
 * @author: LTQ
 * @create: 2018-10-05 09:58
 **/

public interface LogService {

    /**
     * 添加或者修改日志信息
     * @param log
     */
    void save(Log log);

    /**
     * 根据条件分页查询日志集合
     * @param log
     * @param page 当前页
     * @param pageSize 每页数据条数
     * @param direction 数据库排序规则
     * @param properties 根据哪个字段排序
     * @return
     */
    List<Log> list(Log log, Integer page, Integer pageSize, Sort.Direction direction, String...properties);

    /**
     * 获取日志总记录数
     * @param log
     * @return
     */
    Long getCount(Log log);
}
