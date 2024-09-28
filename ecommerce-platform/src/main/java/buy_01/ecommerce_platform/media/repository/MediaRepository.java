package buy_01.ecommerce_platform.media.repository;

import buy_01.ecommerce_platform.media.model.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends MongoRepository<Media, String> {
    Media findByProductId(String productId);
}