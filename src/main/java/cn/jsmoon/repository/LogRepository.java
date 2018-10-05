package cn.jsmoon.repository;

import cn.jsmoon.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 日志Repository接口
 * @author: LTQ
 * @create: 2018-10-05 09:54
 **/
public interface LogRepository extends JpaRepository<Log,Integer>,JpaSpecificationExecutor<Log> {

}
