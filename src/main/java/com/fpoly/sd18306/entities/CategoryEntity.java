package com.fpoly.sd18306.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "categories")
public class CategoryEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "categories_name", unique = true, length = 255)
	private String categories_name;
	
	@Column(name = "image", length = 255)
	private String image;
	
	@OneToMany(mappedBy = "categoryEntity")
	@JsonManagedReference
	List<ProductEntity> productEntity;
}
