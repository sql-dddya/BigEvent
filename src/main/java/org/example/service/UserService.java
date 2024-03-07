package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.User;

public interface UserService extends IService<User>{
    User findByName(String username);

//    void register(String username, String password);

//    void updateUserInfo(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(Integer id, String password);

}
