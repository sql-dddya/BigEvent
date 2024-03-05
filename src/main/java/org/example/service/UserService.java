package org.example.service;

import org.example.pojo.User;

public interface UserService {
    User findByName(String username);

    void register(String username, String password);

    void updateUserInfo(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(Integer id, String password);

}
