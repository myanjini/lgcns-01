package board.aop;

import java.util.Arrays;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {
    
    @Autowired
    private PlatformTransactionManager tractionManger;
    
    @Bean
    TransactionInterceptor transactionAdvice() {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(tractionManger);
        
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName("*");
        transactionAttribute.setRollbackRules(Arrays.asList(new RollbackRuleAttribute(Exception.class)));
        source.setTransactionAttribute(transactionAttribute);
        
        transactionInterceptor.setTransactionAttributeSource(source);
        
        return transactionInterceptor;
    }
    
    @Bean
    Advisor transactionAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* board..service.*Impl.*(..))");
        
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }
}
