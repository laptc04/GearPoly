package com.fpoly.sd18306.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.jpa.CartJpa;
import com.fpoly.sd18306.jpa.ProductJPA;



@Controller
public class CartController {
	@Autowired
	CartJpa cartJpa; 
	
	@Autowired
	ProductJPA productJPA;
	
//	@GetMapping("/index")
//	public String cartList(Model model) {
//		model.addAttribute("cart", cartJpa.findAll());
//		return "client/index";
//		
//	}
	@GetMapping("user/index")
	public String getsanpham(Model model) {
		model.addAttribute("products", productJPA.findAll());
		return "client/sanpham";
		
	}
	
	@GetMapping("user/chitiet")
	public String getchitietsanpham(Model model,@RequestParam("id") String id){
		model.addAttribute("productID", productJPA.findById(id).orElse(null));
		return "client/chitietsanpham";
	}
		
	
	@GetMapping("/add-to-cart")
	public String updateCart(Model model,@RequestParam("id") int id , @RequestParam("path") String path,
			@RequestParam(name = "quantity", defaultValue = "1") int quantity) {
		model.addAttribute("cartID", cartJpa.findById("id"));
		return "client/giohang";
	}
//	//
//	@GetMapping("/remove-to-cart")
//	public String removeCar(@RequestParam("id") int id) {
//		cartService.remove(id);
//		return "redirect:/cart";
//	}
//	//
//	@GetMapping("/update-to-cart")
//	public String updateCart(@RequestParam("id") int id,
//			@RequestParam("quantity") int quantity) {
//		cartService.update(id,quantity);
//		if (quantity >=1) {
//			cartService.update(id, quantity);
//		}
//		else if (quantity <1) {
//			cartService.remove(id);
//		}
//		return "redirect:/cart";
//	}
}
