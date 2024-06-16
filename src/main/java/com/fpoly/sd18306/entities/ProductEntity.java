package com.fpoly.sd18306.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "products")
public class ProductEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "product_name")
	private String product_name;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "hien")
	private boolean hien;
	
	@ManyToOne
	@JoinColumn(name = "categories_id")
	@JsonIgnoreProperties(value = "productEntity")
	CategoryEntity categoryEntity;
	
	@OneToMany(mappedBy = "productEntity")
	List<ImageEntity> imageEntities;
	
	@OneToMany(mappedBy = "productEntity")
	List<DetailBillEntity> detailsBillEntities;
}
