package cn.jsmoon.service.impl;

import cn.jsmoon.entity.Log;
import cn.jsmoon.repository.LogRepository;
import cn.jsmoon.repository.UserRepository;
import cn.jsmoon.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 日志service实现类
 * @author: LTQ
 * @create: 2018-10-05 09:59
 **/
@Service("logService")
public class LogServiceImpl implements LogService{

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
}
