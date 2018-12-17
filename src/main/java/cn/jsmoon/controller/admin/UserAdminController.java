package cn.jsmoon.controller.admin;

import cn.jsmoon.entity.Log;
import cn.jsmoon.entity.Role;
import cn.jsmoon.entity.User;
import cn.jsmoon.entity.UserRole;
import cn.jsmoon.service.LogService;
import cn.jsmoon.service.RoleService;
import cn.jsmoon.service.UserRoleService;
import cn.jsmoon.service.UserService;
import cn.jsmoon.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理用户Controller类
 *
 * @author: LTQ
 * @create: 2018-09-15 02:56
 **/
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private LogService logService;

    /**
     * 分页查询用户信息
     * @param user
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions(value = "用户管理")
    public Map<String, Object> list(User user,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<User> userList = userService.list(user, page, rows, Direction.ASC, "id");
        userList.forEach(u -> {
            List<Role> roleList = roleService.findByUserId(u.getId());
            StringBuffer sb = new StringBuffer();
            roleList.forEach(role -> sb.append("," + role.getName()));
            u.setRoles(sb.toString().replaceFirst(",", ""));
        });
        Long total = userService.getCount(user);
        resultMap.put("rows", userList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION,"查询用户信息"));
        return resultMap;
    }

    /**
     * 添加或修改用户信息
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @ResponseBody
    @RequiresPermissions(value = "用户管理")
    public Map<String, Object> save(User user) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        if (user.getId() == null) {
            if (userService.findByUserName(user.getUserName()) != null) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "用户名已经存在！！");
                return resultMap;
            }
        }
        if(user.getId()!=null){
            logService.save(new Log(Log.UPDATE_ACTION,"更新用户信息"+user));
        }else{
            logService.save(new Log(Log.ADD_ACTION,"添加用户信息"+user));
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
    @ResponseBody
    @RequiresPermissions(value = "用户管理")
    public Map<String, Object> delete(Integer id) throws Exception {
        logService.save(new Log(Log.DELETE_ACTION,"删除用户信息"+userService.findById(id)));
        Map<String, Object> resultMap = new HashMap<>();
        userRoleService.deleteByUserId(id); //删除用户角色关联表信息
        userService.deleteById(id); //删除用户表信息
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 保存用户角色设置
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveRoleSet")
    @ResponseBody
    @RequiresPermissions(value = "用户管理")
    public Map<String,Object> save(String roleIds,Integer userId)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        userRoleService.deleteByUserId(userId);
        if(StringUtil.isNotEmpty(roleIds)){
            String roleIdStr[] = roleIds.split(",");
            for (int i = 0; i < roleIdStr.length; i++) {
                UserRole userRole = new UserRole();
                userRole.setUser(userService.findById(userId));
                userRole.setRole(roleService.findById(Integer.parseInt(roleIdStr[i])));
                userRoleService.save(userRole);
            }
        }
        resultMap.put("success", true);
        logService.save(new Log(Log.UPDATE_ACTION,"保存用户角色设置"));
        return resultMap;
    }

    /**
     * 修改密码
     * @param newPassword
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyPassword")
    @ResponseBody
    @RequiresPermissions(value="修改密码")
    public Map<String,Object> modifyPassword(String newPassword,HttpSession session)throws Exception{
        Map<String,Object> resultMap=new HashMap<>();
        User currentUser=(User) session.getAttribute("currentUser");
        User user=userService.findById(currentUser.getId());
        user.setPassword(newPassword);
        userService.save(user);
        resultMap.put("success", true);
        logService.save(new Log(Log.UPDATE_ACTION,"修改密码"));
        return resultMap;
    }

    /**
     * 安全退出
     * @return
     * @throws Exception
     */
    @GetMapping("/logout")
    @RequiresPermissions(value="安全退出")
    public String logout(HttpSession session)throws Exception{
        logService.save(new Log(Log.LOGOUT_ACTION,"用户注销"));
        SecurityUtils.getSubject().logout();
        return "redirect:/login.html";
    }



}
