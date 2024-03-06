package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByname(String username);

//    void register(String username, String password);

//    void updateUserInfo(User user);

//    void updateAvatar(String avatarUrl, Integer id);

//    void updatePwd(Integer id, String password);
}
