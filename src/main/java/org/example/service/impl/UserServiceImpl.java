package org.example.service.impl;

import lombok.val;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.Md5Util;
import org.example.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.Map;

import static java.time.LocalDateTime.now;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String username) {
        User user = userMapper.findByname(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        String Md5Password = Md5Util.getMD5String(password);
        userMapper.register(username, Md5Password);
    }

    @Override
    public void updateUserInfo(User user) {
        user.setUpdateTime(now());
        userMapper.updateUserInfo(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(Integer id, String password) {
        userMapper.updatePwd(id, Md5Util.getMD5String(password));
    }
}
