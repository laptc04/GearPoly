package com.fpoly.sd18306.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.sd18306.entities.SupplierEntity;


public interface SuppliersJpa extends JpaRepository<SupplierEntity, String> {
	
}
