package com.quick.text.Test4Excel.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCell {
    /**
     * 顺序 default 100
     * 
     * @return
     */
    int index();

    /**
     * 当值为null时要显示的值 default StringUtils.EMPTY
     * 
     * @return
     */
    String defaultValue() default StringUtils.EMPTY;

    /**
     * 用于验证
     * 
     * @return
     */
    Valid valid() default @Valid();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Valid {
        /**
         * 必须与in中String相符,目前仅支持String类型
         * 
         * @return e.g. {"abc","123"}
         */
        String[] in() default {};

        /**
         * 是否允许为空,用于验证数据 default true
         * 
         * @return
         */
        boolean allowNull() default true;

        /**
         * Apply a "greater than" constraint to the named property , equivalent ">"
         * 
         * @return
         */
        double gt() default Double.NaN;

        /**
         * Apply a "less than" constraint to the named property , equivalent "<"
         * @return
         */
        double lt() default Double.NaN;

        /**
         * Apply a "greater than or equal" constraint to the named property , equivalent ">="
         * 
         * @return
         */
        double ge() default Double.NaN;

        /**
         * Apply a "less than or equal" constraint to the named property , equivalent "<="
         * 
         * @return
         */
        double le() default Double.NaN;
    }
}