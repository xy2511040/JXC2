package cn.jsmoon.repository;

import cn.jsmoon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户Repository接口
 * @author: LTQ
 * @create: 2018-08-21 15:08
 **/
public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User>{


    /**
     * 通过用户名查找用户实体
     * @param userName
     * @return
     */
    @Query(value = "select * from t_user where user_name=?1",nativeQuery = true)
    User findByUserName(String userName);


}
