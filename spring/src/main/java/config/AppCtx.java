package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ex02.MemberDAO;
import ex02.MemberPrinter;
import ex02.MemberSummaryPrinter;
import ex02.VersionPrinter;
import ex03.Client;
import ex03.Client2;


@Configuration
public class AppCtx {
    @Bean(initMethod = "connect", destroyMethod = "close")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Client2 client2() {
        Client2 client = new Client2();
        client.setHost("www.test.com");
        return client;
    }

    // @Bean
    public Client client() {
        Client client = new Client();
        client.setHost("www.test.com");
        return client;
    }

    @Bean
    public MemberDAO memberDAO() {
        return new MemberDAO();
    }

    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

    @Bean
    @Qualifier("summaryPrinter")
    public MemberSummaryPrinter memberPrinter2() {
        return new MemberSummaryPrinter();
    }

    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(3);
        return versionPrinter;
    }
}
