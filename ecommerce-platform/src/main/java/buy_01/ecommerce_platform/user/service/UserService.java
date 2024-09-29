package buy_01.ecommerce_platform.user.service;

import buy_01.ecommerce_platform.user.model.User;
import buy_01.ecommerce_platform.user.repository.UserRepository;

import buy_01.ecommerce_platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Méthode pour récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Méthode pour récupérer un utilisateur par ID
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    // Méthode pour créer un nouvel utilisateur
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Méthode pour mettre à jour un utilisateur
    public User updateUser(String id, User user) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.name = user.name;
            existingUser.email =  user.email;
            return userRepository.save(existingUser);
        }
        throw new ResourceNotFoundException("User not found with id: " + id);
    }

    // Méthode pour supprimer un utilisateur
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
    // Add other methods as needed
