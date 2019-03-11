package com.shorturl.config;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class LoggingConfig {
	
	@Before("execution(* com.shorturl.services.*.*(..)) "
			+ " || execution(* com.shorturl.controller.*.*(..)) "
			+ " || execution(* com.shorturl.exception.*.*(..)) "
			+ " || execution(* com.shorturl.repository.*.*(..))")
	public void beforeAll(JoinPoint joinPoint) {
		log.debug("{} {}", joinPoint.getSignature(), "START");
	}
	
	@Before("execution(* com.shorturl.services.*.*(..)) "
			+ " || execution(* com.shorturl.controller.*.*(..)) "
			+ " || execution(* com.shorturl.exception.*.*(..)) "
			+ " || execution(* com.shorturl.repository.*.*(..))")
	public void afterAll(JoinPoint joinPoint) {
		log.debug("{} {}", joinPoint.getSignature(), "END");
	}

}
