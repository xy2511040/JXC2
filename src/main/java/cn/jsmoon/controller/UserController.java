package cn.jsmoon.controller;

import cn.jsmoon.entity.Menu;
import cn.jsmoon.entity.Role;
import cn.jsmoon.entity.User;
import cn.jsmoon.service.MenuService;
import cn.jsmoon.service.RoleService;
import cn.jsmoon.service.UserService;
import cn.jsmoon.util.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller层
 *
 * @author: LTQ
 * @create: 2018-08-21 10:49
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    /**
     * 用户登录判断
     *
     * @param imageCode
     * @param user
     * @param bindingResult
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(String imageCode, @Valid User user, BindingResult bindingResult, HttpSession session) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isEmpty(imageCode)) {
            map.put("success", false);
            map.put("errorInfo", "请输入验证码！");
            return map;
        }
        if (!session.getAttribute("checkcode").equals(imageCode)) {
            map.put("success", false);
            map.put("errorInfo", "验证码输入错误，请重新输入！");
            return map;
        }
        if (bindingResult.hasErrors()) {
            map.put("success", false);
            map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
            return map;
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            subject.login(token);
            String userName = (String) SecurityUtils.getSubject().getPrincipal();
            User currentUser = userService.findByUserName(userName);
            session.setAttribute("currentUser", currentUser);
            List<Role> roleList = roleService.findByUserId(currentUser.getId());
            map.put("roleList", roleList);
            map.put("roleSize", roleList.size());
            map.put("success", true);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("errorInfo", "用户名或者密码错误！");
            return map;
        }

    }

    /**
     * 保存角色信息
     *
     * @param roleId
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/saveRole")
    public Map<String, Object> saveRole(Integer roleId, HttpSession session) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Role currentRole = roleService.findById(roleId);
        session.setAttribute("currentRole", currentRole);
        map.put("success", true);
        return map;
    }

    /**
     * 加载当前登录用户信息
     *
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/loadUserInfo")
    public String loadUserInfo(HttpSession session) throws Exception {
        User currentUser = (User) session.getAttribute("currentUser");
        Role currentRole = (Role) session.getAttribute("currentRole");
        return "欢迎您：" + currentUser.getTrueName() + "&nbsp;【&nbsp;" + currentRole.getName() + "&nbsp;】";
    }

    /**
     * 加载当前登录角色相对应的菜单
     *
     * @param session
     * @param parentId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/loadMenuInfo")
    public String loadMenuInfo(HttpSession session, Integer parentId) throws Exception {
        Role currentRole = (Role) session.getAttribute("currentRole");
        return getALLMenuByParentId(parentId,currentRole.getId()).toString();
    }

    /**
     * 通过父节点Id和用户角色Id查询所有菜单
     *
     * @param parentId
     * @param roleId
     * @return
     * @throws Exception
     */
    public JsonArray getALLMenuByParentId(Integer parentId, Integer roleId) throws Exception {
        JsonArray jsonArray = this.getMenuByParentId(parentId, roleId);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            if ("open".equals(jsonObject.get("state").getAsString())) {
                continue;
            } else {
                jsonObject.add("children", getALLMenuByParentId(jsonObject.get("id").getAsInt(), roleId));
            }
        }
        return jsonArray;
    }

    /**
     * 通过父节点Id和用户角色Id查询菜单
     *
     * @param parentId
     * @param roleId
     * @return
     * @throws Exception
     */
    public JsonArray getMenuByParentId(Integer parentId, Integer roleId) throws Exception {
        JsonArray jsonArray = new JsonArray();
        List<Menu> menuList = menuService.findByParentIdAndRoleId(parentId, roleId);
        menuList.forEach(menu -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", menu.getId());      //菜单节点Id
            jsonObject.addProperty("text", menu.getName());  //菜单节点名称
            if (menu.getState() == 1) {
                jsonObject.addProperty("state", "close");    //根节点
            } else {
                jsonObject.addProperty("state", "open");     //叶子节点
            }
            jsonObject.addProperty("iconCls", menu.getIcon());     //节点图标
            JsonObject attributeObject = new JsonObject();                   //扩展属性
            attributeObject.addProperty("url", menu.getUrl());     //菜单请求地址
            jsonObject.add("attributes", attributeObject);
            jsonArray.add(jsonObject);
        });
        return jsonArray;
        /*for (Menu menu : menuList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", menu.getId());      //菜单节点Id
            jsonObject.addProperty("text", menu.getName());  //菜单节点名称
            if (menu.getState() == 1) {
                jsonObject.addProperty("state", "close");    //根节点
            } else {
                jsonObject.addProperty("state", "open");     //叶子节点
            }
            jsonObject.addProperty("iconCls", menu.getIcon());     //节点图标
            JsonObject attributeObject = new JsonObject();                   //扩展属性
            attributeObject.addProperty("url", menu.getUrl());     //菜单请求地址
            jsonObject.add("attributes", attributeObject);
            jsonArray.add(jsonObject);
        }*/
    }


}
