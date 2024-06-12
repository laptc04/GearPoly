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
    
	@Query("SELECT p FROM AccountEntity p WHERE LOWER(p.fullname) LIKE LOWER(CONCAT('%', :fullname, '%'))")
	public List<AccountEntity> findByNameContainingIgnoreCase(@Param("fullname") String fullname);
	
	@Query("SELECT p FROM AccountEntity p WHERE LOWER(p.phone) LIKE LOWER(CONCAT('%', :phone, '%'))")
	public List<AccountEntity> findByPhoneContainingIgnoreCase(@Param("phone") String phone);
	

//	@Query(nativeQuery = true, name = "UseAd.getAccountBillsByTotal")
//	List<UseAdEntity>findAccountBillsByTotal(@Param("bill_date") String bill_date);
//	@Query("SELECT a.fullname, YEAR(b.billDate) AS year, SUM(b.total) AS totalSum \" +\r\n"
//			+ "FROM BillEntity b"
//			+ "INNER JOIN AccountEntity a ON a.id = b.account_id"
//			+ "GROUP BY a.fullname, YEAR(b.billDate)"
//			+ "ORDER BY totalSum DESC, year DESC")
//	public List<AccountEntity> findNameByYear(@Param("billDate") String billDate);
}
 


