package cn.jsmoon.service;

import cn.jsmoon.entity.Log;

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
}
