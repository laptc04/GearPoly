package com.fpoly.sd18306.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.BillEntity;
import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.entities.UseAdEntity;

@Repository
public interface UseAdJPA extends JpaRepository<AccountEntity, String> {
	Optional<AccountEntity> findById(String id);
    //boolean existsByName(String fullname);

	@Query("SELECT p FROM AccountEntity p WHERE LOWER(p.fullname) LIKE LOWER(CONCAT('%', :fullname, '%'))")
	public List<AccountEntity> findByNameContainingIgnoreCase(@Param("fullname") String fullname);
	
	@Query("SELECT p FROM AccountEntity p WHERE LOWER(p.phone) LIKE LOWER(CONCAT('%', :phone, '%'))")
	public List<AccountEntity> findByPhoneContainingIgnoreCase(@Param("phone") String phone);
	
	

	@Query(nativeQuery = true, name = "UseAd.getAccountBillsByTotal")
	List<UseAdEntity>findAccountBillsByTotal(@Param("bill_date") String bill_date);

}
 


