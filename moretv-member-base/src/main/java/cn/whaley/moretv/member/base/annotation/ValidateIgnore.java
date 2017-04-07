package cn.whaley.moretv.member.base.annotation;

import java.lang.annotation.*;

/**
 * 是否忽略BaseRequest中accountId的校验
 *
 * 在controller参数BaseRequest上声明此注解，表示accountId可以为空
 *
 * Created by Bob Jiang on 2017/4/6.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateIgnore {

}
