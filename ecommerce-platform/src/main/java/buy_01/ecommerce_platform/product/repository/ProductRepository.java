package buy_01.ecommerce_platform.product.repository;

import buy_01.ecommerce_platform.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}