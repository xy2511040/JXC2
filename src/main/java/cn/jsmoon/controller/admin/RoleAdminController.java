package cn.jsmoon.controller.admin;

import cn.jsmoon.entity.Menu;
import cn.jsmoon.entity.Role;
import cn.jsmoon.entity.RoleMenu;
import cn.jsmoon.entity.UserRole;
import cn.jsmoon.service.MenuService;
import cn.jsmoon.service.RoleMenuService;
import cn.jsmoon.service.RoleService;
import cn.jsmoon.service.UserRoleService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 后台管理角色Controller类
 * @author: LTQ
 * @create: 2018-09-19 20:12
 **/
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private MenuService menuService;

    /**
     * 展示全部角色
     * @return
     * @throws Exception
     */
    @RequestMapping("/listAll")
    @RequiresPermissions(value = {"用户管理","角色管理"},logical = Logical.OR)
    public Map<String,Object> listAll()throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", roleService.listAll());
        return resultMap;
    }

    /**
     * 分页查询角色信息
     * @param role
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "角色管理")
    public Map<String, Object> list(Role role,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "rows", required = false) Integer rows) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<Role> roleList = roleService.list(role, page, rows, Sort.Direction.ASC, "id");
        Long total = roleService.getCount(role);
        resultMap.put("rows", roleList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改角色信息
     * @param role
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "角色管理")
    public Map<String, Object> save(Role role) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        if (role.getId() == null) {
            if (roleService.findByRoleName(role.getName()) != null) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "角色名已经存在！！");
                return resultMap;
            }
        }
        roleService.save(role);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 删除角色信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = "角色管理")
    public Map<String, Object> delete(Integer id) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        userRoleService.deleteByRoleId(id); //删除用户角色关联表信息
        roleMenuService.deleteByRoleId(id); //删除角色菜单关联表信息
        roleService.deleteById(id); //删除角色表信息
        resultMap.put("success",true);
        return resultMap;
    }

    /**
     * 加载当前选中角色的相对应的菜单权限
     * @param parentId
     * @param roleId
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadCheckMenuInfo")
    @RequiresPermissions(value = "角色管理")
    public String loadCheckMenuInfo(Integer parentId,Integer roleId)throws Exception{
        List<Menu> menuList = menuService.findByRoleId(roleId);
        List<Integer> menuIdList = new LinkedList<>();
        menuList.forEach(menu -> menuIdList.add(menu.getId()));
        return getALLCheckMenuByParentId(parentId,menuIdList).toString();
    }


    /**
     * 根据父节点id和权限菜单id集合获取所有复选框菜单集合
     * @param parentId
     * @param menuIdList
     * @return
     * @throws Exception
     */
    public JsonArray getALLCheckMenuByParentId(Integer parentId, List<Integer> menuIdList)throws Exception{
        JsonArray jsonArray = this.getCheckMenuByParentId(parentId, menuIdList);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            if ("open".equals(jsonObject.get("state").getAsString())) {
                continue;
            } else {
                jsonObject.add("children", getALLCheckMenuByParentId(jsonObject.get("id").getAsInt(), menuIdList));
            }
        }
        return jsonArray;
    }

    /**
     * 根据父节点id和权限菜单id集合获取一层复选框菜单集合
     * @param parentId
     * @param menuIdList
     * @return
     * @throws Exception
     */
    public JsonArray getCheckMenuByParentId(Integer parentId, List<Integer> menuIdList)throws Exception{
        JsonArray jsonArray = new JsonArray();
        List<Menu> menuList = menuService.findByParentId(parentId);
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
           if (menuIdList.contains(menu.getId())){
               jsonObject.addProperty("checked", true);
           }
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }

    /**
     * 保存角色菜单设置
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveMenuSet")
    @RequiresPermissions(value = "角色管理")
    public Map<String,Object> saveMenuSet(String menuIds, Integer roleId)throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        roleMenuService.deleteByRoleId(roleId); //删除角色菜单关联表信息
        String menuIdStr[] = menuIds.split(",");
        for (int i = 0; i < menuIdStr.length; i++) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRole(roleService.findById(roleId));
            roleMenu.setMenu(menuService.findById(Integer.parseInt(menuIdStr[i])));
            roleMenuService.save(roleMenu);
        }
        resultMap.put("success", true);
        return resultMap;
    }



}
