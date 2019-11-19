package com.yh.node.web;

import com.yh.node.entity.User;
import com.yh.node.service.UserService;
import com.yh.node.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("/login")
    public Object login(String name, String password, HttpSession session){
        Map<String, Object> result = service.login(name, password);
        session.setAttribute("user",result.get("user"));
        return result;
    }

    @RequestMapping("/reg")
    public Object register(String name,String nickname, String password){
        Map<String, Object> result = service.register(name,nickname, password);
        return result;
    }

    @RequestMapping("/changePassword")
    public Object changePassword(String userId,String lastPassword,String newPassword,HttpSession session){
        Map<String, Object> result=new HashMap<String,Object>();
        User user = (User) session.getAttribute("user");
        lastPassword= Md5Util.md5(lastPassword);
        lastPassword= Md5Util.md5(lastPassword);
        if (user==null||!user.getId().equals(userId)||!user.getPassword().equals(lastPassword)){
            result.put("success",false);
            result.put("authority",false);
        }else if(!user.getPassword().equals(lastPassword)){
            result.put("success",false);
            result.put("last_password_error",true);
        }else {
            service.changePassword(userId,newPassword);
            result.put("success",true);
        }
        return result;
    }
}
