package com.fpoly.sd18306.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	
	@Column(name = "in_stock")
	private int in_stock;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "images")
	private String images;
	
	
//	@OneToMany(mappedBy = "productEntity")
//    private List<ImageEntity> imageEntities;
}
