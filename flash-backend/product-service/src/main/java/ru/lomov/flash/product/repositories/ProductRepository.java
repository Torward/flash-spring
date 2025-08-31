package ru.lomov.flash.product.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lomov.flash.product.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    
    Page<Product> findByCategory(String category, Pageable pageable);
    
    Page<Product> findByType(String type, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Product> searchProducts(@Param("query") String query, Pageable pageable);
    
    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);
    
    List<Product> findByUserId(String userId);
    
    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    Page<Product> findLatestProducts(Pageable pageable);
}
