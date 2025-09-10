package aop1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SimpleLoggingAspect {

	@Pointcut("execution(* aop1.SimpleService.doSomething(..))")
	public void aaa() {}
	
	@Before("aaa()")
	public void before() {
		System.out.println("[AOP] 메서드 실행 전: Before advice");
	}
	
	@After("aaa()")
	public void after() {
		System.out.println("[AOP] 메서드 실행 후: After advice");
	}
	
	@Around("execution(* aop1.SimpleService.doSomething(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
		long start = System.nanoTime();
		
		System.out.println("[AOP] 메서드 실행 전");
		
		Object result = joinPoint.proceed();
		
		System.out.println("[AOP] 메서드 실행 후");
		
		long end = System.nanoTime();
		long duration = end- start;
		
		System.out.printf("[AOP] 실행 시간: %,d ns\n",duration);
		
		return result;
	}
}
