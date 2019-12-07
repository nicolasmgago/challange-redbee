package challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"challenge/repository", "challenge/services", "challenge/rabbit", "challenge/controller"})
@EnableJpaRepositories(basePackages = "challenge.repository")
@EntityScan(basePackages = "challenge.model")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}