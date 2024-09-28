package buy_01.ecommerce_platform.media.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "media")
public class Media {
    @Id
    private String id;

    @NotBlank(message = "Image path is mandatory")
    private String imagePath;

    @NotBlank(message = "Product ID is mandatory")
    private String productId;

    // Constructors
    public Media() {}

    public Media(String imagePath, String productId) {
        this.imagePath = imagePath;
        this.productId = productId;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
