package com.socgen.empapp.common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Aspect for advices to be executed for corresponding pointcuts for logging
 * messages - info, error
 * 
 * @author anunay
 */
// pointcuts for @Before, @After and @AfterThrowing advices ->
// "execution(* com.socgen.empapp.*.*.*(..))"
// for application level logging and exception handling.
//
// pointcut for @Around advice is only for package "*.controller", as it updates
// "metadata" to ResponseEntity - time, length, etc.

@Component
@Aspect
@Slf4j
@NoArgsConstructor
@Data
public class EmpAppExceptionLoggerAndResponseEnhancerAspect {

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@Pointcut("execution(* com.socgen.empapp.controller.*.*(..))")
	private void anyCntlrOprn() {
	}

	@Pointcut("execution(* com.socgen.empapp.business.*.*(..))")
	private void anyBussOprn() {
	}

	@Pointcut("anyBussOprn() || anyCntlrOprn()")
	private void anyOprn() {
	}

	@AfterThrowing(pointcut = "anyOprn()", throwing = "e")
	public void logAfterThrowingAllMethods(JoinPoint joinPoint, Exception e) {
		log.error("{} >> {} >> erroneously ends with {}", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName(), joinPoint.getArgs(), e);
	}

	@Before("anyOprn()")
	public void beforeAdvice(JoinPoint joinPoint) {
		startTime = LocalDateTime.now();
		log.info("{} >> {} >> starts with {}", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName(), joinPoint.getArgs());
	}

	@After("anyOprn()")
	public void afterAdvice(JoinPoint joinPoint) {
		endTime = LocalDateTime.now();
		log.info("{} >> {} >> ends", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName());
	}

	@SuppressWarnings("rawtypes")
	@Around("anyCntlrOprn()")
	public ResponseEntity<?> afterReturnAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		endTime = LocalDateTime.now();
		log.info("{} >> {} >> returns", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName());
		Object response = null;
		try {
			response = joinPoint.proceed();
		} catch (Throwable e) {
			throw e;
		}

		if (response instanceof ResponseEntity) {
			EmpAppCustomResponseEntity appResponseEntity = new EmpAppCustomResponseEntity();
			
			appResponseEntity.setData(((ResponseEntity) response).getBody()); // 1. set data
			
			ResponseMeta responseMeta = appResponseEntity.getMeta();
			if (null == responseMeta) {
				responseMeta = new ResponseMeta();
				responseMeta.setStatusMessage(AppConstants.SUCCESS);
			}
			Object data = appResponseEntity.getData();
			if (data instanceof List) {
				responseMeta.setTotal(((List<?>)data).size());
			} else {
				responseMeta.setTotal(1);
			}
			responseMeta.setResponseTime(ChronoUnit.MILLIS.between(startTime, endTime));
			responseMeta.setTimestamp(startTime);
			appResponseEntity.setMeta(responseMeta); // 2. set meta

			response = ResponseEntity.status(((ResponseEntity<?>) response).getStatusCode()).body(appResponseEntity);
		}
		return (ResponseEntity<?>) response;
	}

}
