package com.fpoly.sd18306.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.sd18306.entities.DetailBillEntity;

public interface DetailsBillJPA extends JpaRepository<DetailBillEntity, String>{
	
	@Query(value = "SELECT * FROM details_bill WHERE bill_id = :bill_id", nativeQuery = true)
	List<DetailBillEntity> findBybillId(@Param("bill_id") String bill_id);

}
