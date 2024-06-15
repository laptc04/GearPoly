package com.fpoly.sd18306.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpoly.sd18306.entities.CategoryEntity;
import com.fpoly.sd18306.entities.DetailBillEntity;

public interface DetailsBillJPA extends JpaRepository<DetailBillEntity, String>{

}
