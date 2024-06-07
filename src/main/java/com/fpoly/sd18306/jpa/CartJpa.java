package com.fpoly.sd18306.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.sd18306.entities.CartEntity;

public interface CartJpa extends  JpaRepository <CartEntity, String>{
 
}
