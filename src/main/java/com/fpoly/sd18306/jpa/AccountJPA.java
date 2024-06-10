package com.fpoly.sd18306.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.sd18306.entities.AccountEntity;

import jakarta.transaction.Transactional;


public interface AccountJPA extends JpaRepository<AccountEntity, String> {
	AccountEntity findByIdAndPassword(String id, String password);
	
	@Modifying
	@Transactional
    @Query("UPDATE AccountEntity a SET a.password = :newPassword WHERE a.id = :id")
    void updatePassword(@Param("id") String id, @Param("newPassword") String newPassword);
}
