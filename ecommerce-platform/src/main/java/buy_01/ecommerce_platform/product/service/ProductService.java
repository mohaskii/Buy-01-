package buy_01.ecommerce_platform.product.service;

import buy_01.ecommerce_platform.product.model.Product;
import buy_01.ecommerce_platform.product.repository.ProductRepository;
import buy_01.ecommerce_platform.service.KafkaMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final KafkaMessageService kafkaMessageService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductService(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        kafkaMessageService.sendProductMessage("Nouveau produit créé : " + newProduct.getId());
        return newProduct;
    }

    public Product updateProduct(String id, Product product) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setUserId(product.getUserId());
            Product productUpdated = productRepository.save(existingProduct);
            kafkaMessageService.sendProductMessage("Produit: " + productUpdated.getId() + "à été mis à jour");
        }
        return null;
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}