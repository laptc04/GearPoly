 package com.fpoly.sd18306.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.CartEntity;
import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.jpa.CartJpa;
import com.fpoly.sd18306.jpa.ProductJPA;
import com.fpoly.sd18306.models.Product;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {
	@Autowired
	HttpSession session;

	@Autowired
	ProductService service;

	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CartJpa cartJPA;

	public List<CartEntity> getCartList() {
		List<CartEntity> CartEntity = (List<CartEntity>) session.getAttribute("cart");
		return CartEntity == null ? new ArrayList<CartEntity>() : CartEntity;
	}

	
	public void remove(int id) {
		//get cart list
		List<CartEntity> CartEntitys = this.getCartList();
		for(int index = 0 ; index < CartEntitys.size(); index++) {
			if(CartEntitys.get(index).getId() == id) {
				CartEntitys.remove(index);
				break;
			}
		}
		session.setAttribute("cart", CartEntitys);
 	}

	public void update(int id, int quantity) {
		List<CartEntity> CartEntitys = this.getCartList();
//		for(int index = 0 ; index < CartEntitys.size(); index++) {
//			if(CartEntitys.get(index).getId() == id) {
//				CartEntity item = CartEntitys.get(index);
//				item.setQuantity(quantity);
//				CartEntitys.set(index, item);
//				break;
//			}
//		}
		for (CartEntity cartEntity : CartEntitys) {
			if (cartEntity.getId() ==id) {
				cartEntity.setQuantity(quantity);
				cartEntity.setPrice((cartEntity.getProductEntity().getPrice()) * quantity);
				cartJPA.save(cartEntity);
				break;
			}
		}
		session.setAttribute("cart", CartEntitys);
	}
	
	public void clear() {
		session.removeAttribute("cart");
	}

	public int getCount() {
		int count = 0 ;
		for(CartEntity item : this.getCartList()) {
			count += item.getQuantity();
		}
		return count;
		
	}

	public int getAmount() {
		int amount = 0 ;
		List<CartEntity> cartList =this.getCartList();
		for (CartEntity cartEntity : cartList) {
			amount += cartEntity.getPrice();
		}
		
		
		return amount;
	}

}
