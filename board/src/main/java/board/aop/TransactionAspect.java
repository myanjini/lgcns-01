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
    
    // 트랜잭션 관리자
    @Autowired
    private PlatformTransactionManager tractionManger;
    
    // 트랜잭션 인터셉터 정의
    // 트랜잭션 관리자를 사용해서 트랜잭션 시작, 커밋, 롤백 등의 처리를 수행
    @Bean
    TransactionInterceptor transactionAdvice() {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(tractionManger);
        
        // 모든 메서드에 동일한 트랜잭션 속성을 적용
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        
        // 트랜잭션 속성을 정의 -> 트랜잭션 이름, 롤백 규칙
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName("*");
        transactionAttribute.setRollbackRules(Arrays.asList(new RollbackRuleAttribute(Exception.class)));
        source.setTransactionAttribute(transactionAttribute);
        
        transactionInterceptor.setTransactionAttributeSource(source);
        
        return transactionInterceptor;
    }
    
    // AOP 포이트컷과 어드바이저 설정
    @Bean
    Advisor transactionAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* board..service.*Impl.*(..))");
        
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }
}
