package vrp;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@EnableProcessApplication
public class SupportSystemApplication {

    public static void main(String [] args){
        SpringApplication.run(SupportSystemApplication.class, args);
    }

}
