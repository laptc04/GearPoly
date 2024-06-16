package com.fpoly.sd18306.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.sd18306.entities.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, String>{
	Optional<ProductEntity> findById(int id);

	@Query("SELECT p FROM ProductEntity p WHERE p.product_name LIKE %:product_name%")
    List<ProductEntity> findByName(@Param("product_name") String product_name);

    List<ProductEntity> findByPriceBetween(Double minPrice, Double maxPrice);
    
//	@Query(value = "SELECT * FROM products WHERE product_name LIKE %:product_name%", nativeQuery = true)
//    List<ProductEntity> findByName(@Param("product_name") String product_name);
	
	Page<ProductEntity> findByHien(boolean hien, Pageable pageable);

//	List<ProductEntity> findByPriceBetween(Double minPrice, Double maxPrice);
	
	Page<ProductEntity> findByCategoryEntityId(int categoryId, Pageable pageable);
}
	