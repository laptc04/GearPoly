package com.fpoly.sd18306.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.sd18306.entities.AccountsEntity;

public interface AccountJPA extends JpaRepository<AccountsEntity, String> {

}
