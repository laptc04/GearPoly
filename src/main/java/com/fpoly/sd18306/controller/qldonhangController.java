package com.fpoly.sd18306.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.jpa.BillsJPA;

@Controller
public class qldonhangController {
	@Autowired
	BillsJPA billsJPA;
	
	@GetMapping("admin/qldonhang")
	public String getQlDonHang(Model model){
		model.addAttribute("donhangall", billsJPA.findAll());
		return "admin/qldonhang";
	}
}
