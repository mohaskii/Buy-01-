package buy_01.ecommerce_platform.user.service;

import buy_01.ecommerce_platform.user.model.User;
import buy_01.ecommerce_platform.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        // Add logic for user creation
        return userRepository.save(user);
    }

    // Add other methods as needed
}