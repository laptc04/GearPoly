 package com.fpoly.sd18306.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.jpa.ProductJPA;
import com.fpoly.sd18306.models.CartItem;
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

	public ArrayList<CartItem> getCartItems() {
		ArrayList<CartItem> cartItem = (ArrayList<CartItem>) session.getAttribute("cart");
		return cartItem == null ? new ArrayList<CartItem>() : cartItem;
	}

	public void add(@RequestParam("id") int id) {
		Optional<ProductEntity> productfind = productJPA.findById(id);
		
		CartItem item = new CartItem();
		if(productfind.isPresent()) {
			
			item.setId(productfind.get().getId());
			item.setProduct_name(productfind.get().getProduct_name());
			item.setDescription(productfind.get().getDescription());
			item.setPrice(productfind.get().getPrice());
			item.setQuantity(1);
			ArrayList<CartItem> cartItems = this.getCartItems();
			int index = -1;
			for (int i = 0; i < cartItems.size(); i++) {
				if (cartItems.get(i).getId() == id) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				cartItems.add(item);
			} else {
				int quantity = cartItems.get(index).getQuantity();
				item.setQuantity(quantity + 1);
				cartItems.set(index, item);
			}
			session.setAttribute("cart", cartItems);
			
		}
		
		
		
	}

	public void remove(int id) {
		//get cart list
		ArrayList<CartItem> cartItems = this.getCartItems();
		for(int index = 0 ; index < cartItems.size(); index++) {
			if(cartItems.get(index).getId() == id) {
				cartItems.remove(index);
				break;
			}
		}
		session.setAttribute("cart", cartItems);
 	}

	public void update(int id, int quantity) {
		ArrayList<CartItem> cartItems = this.getCartItems();
		for(int index = 0 ; index < cartItems.size(); index++) {
			if(cartItems.get(index).getId() == id) {
				CartItem item = cartItems.get(index);
				item.setQuantity(quantity);
				cartItems.set(index, item);
				break;
			}
		}
		session.setAttribute("cart", cartItems);
	}
	
	public void clear() {
		session.removeAttribute("cart");
	}

	public int getCount() {
		int count = 0 ;
		for(CartItem item : this.getCartItems()) {
			count += item.getQuantity();
		}
		return count;
		
	}

	public int getAmount() {
		int amout = 0 ;
		for(CartItem item : this.getCartItems()) {
			amout += item.getQuantity() * item.getPrice();
		}
		
		
		return amout;
	}

}
