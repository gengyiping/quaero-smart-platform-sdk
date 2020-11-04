package com.quaero.quaerosmartplatform.annotations;

import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.AsyncConfigurationSelector;

import java.lang.annotation.*;

/**
 * @desc 日志打印
 *
 * @author wuhanzhang
 * @since 2020/11/4
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AsyncConfigurationSelector.class})
public @interface EasyLog {

    /**
     * 是否关闭日志输出功能
     */
    boolean enable() default true;

    /**
     * 是否输出执行时间
     */
    boolean duration() default true;

    /**
     * 是否只在debug输出日志
     */
    boolean response() default true;
}
