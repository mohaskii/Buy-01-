package buy_01.ecommerce_platform.user.service;

import buy_01.ecommerce_platform.user.model.User;
import buy_01.ecommerce_platform.user.model.UserRole;
import buy_01.ecommerce_platform.user.repository.UserRepository;
import buy_01.ecommerce_platform.exception.ResourceNotFoundException;
import buy_01.ecommerce_platform.service.KafkaMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaMessageService kafkaMessageService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KafkaMessageService kafkaMessageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.kafkaMessageService = kafkaMessageService;
    }

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
        User newUser = userRepository.save(user);
        kafkaMessageService.sendUserMessage("Nouvel utilisateur créé : " + newUser.getId());
        return newUser;
    }

    // Méthode pour mettre à jour un utilisateur
    public User updateUser(String id, User user) {
        User existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            // Vous pouvez également mettre à jour d'autres informations si nécessaire
            User userUpdated = userRepository.save(existingUser);
            kafkaMessageService.sendUserMessage("Utilisateur" + userUpdated.getId() + "à été mis à jour");
            return userUpdated;
        }
        throw new ResourceNotFoundException("User not found with id: " + id);
    }

    // Méthode pour supprimer un utilisateur
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    // Méthode d'enregistrement d'un nouvel utilisateur
    public User registerUser(User user) {
        // Vérifier si un utilisateur avec cet email existe déjà
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }

        // Encoder le mot de passe avant de le sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Définir le rôle par défaut s'il n'est pas fourni
        if (user.getRole() == null) {
            user.setRole(UserRole.CLIENT); // Par défaut, on enregistre les utilisateurs comme CLIENT
        }

        // Sauvegarder le nouvel utilisateur
        user = userRepository.save(user);
        kafkaMessageService.sendUserMessage("Utilisateur" + user.getId() + "à été ajouté");
        return user;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
