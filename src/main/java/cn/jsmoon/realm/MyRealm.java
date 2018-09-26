package cn.jsmoon.realm;

import cn.jsmoon.entity.Menu;
import cn.jsmoon.entity.Role;
import cn.jsmoon.entity.User;
import cn.jsmoon.repository.MenuRepository;
import cn.jsmoon.repository.RoleRepository;
import cn.jsmoon.repository.UserRepository;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Shiro自定义的MyRealm
 * @author: LTQ
 * @create: 2018-08-21 15:08
 **/
public class MyRealm extends AuthorizingRealm{

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private MenuRepository menuRepository;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userRepository.findByUserName(userName);
        Set<String> roles = new HashSet<>();
        List<Role> roleList = roleRepository.findByUserId(user.getId());
        roleList.forEach(role -> {
            roles.add(role.getName());
            List<Menu> menuList = menuRepository.findByRoleId(role.getId());
            menuList.forEach(menu -> info.addStringPermission(menu.getName()));
        });
        info.setRoles(roles);
        return info;
    }

    /**
     * 登陆权限认证
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
