package ex00;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        Greeter g1 = new Greeter();
        Greeter g2 = new Greeter();
        
        System.out.println("g1 : " + g1);
        System.out.println("g2 : " + g2);
        System.out.println("g1 == g2 : " + (g1 == g2));
        
        
        
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);

        Greeter g3 = ctx.getBean("greeter", Greeter.class);
        Greeter g4 = ctx.getBean("greeter", Greeter.class);
        
        System.out.println("g3 : " + g3);
        System.out.println("g4 : " + g4);
        System.out.println("g3 == g4 : " + (g3 == g4));

        ctx.close();

        
        String s1 = "같아요";
        String s2 = "같아요";
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }
}
