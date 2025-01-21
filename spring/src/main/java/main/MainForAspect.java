package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import config.AppCtxAspect;
import ex04.Calculator;

public class MainForAspect {
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtxAspect.class);
        
        Calculator imp = ctx.getBean("impCalculator", Calculator.class);
        System.out.println(imp.factorial(10));
        System.out.println(imp.getClass().getName());
        
        Calculator rec = ctx.getBean("recCalculator", Calculator.class);
        System.out.println(rec.factorial(10));
        System.out.println(rec.getClass().getName());
        
        ctx.close();
    }
}
