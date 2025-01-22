package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import config.AppCtxAspect;
import ex04.Calculator;

public class MainForAspect {
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtxAspect.class);
        
        Calculator rec = ctx.getBean("recCalculator", Calculator.class);
        System.out.println(rec.factorial(10));
        System.out.println(rec.factorial(10));
        System.out.println(rec.factorial(7));
        System.out.println(rec.factorial(7));
        System.out.println(rec.factorial(5));
        
        ctx.close();
    }
}
