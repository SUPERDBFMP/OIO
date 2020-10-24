package indi.dbfmp.oio.oauth.core.annotaion;

import indi.dbfmp.oio.oauth.core.enums.WrapperTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *  条件组装注解
 * </p>
 *
 * @author dbfmp
 * @name: WrapperCondtion
 * @since 2020/10/24 1:11 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WrapperCondition {

    String columnName() default "";

    WrapperTypes wrapperType() default WrapperTypes.EQ;

    WrapperTypes orderByType() default WrapperTypes.DESC;

    boolean groupByColumn() default false;

    boolean orderByColumn() default false;



}
