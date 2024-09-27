package buy_01.ecommerce_platform.user.repository;

import buy_01.ecommerce_platform.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
