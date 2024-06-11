package com.fpoly.sd18306.models;

import com.fpoly.sd18306.entities.ProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem extends ProductEntity{
	private int quantity;
}
