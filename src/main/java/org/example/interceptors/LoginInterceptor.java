package org.example.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.example.util.JwtUtil;
import org.example.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证令牌 确定是否放行
        String token = request.getHeader("Authorization");

        try {
            // 从redis中获取token
            ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            String s = operations.get(token);
            if(s == null){
                throw new RuntimeException();
            }

            // 解析token进行验证
            Map<String, Object> calims = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(calims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除ThreadLocal中存放的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
