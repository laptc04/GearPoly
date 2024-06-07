package com.fpoly.sd18306.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.sd18306.entities.CategoryEntity;
import com.fpoly.sd18306.jpa.CategoryJPA;
import com.fpoly.sd18306.jpa.ImageJPA;
import com.fpoly.sd18306.jpa.ProductJPA;
import com.fpoly.sd18306.models.Product;

@Controller
public class ProductController {
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CategoryJPA categoryJPA; 
	
	@Autowired
	ImageJPA imageJPA;
	
	
	@GetMapping("/productsManager")
	public String product(Model model) {
		model.addAttribute("products", productJPA.findAll());
		return "/admin/qlsanpham";
	}
	
//	@GetMapping("/add-productsManager")
//	public String AddProduct() {
//		
//		return "/admin/qlsanpham";
//	}
//	
//	@GetMapping("/add-productsManager")
//	public String AddProductSave(Product product, RedirectAttributes redirectAttributes) {
//		
//		return "/admin/qlsanpham";
//	}
	
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategoriesEntities() {
		return categoryJPA.findAll();
	}
}
