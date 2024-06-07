package com.fpoly.sd18306.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.sd18306.entities.ImageEntity;

public interface ImageJPA extends JpaRepository<ImageEntity, String>{

	Optional<ImageEntity> findById(int id);

}
