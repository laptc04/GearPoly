package com.fpoly.sd18306.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.sd18306.entities.AccountEntity;

public interface AccountJPA extends JpaRepository<AccountEntity, String> {

}
