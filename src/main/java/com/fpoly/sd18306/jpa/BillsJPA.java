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

	// lấy sđt theo id khách hàng
	@Query(value = "SELECT phone FROM accounts WHERE id = :id", nativeQuery = true)
	String findPhoneById(@Param("id") String id);

	// lấy số lượng từng sản phẩm theo id sản phẩm có trong giỏ hàng
	@Query(value = "SELECT quantity FROM carts WHERE product_id = :product_id", nativeQuery = true)
	int findQuantityByProductId(@Param("product_id") int idsp);
	
//	@Modifying
//	@Transactional
//	@Query(value = "DELETE FROM carts WHERE product_id = :product_id", nativeQuery = true)
//	void deleteByProductID(@Param("product_id") int idsp);

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
}
