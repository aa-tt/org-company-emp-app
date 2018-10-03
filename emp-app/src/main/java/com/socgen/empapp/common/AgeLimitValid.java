package com.socgen.empapp.common;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { EmpAppCustomValidators.AgeLimitValidator.class })
public @interface AgeLimitValid {

	String message() default "{com.socgen.empapp.common.AgeLimitValid.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
