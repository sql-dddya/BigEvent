package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.User;

@Mapper
public interface UserMapper {

    User findByname(String username);

    void register(String username, String password);

    void updateUserInfo(User user);

    void updateAvatar(String avatarUrl, Integer id);

    void updatePwd(Integer id, String password);
}
