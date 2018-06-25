package com.tt.ssm.web;

import com.tt.ssm.entity.User;
import com.tt.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 用户控制器类
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    private String userLogin(@RequestParam("usercode")String usercode,@RequestParam("password")String password, Model model, HttpSession session){
        User user = userService.findUser(usercode,password);
        if(user!=null){
            session.setAttribute("USER_SESSION",user);
//            return "customer"; //转发
            return "redirect:/customer/list"; //重定向
        }
        model.addAttribute("msg","账号或密码错误,请重新输入！");
        //返回到登录界面
        return "login";
    }

    @RequestMapping(value = "/logout")
    private String userLogout(HttpSession session){
        session.invalidate();
        return "login";
    }
}