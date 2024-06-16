package com.fpoly.sd18306.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.sd18306.entities.CategoryEntity;

public interface CategoryJPA extends JpaRepository<CategoryEntity, String>{
//	@Query(value = "SELECT * FROM categories WHERE categories_name LIKE %:categories_name%", nativeQuery = true)
//    List<CategoryEntity> findByName(@Param("categories_name") String categories_name);
    
	@Query(value = "SELECT * FROM categories WHERE categories_name LIKE %:categories_name%",
	           countQuery = "SELECT count(*) FROM categories WHERE categories_name LIKE %:categories_name%",
	           nativeQuery = true)
	    Page<CategoryEntity> findByName(@Param("categories_name") String categories_name, Pageable pageable);
	
	Optional<CategoryEntity> findById(int id);

	
}
