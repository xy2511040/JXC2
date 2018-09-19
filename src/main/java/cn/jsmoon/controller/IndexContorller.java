package cn.jsmoon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请求首页Controller层
 * @author: LTQ
 * @create: 2018-08-21 10:58
 **/
@Controller
public class IndexContorller {

    @RequestMapping("/")
    public String root(){
        return "redirect:/login.html";
    }
}
