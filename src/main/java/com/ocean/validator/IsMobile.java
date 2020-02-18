package com.ocean.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {MobileValidator.class}
)
public @interface IsMobile {
    boolean required() default true;

    String message() default "手机格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
