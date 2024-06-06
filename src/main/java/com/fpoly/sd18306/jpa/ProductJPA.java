package com.fpoly.sd18306.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.sd18306.entities.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, String>{
	Optional<ProductEntity> findById(int id);
}
