package com.fpoly.sd18306.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.entities.UseAdEntity;

@Repository
public interface UseAdJPA extends JpaRepository<UseAdEntity, String> {
	Optional<UseAdEntity> findById(String id);
    
	@Query("SELECT p FROM UseAdEntity p WHERE LOWER(p.fullname) LIKE LOWER(CONCAT('%', :fullname, '%'))")
	public List<UseAdEntity> findByNameContainingIgnoreCase(@Param("fullname") String fullname);
 }


