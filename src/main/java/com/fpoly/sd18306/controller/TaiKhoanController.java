package com.fpoly.sd18306.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.models.Account;

import jakarta.validation.Valid;


@Controller
public class TaiKhoanController {
	@Autowired
	AccountJPA accountJPA;
	
	@GetMapping("/index")
	public String index() {
		
		return "client/index";
	}
	@GetMapping("/register")
	public String register() {
		return "client/register";
	}
	
	@PostMapping("/register")
	public String registerSave(@Valid  Account account, BindingResult error,
							   @RequestParam("id") String id,
							   @RequestParam("fullname") String fullname,
							   @RequestParam("email") String email,
							   @RequestParam("password") String password, Model model) {
		
		if(error.hasErrors()) {
			model.addAttribute("error", error);
		}else {
			AccountEntity accountEntity = new AccountEntity();
			
			accountEntity.setId(id);
			accountEntity.setFullname(fullname);
			accountEntity.setEmail(email);
			accountEntity.setPassword(password);
			accountEntity.setPhone("");
			accountEntity.setAddress("");
			accountEntity.setImage("");
			accountEntity.setRole(false);
			AccountEntity accSaveEntity = accountJPA.save(accountEntity);
		}
		model.addAttribute("account", account);
		return "client/register";
	}
	@GetMapping("/login")
	public String login() {
		return "client/login";
	}
	
	@PostMapping("/login")
	public String loginSave(@Valid @ModelAttribute("account") Account account,BindingResult error, Model model) {
		if(error.hasErrors()) {
			model.addAttribute("error", error);
		}
		model.addAttribute("account", account);
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
