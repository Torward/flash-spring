package ru.lomov.flash.product.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lomov.flash.product.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    
    Product createProduct(Product product);
    
    Product updateProduct(String id, Product product);
    
    void deleteProduct(String id);
    
    Optional<Product> getProductById(String id);
    
    Page<Product> getAllProducts(Pageable pageable);
    
    Page<Product> getProductsByCategory(String category, Pageable pageable);
    
    Page<Product> getProductsByType(String type, Pageable pageable);
    
    Page<Product> searchProducts(String query, Pageable pageable);
    
    Page<Product> getProductsByPriceRange(Double minPrice, Double maxPrice, Pageable pageable);
    
    List<Product> getUserProducts(String userId);
    
    Page<Product> getLatestProducts(Pageable pageable);
}
