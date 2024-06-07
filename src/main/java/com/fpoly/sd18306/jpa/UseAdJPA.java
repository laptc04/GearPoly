package com.fpoly.sd18306.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.entities.UseAdEntity;

public interface UseAdJPA extends JpaRepository<UseAdEntity, String>{
	Optional<UseAdEntity> findById(String id);

}

