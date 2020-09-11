package com.socgen.empapp.common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SLF4JLogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.logging.Slf4JLoggingSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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
public class EmpAppExceptionLoggerAndResponseEnhancerAspect {

	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public Log log = new SLF4JLogFactory().getInstance("EmpAppExceptionLoggerAndResponseEnhancerAspect");

	@Pointcut("execution(* com.socgen.empapp.controller.*.*(..))")
	public void anyCntlrOprn() {
	}

	@Pointcut("execution(* com.socgen.empapp.business.*.*(..))")
	public void anyBussOprn() {
	}

	@Pointcut("anyBussOprn() || anyCntlrOprn()")
	public void anyOprn() {
	}

	@AfterThrowing(pointcut = "anyOprn()", throwing = "e")
	public void logAfterThrowingAllMethods(JoinPoint joinPoint, Exception e) {
		log.error(joinPoint.getTarget().getClass().getSimpleName() + " >> " + joinPoint.getSignature().getName() +" >> erroneously ends with " + joinPoint.getArgs(), e);
	}

	@Before("anyOprn()")
	public void beforeAdvice(JoinPoint joinPoint) {
		startTime = LocalDateTime.now();
		log.info(joinPoint.getTarget().getClass().getSimpleName() + " >> " + joinPoint.getSignature().getName() + " >> starts with " + joinPoint.getArgs());
	}

	@After("anyOprn()")
	public void afterAdvice(JoinPoint joinPoint) {
		endTime = LocalDateTime.now();
		log.info(joinPoint.getTarget().getClass().getSimpleName() + " >> "+joinPoint.getSignature().getName()+" >> ends");
	}

	@SuppressWarnings("rawtypes")
	@Around("anyCntlrOprn()")
	public ResponseEntity<?> afterReturnAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		endTime = LocalDateTime.now();
		log.info(joinPoint.getTarget().getClass().getSimpleName() + " >> "+joinPoint.getSignature().getName()+" >> returns");
		Object response = null;
		try {
			response = joinPoint.proceed();
		} catch (Throwable e) {
			throw e;
		}

		if (response instanceof ResponseEntity) {
			EmpAppCustomResponseEntity appResponseEntity = new EmpAppCustomResponseEntity();
			
			appResponseEntity.data = ((ResponseEntity) response).getBody(); // 1. set data
			
			ResponseMeta responseMeta = appResponseEntity.meta;
			if (null == responseMeta) {
				responseMeta = new ResponseMeta();
				responseMeta.statusMessage = AppConstants.SUCCESS;
			}
			Object data = appResponseEntity.data;
			if (data instanceof List) {
				responseMeta.total = ((List<?>)data).size();
			} else {
				responseMeta.total = 1;
			}
			responseMeta.responseTime = ChronoUnit.MILLIS.between(startTime, endTime);
			responseMeta.timestamp = startTime;
			appResponseEntity.meta = responseMeta; // 2. set meta

			response = ResponseEntity.status(((ResponseEntity<?>) response).getStatusCode()).body(appResponseEntity);
		}
		return (ResponseEntity<?>) response;
	}

}
