package com.fpoly.sd18306.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SuppliersController {
	
	
	
	
	@GetMapping("/Suppliers")
	public String getSuppliers() {
		return "admin/qlHangsx";
	}
	
}
