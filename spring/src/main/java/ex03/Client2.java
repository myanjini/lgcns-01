package ex03;

public class Client2 {
    private String host;
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public void send() {
        System.out.println("Client.send() is called ... " + this.host);
    }
    
    public void connect() {
        System.out.println("Client.connect() is called ... ");
    }

    public void close() {
        System.out.println("Client.close() is called ... ");
    }
}
