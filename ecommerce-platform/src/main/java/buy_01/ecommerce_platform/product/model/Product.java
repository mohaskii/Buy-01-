package buy_01.ecommerce_platform.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Document(collection = "products")
public class Product {
    @Id
    private String id;

    @NotBlank(message = "Product name is mandatory")
    private String name;

    @NotBlank(message = "Product description is mandatory")
    private String description;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Quantity is mandatory")
    @PositiveOrZero(message = "Quantity must be zero or greater")
    private Integer quantity;

    @NotBlank(message = "User ID is mandatory")
    private String userId;

    // Constructors
    public Product() {}

    public Product(String name, String description, Double price, Integer quantity, String userId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.userId = userId;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
