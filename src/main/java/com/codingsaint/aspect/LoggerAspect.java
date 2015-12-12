package com.codingsaint.aspect;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
	private static final Logger logger = LoggerFactory
			.getLogger(LoggerAspect.class);

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
	public void controllerBean() {
	}

	@Pointcut("@annotation(org.springframework.stereotype.Service)")
	public void serviceBean() {
	}

	@Pointcut("@annotation(org.springframework.stereotype.Component)")
	public void componentBean() {
	}

	@Pointcut("@annotation(org.springframework.stereotype.Repository)")
	public void repositoryBean() {
	}

	@Pointcut("within(com.codingsaint..*)")
	public void methodPointcuts() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMapping() {
	}

	@Pointcut("execution(* com.codingsaint.*Controller.*(..))")
	public void methodPointcut() {
	}

	@Around("requestMapping() && methodPointcuts()")
	public Object aroundControllersMethod(ProceedingJoinPoint joinPoint)
			throws Throwable {
		logger.debug(" Started : {}", joinPoint.getSignature());
		Date start = new Date();
		Object result = joinPoint.proceed();
		Date end = new Date();
		logger.debug("Return of {} with {}", joinPoint.getSignature(),
				BeanUtils.describe(result));
		logger.debug("Time Taken by  {} : {} millisec",
				joinPoint.getSignature(), end.getTime() - start.getTime());
		return result;
	}

	@Around("serviceBean() && methodPointcuts()")
	public Object aroundServiceMethod(ProceedingJoinPoint joinPoint)
			throws Throwable {
		logger.debug(" Started : {}", joinPoint.getSignature());
		Date start = new Date();
		Object result = joinPoint.proceed();
		Date end = new Date();
		logger.debug("Return of {} with {}", joinPoint.getSignature(),
				BeanUtils.describe(result));
		logger.debug("Time Taken by  {} : {} millisec",
				joinPoint.getSignature(), end.getTime() - start.getTime());
		return result;
	}

	@Around("repositoryBean() && methodPointcuts()")
	public Object aroundRepositoryMethod(ProceedingJoinPoint joinPoint)
			throws Throwable {
		logger.debug(" Started : {}", joinPoint.getSignature());
		Date start = new Date();
		Object result = joinPoint.proceed();
		Date end = new Date();
		logger.debug("Return of {} with {}", joinPoint.getSignature(),
				BeanUtils.describe(result));
		logger.debug("Time Taken by  {} : {} millisec",
				joinPoint.getSignature(), end.getTime() - start.getTime());
		return result;

	}

	@Around("componentBean() && methodPointcuts()")
	public Object aroundRcomponentMethod(ProceedingJoinPoint joinPoint)
			throws Throwable {
		logger.debug(" Started : {}", joinPoint.getSignature());
		Date start = new Date();
		Object result = joinPoint.proceed();
		Date end = new Date();
		logger.debug("Return of {} with {}", joinPoint.getSignature(),
				BeanUtils.describe(result));
		logger.debug("Time Taken by  {} : {} millisec",
				joinPoint.getSignature(), end.getTime() - start.getTime());
		return result;

	}
}