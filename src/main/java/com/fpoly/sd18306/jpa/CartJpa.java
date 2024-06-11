package com.fpoly.sd18306.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.CartEntity;
import com.fpoly.sd18306.entities.ProductEntity;

public interface CartJpa extends  JpaRepository <CartEntity, String>{
	@Query(value="select *from carts c where c.account_id =:accountID", nativeQuery = true)
	public List<CartEntity> findByAccountID(@RequestParam("accountID") String accountID);
	
	Optional<CartEntity> findByAccountEntityAndProductEntity(AccountEntity account, ProductEntity product);
}

