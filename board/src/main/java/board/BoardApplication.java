package board;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BoardApplication {
    
	public static void main(String[] args) {
	    log.trace("trace");
	    log.debug("debug");
	    log.info("info");
	    log.warn("warn");
	    log.error("error");
	    
	    SpringApplication.run(BoardApplication.class, args);
	}

}
