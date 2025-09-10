package chap08;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		
		MemberDao dao = ctx.getBean(MemberDao.class);
//		Member member = new Member (null,"hong3@korea.com","1234","홍다철",LocalDateTime.now());
//		dao.insert2(member);
//		System.out.println(member.getId());
		
		Member member = dao.selectByEmail2("hong3@korea.com");
		System.out.println(member);
		
		int a = dao.count();
		System.out.println(a);
		
		member = dao.selectByEmail1("hong3@korea.com");
		System.out.println(member);
		
		System.out.println(dao.selectAll());

	}

}
