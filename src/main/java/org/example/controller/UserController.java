package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.validation.constraints.Pattern;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.JwtUtil;
import org.example.util.Md5Util;
import org.example.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^.{5,16}$") String username, @Pattern(regexp = "^.{5,16}$") String password){
        // 查看当前用户是否存在
        User u = userService.findByName(username);
        if(u != null){
            return Result.error("当前用户已存在");
        }

//        userService.register(username, password);

        // 注册
        String Md5Password = Md5Util.getMD5String(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(Md5Password);
        user.setCreateTime(now());
        user.setUpdateTime(now());
        userService.save(user);

        return Result.success();
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^.{5,16}$") String username, @Pattern(regexp = "^.{5,16}$") String password){
        // 根据用户名找到用户
        User user = userService.findByName(username);
        if(user == null){
            return Result.error("该用户不存在");
        }

        // 判断密码是否正确
        if(!user.getPassword().equals(Md5Util.getMD5String(password))){
            return Result.error("密码错误");
        }

        // 成功登陆
        Map<String, Object> claims = new HashMap<>();
        Integer id = userService.findByName(username).getId();
        claims.put("id", id);
        claims.put("username", username);
        String token = JwtUtil.genToken(claims);

        // 将token存入redis
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.set(token,token,1, TimeUnit.HOURS);

        // 将token返回给浏览器
        return Result.success(token);
    }

    /**
     * 获取用户详细信息
     * @return
     */
    @RequestMapping("/userInfo")
    public Result<User> getUserInfo(/*@RequestHeader(name = "Authorization") String token*/){
        /*// 从token中获取用户名
        String username = (String) JwtUtil.parseToken(token).get("username");
        System.out.println(username);*/

        // 从ThreadLocal中获取用户名
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        // 根据用户名查询用户
        User user = userService.findByName(username);
        return Result.success(user);
    }

    /**
     * 更新用户基本信息
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody @Validated User user){
//        userService.updateUserInfo(user);
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 更新用户头像
     * @param avatarUrl
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){
/*          // 方法一
        userService.updateAvatar(avatarUrl);*/

        // 方法二：从ThreadLocal 中获取用户id, 使用条件构造器
        userService.updateAvatar(avatarUrl);

        return Result.success();
    }

    /**
     * 更新用户密码
     * @param params oldPwd、newPwd、rePwd
     * @return
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,Object> params, @RequestHeader("Authorization") String token){
        // 参数校验
        String oldPwd = (String) params.get("old_pwd");
        String newPwd = (String) params.get("new_pwd");
        String rePwd = (String) params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }
        if(!newPwd.matches("^.{5,16}$")){
            return Result.error("新密码不符合5，16位非空字符串的要求");
        }

        // 判断原密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByName(username);
        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码输入错误");
        }

        // 判断新密码和确认密码是否一致
        if(!newPwd.equals(rePwd)){
            return Result.error("两次密码输入不一致");
        }

        // 更新密码
        userService.updatePwd(user.getId(), newPwd);

        // 删除redis中的数据
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }

}
