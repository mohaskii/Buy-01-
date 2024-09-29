package buy_01.ecommerce_platform.product.service;

import buy_01.ecommerce_platform.product.model.Product;
import buy_01.ecommerce_platform.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setUserId(product.getUserId());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}