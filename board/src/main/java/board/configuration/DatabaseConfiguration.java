package board.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
//    @Autowired
//    private ApplicationContext applicationContext;
    
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    HikariConfig hikariConfig() {
        return new HikariConfig();
    }
    
    @Bean
    DataSource dataSource() {
        DataSource dataSource = new HikariDataSource(hikariConfig());
        System.out.println(dataSource);        
        return dataSource;
    }
    
//    // SqlSessionFactory: SqlSession 객체를 생성하는 역할을 담당하는 인터페이스
//    //                    데이터베이스 연결, 트랜잭션 관리, 매퍼 파일의 위치 등 MyBatis 설정 정보를 포함       
//    // SqlSession: MyBatis에서 데이터베이스와 상호작용(SQL 실행, 트랜잭션을 관리, ...)하는 인터페이스
//    @Bean
//    SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setMapperLocations(
//            applicationContext.getResources("classpath:/mapper/**/sql-*.xml")
//        );
//        
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        sessionFactoryBean.setConfiguration(configuration);
//        
//        return sessionFactoryBean.getObject();
//    }
//    
//    // SqlSessionTemplate: MyBatis와 스프링 프레임워크를 통합할 때 사용하는 클래스 
//    //                     SqlSesstion 인터페이스를 구현 
//    @Bean
//    SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
    
//    @Bean
//    @ConfigurationProperties(prefix = "mybatis.configuration")
//    org.apache.ibatis.session.Configuration mybatisConfig() {
//        return new org.apache.ibatis.session.Configuration();
//        
//    }
}
