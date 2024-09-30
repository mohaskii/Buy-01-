package buy_01.ecommerce_platform.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Import;
import buy_01.ecommerce_platform.user.config.SecurityConfig;

@SpringBootApplication
@PropertySource("classpath:user-service.properties")
@Import(SecurityConfig.class)
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
