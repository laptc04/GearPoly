package com.fpoly.sd18306.entities;

import java.io.Serializable;

//
import java.sql.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@NamedNativeQuery(
name = "UseAd.getAccountBillsByTotal",
query = "SELECT a.fullname, YEAR(b.bill_date) AS year, SUM(b.total) AS totalSum " +
	       "FROM bills b " +
	       "INNER JOIN accounts a ON a.id = b.account_id " +
	       "GROUP BY a.fullname, YEAR(b.bill_date) " +
	       "ORDER BY totalSum DESC, year DESC",
resultSetMapping = "UseAdEntitye"
)
@SqlResultSetMapping
(name = "UseAdEntitye",classes = {
		@ConstructorResult(targetClass =  UseAdEntity.class, 
		columns = {
		@ColumnResult(name = "fullname"),
		@ColumnResult(name = "total", type = int.class),
		@ColumnResult(name = "bill_date", type = Date.class)
				}
			)
		}
	)
@Getter
@Setter
@Entity
public class UseAdEntity{
	@Id
	private final String fullname;
	private final int total;
	private final Date bill_date;
	
	public
	UseAdEntity(
			
			String fullname, 
			int total, 
			Date bill_date) {
		
		this.fullname = fullname;
		this.total = total;
		this.bill_date = bill_date;
	}
}
