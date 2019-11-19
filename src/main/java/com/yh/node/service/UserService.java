package com.yh.node.service;

import com.yh.node.dao.UserDao;
import com.yh.node.entity.User;
import com.yh.node.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    public Map<String,Object> login(String name,String password){
        Map<String,Object> result=new HashMap<String,Object>();
        if (name==null||name.trim().length()==0){
            result.put("success",false);
            result.put("name_null",true);
            return result;
        }
        if (password==null||password.trim().length()==0){
            result.put("success",false);
            result.put("password_null",true);
            return result;
        }
        User user=dao.findByName(name);
        password=Md5Util.md5(password);
        password=Md5Util.md5(password);
        if (user==null||!user.getPassword().equals(password)){
            result.put("success",false);
            result.put("name_password_error",true);
            return result;
        }
        result.put("success",true);
        result.put("user",user);
        return result;
    }

    public Map<String,Object> register(String name,String nickname,String password){
        Map<String,Object> result=new HashMap<String,Object>();
        if (name==null||name.trim().length()==0){
            result.put("success",false);
            result.put("name_null",true);
            return result;
        }
        if (nickname==null||nickname.trim().length()==0){
            result.put("success",false);
            result.put("nickname_null",true);
            return result;
        }
        if (password==null||password.trim().length()==0){
            result.put("success",false);
            result.put("password_null",true);
            return result;
        }
        User user=dao.findByName(name);
        if (user!=null){
            result.put("success",false);
            result.put("name_repeat",true);
            return result;
        }
        User u=new User();
        u.setId(UUID.randomUUID().toString());
        u.setName(name);
        u.setNickname(nickname);
        password=Md5Util.md5(password);
        password=Md5Util.md5(password);
        u.setPassword(password);
        dao.add(u);
        result.put("success",true);
        return result;
    }

    public void changePassword(String userId,String newPassword){
        newPassword=Md5Util.md5(newPassword);
        newPassword=Md5Util.md5(newPassword);
        User user=new User();
        user.setId(userId);
        user.setPassword(newPassword);
        dao.update(user);
    }
}
