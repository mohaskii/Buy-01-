package buy_01.ecommerce_platform.media.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "media")
public class Media {

    @Id
    private String id;
    private String imagePath;
    private String productId;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Constructors, getters, and setters
}