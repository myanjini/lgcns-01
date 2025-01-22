package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import aop.CacheAspect;
import aop.ExeTimeAspect;
import ex04.Calculator;
import ex04.ImpCalculator;
import ex04.RecCalculator;

@Configuration
@EnableAspectJAutoProxy
public class AppCtxAspect {
    @Bean
    public CacheAspect cacheAspect() {
        return new CacheAspect();
    }
    
    @Bean
    public ExeTimeAspect exeTimeAspect() {
        return new ExeTimeAspect();
    }
    
    @Bean
    public Calculator recCalculator() {
        return new RecCalculator();
    }

    @Bean
    public Calculator impCalculator() {
        return new ImpCalculator();
    }
}
