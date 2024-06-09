package com.fpoly.sd18306.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class CartEntity implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	@JsonIgnoreProperties("carts")
	AccountEntity accountEntity;

	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnoreProperties("carts")
	ProductEntity productEntity;

	@Column
	private int quantity;

	@Column
	private Integer price;

	@Column(length = 255)
	private String image;
}
