package com.fpoly.sd18306.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.fpoly.sd18306.models.Account;

import jakarta.validation.Valid;


@Controller
public class TaiKhoanController {
	@GetMapping("/index")
	public String index() {
		
		return "client/index";
	}
	@GetMapping("/register")
	public String register() {
		return "client/register";
	}
	
	@PostMapping("/register")
	public String registerSave(@Valid @ModelAttribute("account") Account account,BindingResult error, Model model) {
		if(error.hasErrors()) {
			model.addAttribute("error", error);
		}
		model.addAttribute("account", account);
		return "client/register";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "client/login";
	}
	@GetMapping("/changepassword")
	public String changePassWord() {
		
		return "client/changepassword";
	}
	@GetMapping("/forgotpassword")
	public String forgotPassWord() {
		
		return "client/Forgotpassword";
	}
}
