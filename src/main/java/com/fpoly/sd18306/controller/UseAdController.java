package com.fpoly.sd18306.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpoly.sd18306.jpa.UseAdJPA;

@Controller
public class UseAdController {

	@Autowired
	UseAdJPA useadJPA;
	
	@RequestMapping("/")
	public String ManagerUser(Model model) {
		model.addAttribute("account", useadJPA.findAll());
		return "/admin/qlnguoidung";
	}
}
