package com.fpoly.sd18306.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.entities.UseAdEntity;

@Repository
public interface UseAdJPA extends JpaRepository<UseAdEntity, String> {
	Optional<UseAdEntity> findById(String id);

	@Query(value = "SELECT b.account_id AS user_id, SUM(d.price * d.quantity) AS max_price " + "FROM bills b "
			+ "INNER JOIN detailsBill d ON b.id = d.bill_id "
			+ "WHERE b.account_id = ?1 AND MONTH(b.bill_date) = ?2 AND YEAR(b.bill_date) = ?3 "
			+ "GROUP BY b.account_id", nativeQuery = true)
	Object getMaxPriceByUserAndMonthYear(String userId, int month, int year);

}
