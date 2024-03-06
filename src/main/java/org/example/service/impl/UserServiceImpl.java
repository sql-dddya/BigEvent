package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByName(String username) {
        User user = userMapper.findByname(username);
        return user;
    }


//    @Override
//    public void register(String username, String password) {
//    }

//    @Override
//    public void updateUserInfo(User user) {
//        user.setUpdateTime(now());
//
//        userMapper.updateUserInfo(user);
//    }

/*    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);

    }*/

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getUserPic,avatarUrl)
                .set(User::getUpdateTime,now())
                .eq(User::getId, id);

        userMapper.update(null, wrapper);    // wrapper只是封装更新条件，这个命令才是执行。
    }

    @Override
    public void updatePwd(Integer id, String password) {
//        userMapper.updatePwd(id, Md5Util.getMD5String(password));
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getPassword, Md5Util.getMD5String(password))
                .eq(User::getId, id);

        userMapper.update(null, wrapper);
    }
}
