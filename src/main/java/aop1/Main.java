package aop1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		
		SimpleService service = ctx.getBean(SimpleService.class);
		service.doSomething();
		
		ctx.close();
	}
}
