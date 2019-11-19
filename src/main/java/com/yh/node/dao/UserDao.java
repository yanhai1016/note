package com.yh.node.dao;

import com.yh.node.entity.User;

public interface UserDao {
    void add(User user);
    void update(User user);
    User findByName(String name);
    User findByNickname(String nickname);
    User findById(String id);
}
