package com.fpoly.sd18306.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fpoly.sd18306.models.Account;
import com.fpoly.sd18306.models.Product;

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
public class CartEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	@JsonIgnoreProperties("carts")
	AccountEntity accountEntity;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnoreProperties("carts")
	ProductEntity productEntity;

	@Column
	private Integer quantity;

	@Column
	private Integer price;

	@Column(length = 255)
	private String image;
}
