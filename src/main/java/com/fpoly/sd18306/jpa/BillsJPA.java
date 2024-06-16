package com.fpoly.sd18306.jpa;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.BillEntity;

import jakarta.transaction.Transactional;

public interface BillsJPA extends JpaRepository<BillEntity, String> {

	// lấy sđt theo id khách hàng
	@Query(value = "SELECT phone FROM accounts WHERE id = :id", nativeQuery = true)
	String findPhoneById(@Param("id") String id);

	@Query(value = "SELECT * FROM bills WHERE account_id = :account_id", nativeQuery = true)
	List<BillEntity> findByacId(@Param("account_id") String account_id);
	
	// update thông tin mà khách hàng đã sửa
	@Modifying
	@Transactional
	@Query(value = "UPDATE accounts SET fullname = :fullname, phone = :phone, address = :address WHERE id = :id", nativeQuery = true)
	public int updateByAccountId(@Param("fullname") String fullname, @Param("phone") String phone,
			@Param("address") String address, @Param("id") String id);

	// xóa sản phẩm trong giỏ hàng khi mua sản phẩm
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM carts WHERE account_id = :accountId", nativeQuery = true)
	void deleteByAccountId(@Param("accountId") String accountId);
	
	@Query(value = "select total from bills where id = :billID", nativeQuery = true)
	String findTotalById(@Param("billID") String billID);
	
	@Query(value = "select bill_date from bills where id = :billID", nativeQuery = true)
	Date findBillDateById(@Param("billID") String billID);
	
	Page<BillEntity> findByAccount (AccountEntity account, Pageable pageable);
}
