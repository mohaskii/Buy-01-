package buy_01.ecommerce_platform.user.controller;

import buy_01.ecommerce_platform.user.model.User;
import buy_01.ecommerce_platform.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Add other endpoints as needed
}