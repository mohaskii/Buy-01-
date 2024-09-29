package buy_01.ecommerce_platform.user.repository;

import buy_01.ecommerce_platform.user.model.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
