package com.fpoly.sd18306.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.sd18306.entities.BillEntity;
import com.fpoly.sd18306.entities.CartEntity;

import jakarta.transaction.Transactional;

public interface BillsJPA extends JpaRepository<BillEntity, String> {

	//lấy sđt theo id ng dùng
	@Query(value = "SELECT phone FROM accounts WHERE id = :id", nativeQuery = true)
	String findPhoneById(@Param("id") String id);

	// update thông tin mà khách hàng đã sửa
	@Modifying
	@Transactional
	@Query(value = "UPDATE accounts SET fullname = :fullname, phone = :phone, address = :address WHERE id = :id", nativeQuery = true)
	public int updateByAccountId(@Param("fullname") String fullname, @Param("phone") String phone,
			@Param("address") String address, @Param("id") String id);

}
