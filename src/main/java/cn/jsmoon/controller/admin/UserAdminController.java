package cn.jsmoon.controller.admin;

import cn.jsmoon.entity.Role;
import cn.jsmoon.entity.User;
import cn.jsmoon.service.RoleService;
import cn.jsmoon.service.UserRoleService;
import cn.jsmoon.service.UserService;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理用户Controller类
 *
 * @author: LTQ
 * @create: 2018-09-15 02:56
 **/
@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 分页查询用户信息
     * @param user
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public Map<String, Object> list(User user,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<User> userList = userService.list(user, page, rows, Direction.ASC, "id");
        userList.forEach(u -> {
            List<Role> roleList = roleService.fingByUserId(u.getId());
            StringBuffer sb = new StringBuffer();
            roleList.forEach(r -> sb.append("," + r.getName()));
            u.setRoles(sb.toString().replaceFirst(",", ""));
        });
        Long total = userService.getCount(user);
        resultMap.put("rows", userList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改用户信息
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public Map<String, Object> save(User user) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        if (user.getId() == null) {
            if (userService.findByUserName(user.getUserName()) != null) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "用户名已经存在！！");
                return resultMap;
            }
        }
        userService.save(user);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(Integer id) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        userRoleService.deleteByUserId(id); //删除用户角色关联表信息
        userService.deleteById(id); //删除用户表信息
        resultMap.put("success",true);
        return resultMap;
    }

}