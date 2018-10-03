package com.socgen.empapp.common;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolationException;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import org.springframework.beans.factory.annotation.Value;

public class EmpAppCustomValidators {

	@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
	public static class AgeLimitValidator implements ConstraintValidator<AgeLimitValid, String> {
		
		@Value("${employee.ageLimit}")
		private int defaultMinLimit;
		
		private int minAgeLimit;
		
		@Override
		public void initialize(AgeLimitValid minAgeLimit) {
			this.minAgeLimit = defaultMinLimit;			
		}
		
		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			if (Integer.parseInt(value) < minAgeLimit)
				return false;
			return true;
		}		
	}
	
	@SupportedValidationTarget(ValidationTarget.PARAMETERS)
	public static class AgeLimitHRValidator implements ConstraintValidator<AgeLimitHRValid, Object[]> {
		
		@Value("${hr.ageLimit}")
		private int defaultMinLimit;
		
		private int minAgeLimit;
		
		@Override
		public void initialize(AgeLimitHRValid minAgeLimit) {
			this.minAgeLimit = defaultMinLimit;			
		}
		
		@Override
		public boolean isValid(Object[] value, ConstraintValidatorContext context) {
			if(value == null || value[0] == null || value[1] == null)
				return true;
			if ("HR".equals((String)value[0]) && (Integer)value[1] < minAgeLimit) {
				throw new ConstraintViolationException(context.getDefaultConstraintMessageTemplate(), null);
				//return false;
			}
			return true;
		}		
	}

	// add future validation classes here
}
