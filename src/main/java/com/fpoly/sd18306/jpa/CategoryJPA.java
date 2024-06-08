package com.fpoly.sd18306.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.sd18306.entities.CategoryEntity;
import com.fpoly.sd18306.entities.ProductEntity;

public interface CategoryJPA extends JpaRepository<CategoryEntity, String>{
	@Query(value = "SELECT * FROM categories WHERE categories_name LIKE %:categories_name%", nativeQuery = true)
    List<CategoryEntity> findByName(@Param("categories_name") String categories_name);
    
}
