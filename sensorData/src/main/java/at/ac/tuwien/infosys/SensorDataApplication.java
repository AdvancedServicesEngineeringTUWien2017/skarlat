package at.ac.tuwien.infosys;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by lenaskarlat on 4/12/17.
 */
@EnableScheduling
@SpringBootApplication
public class SensorDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorDataApplication.class, args);
    }
}