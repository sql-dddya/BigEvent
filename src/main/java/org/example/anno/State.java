package org.example.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validated.StateValidation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)  // 注解作用在属性上
@Retention(RetentionPolicy.RUNTIME) // 作用范围
@Constraint(validatedBy = {StateValidation.class})  // 校验规则绑定
public @interface State {
    String message() default "该属性只能是已发布或草稿"; // 错误提示信息

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
