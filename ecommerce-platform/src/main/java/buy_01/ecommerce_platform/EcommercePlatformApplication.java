package buy_01.ecommerce_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {
    "buy_01.ecommerce_platform.user.repository",
    "buy_01.ecommerce_platform.product.repository",
    "buy_01.ecommerce_platform.media.repository"
})
@ComponentScan(basePackages = {"buy_01.ecommerce_platform", "buy_01.ecommerce_platform.config"})
public class EcommercePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommercePlatformApplication.class, args);
	}
}
