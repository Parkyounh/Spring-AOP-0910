package deptDB;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
	
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		DeptDao deptdao= ctx.getBean(DeptDao.class);
		
		System.out.println(deptdao.select(10));
		System.out.println(deptdao.selectAll());
	}
}
