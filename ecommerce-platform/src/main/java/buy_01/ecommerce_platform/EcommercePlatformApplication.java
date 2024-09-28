package buy_01.ecommerce_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {
    "buy_01.ecommerce_platform.user.repository",
    "buy_01.ecommerce_platform.product.repository",
    "buy_01.ecommerce_platform.media.repository"
})
public class EcommercePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommercePlatformApplication.class, args);
	}

}
