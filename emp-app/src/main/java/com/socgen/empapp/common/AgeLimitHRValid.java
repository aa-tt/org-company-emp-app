package com.socgen.empapp.common;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, CONSTRUCTOR })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { EmpAppCustomValidators.AgeLimitHRValidator.class })
public @interface AgeLimitHRValid {

	String message() default "{com.socgen.empapp.common.AgeLimitHRValid.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
