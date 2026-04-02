package com.quitq.repository;

import com.quitq.model.Product;
import com.quitq.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Search by name (containing keyword, case insensitive)
//    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1 , '%'))")
    Page<Product> findByNameContainingIgnoreCase( String keyword, Pageable pageable);

    // Filter by category
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1")
    Page<Product> findByCategory_Id( Long categoryId, Pageable pageable);

    // Filter by price range
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    Page<Product> findByPriceBetween( Double min,  Double max, Pageable pageable);




    @Query("SELECT p FROM Product p WHERE p.category.id = ?1")
    List<Product> findByCat_id( long id);

}