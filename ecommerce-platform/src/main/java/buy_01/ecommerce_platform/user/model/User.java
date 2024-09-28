package buy_01.ecommerce_platform.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    public String name;
    public String email;
    private String password;
    private String role;
    private String avatar;

    // Constructors, getters, and setters
}