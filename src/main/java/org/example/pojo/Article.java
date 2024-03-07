package org.example.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.example.anno.State;
import org.hibernate.validator.constraints.URL;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
@Data
public class Article{
    @TableId(type = IdType.AUTO)
    private Integer id;//主键ID
    @NotEmpty
    @Pattern(regexp = "^.{1,16}$")
    private String title;//文章标题
    @NotEmpty
    private String content;//文章内容
    @NotEmpty
    @URL
    private String coverImg;//封面图像
    @NotEmpty
//    @Pattern(regexp = "^(已发布|草稿)$")
    @State
    private String state;//发布状态 已发布|草稿
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
    @TableLogic
    private Integer flag; // 是否被删除：1被删除；0未被删除
}
