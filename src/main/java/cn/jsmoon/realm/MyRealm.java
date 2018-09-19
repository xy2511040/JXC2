package cn.jsmoon.realm;

import cn.jsmoon.entity.User;
import cn.jsmoon.repository.UserRepository;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Shiro自定义的MyRealm
 * @author: LTQ
 * @create: 2018-08-21 15:08
 **/
public class MyRealm extends AuthorizingRealm{

    @Resource
    private UserRepository userRepository;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 权限认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName= (String) token.getPrincipal();
        User user = userRepository.findByUserName(userName);
        if(user!=null){
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xxx");
            return authcInfo;
        }else {
            return null;
        }

    }
}
