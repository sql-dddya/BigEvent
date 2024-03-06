第一版本：SpringBoot + mybatis
第二版本：SpringBoot + mybatisPlus[简化单表查询 + 优化分页查询]
按照黑马的教程，基本完成了大事件后端的开发，除了文件上传到阿里云没有实现。
收获：
1. 了解了spring Boot的注解 
 参数的注解：@RequestBody是将前端传过来的json数据封装成实体类对象；@RequestParam 是解析前端传过来的jqueryString 数据，还可以使用（require = false）不强制传递，在分页搜索查询时使用到；@RequestHeader("authorization") 获取请求头中某属性的值；
 请求方式的注解：@PostMapping; @PutMapping @DeleteMapping @GetMapping @PatchMapping; 可以根据请求方式区分，不用采用请求路径；
 实体类上注解：@NotNULL @NotEmpty @Email @URL @Pattern("^.{1,16}$"):正则表达式进行匹配；@Ignore 返回前端页面时忽略此属性；必须在校验参数前添加@Validated;
 自定义注解：创建注解（画瓢），创建校验规则类 继承一个类 重写isValid方法；
 分组校验：group指定校验属于那个组，默认default，组之间可继承；
 测试类上的注解：@SpringTest：在单元测试启动前，会先初始化Spring的容器。
 mybaatis上的一些注解：@NoArgsConstructor、@AllArgsConstructor、@Data；无参、全参构造，get set方法；
3. token JWT令牌
  用户需要凭证，由权限才可以访问其他接口。所以在登陆时服务器生成token返回给浏览器，在访问其他接口时需要先验证token。
  生成令牌和解析令牌； token分为三段：加密算法.用户信息.签名值；
  用户登陆后，都要登陆用户的信息，如显示昵称、头像、信息等，所以要能够方便的获取到登陆用户的信息。
  请求拦截器:
    每次访问接口都需要解析token，所以在请求拦截器上进行解析token，只有验证通过才放行，继续下面任何一个接口的访问。排除注册和登录接口。
  ThreadLocal
    线程局部变量 而且 线程安全的；
    在请求拦截器解析token完成后，将用户信息存入ThreadLocal中，其他接口从ThreadLocal获取用户信息。防止内存泄漏，在afterHandle方法中，清除数据。
   redis token失效：
     登陆时：token返回浏览器 并 存入redis 【token，token】
     修改密码：删除redis中的token
     请求拦截器：得到浏览器的token，同时根据token，获取redis的值，若获取不到证明token已失效。
     涉及，redis安装包的安装：解压，编译测试，安装，启动。引入依赖，配置文件配置，默认端口号：6379。
4. mybatis
  下划线命名和驼峰命名的自动转换；配置文件中配置
5. 全局异常处理器
   使用到注解：@RestControllerAdvice 可以全局捕获指定异常，进行处理。 指定异常用注解：@ExceptionHandler(Exception.class)；
6. Result类
   属性；code msg data; 定义 success 和 erro 的方法
7. 项目部署
   环境配置优先级
   多环境开发profile：单文件；多文件；分组；
   
 
