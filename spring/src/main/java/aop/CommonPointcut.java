package aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {
    @Pointcut("execution(public * ex04..*(..))")
    public void commonTarget() {
        
    }
}
