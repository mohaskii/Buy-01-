package buy_01.ecommerce_platform.media.repository;

import buy_01.ecommerce_platform.media.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MediaRepository extends MongoRepository<Media, String> {
    List<Media> findByProductId(String productId);
}