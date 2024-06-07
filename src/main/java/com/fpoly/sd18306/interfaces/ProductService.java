package com.fpoly.sd18306.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpoly.sd18306.entities.ProductEntity;

@Repository
public interface ProductService extends JpaRepository<ProductEntity, Integer> {
	
	public List<ProductEntity> findAll();
}