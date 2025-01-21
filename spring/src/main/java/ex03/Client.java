package ex03;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Client implements InitializingBean, DisposableBean {

    private String host;
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public void send() {
        System.out.println("Client.send() is called ... " + this.host);
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Client.afterPropertiesSet() is called ... ");
        
    }
    
    @Override
    public void destroy() throws Exception {
        System.out.println("Client.destroy() is called ... ");        
    }
}
