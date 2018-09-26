package cn.jsmoon.controller.admin;

import cn.jsmoon.service.RoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: LTQ
 * @create: 2018-09-19 20:12
 **/
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {

    @Resource
    private RoleService roleService;

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



}
