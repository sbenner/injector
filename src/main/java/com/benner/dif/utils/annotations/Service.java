package com.benner.dif.utils.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/10/17
 * Time: 11:36 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in method only.
public @interface Service {
    public String name() default "";
    public boolean set() default false;

}