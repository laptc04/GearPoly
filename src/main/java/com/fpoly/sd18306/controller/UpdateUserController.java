package com.fpoly.sd18306.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.BillEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.jpa.BillsJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UpdateUserController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	BillsJPA billsJPA;

	@Autowired
	AccountJPA accountJPA;

	@GetMapping("/user/nguoidung")
	public String ngdung(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				String accountId = cookie.getValue();
				Optional<AccountEntity> accountEntity = accountJPA.findById(accountId);
				AccountEntity account = accountJPA.findById(accountId).orElse(null);
				if (account != null) {
					Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

					Page<BillEntity> billPage = billsJPA.findByAccount(account, pageable);
					List<BillEntity> billEntity = new ArrayList<>(billPage.getContent());

					model.addAttribute("account", accountEntity.get());
					model.addAttribute("bills", billEntity);
					model.addAttribute("currentPage", page);
					model.addAttribute("totalPages", billPage.getTotalPages());
				}
			}
		}
		return "client/qlTTngdung";
	}

	@PostMapping("/update-user")
	public String updateUser(Model model, @RequestParam("id") String id, @RequestParam("fullname") String fullname,
			@RequestParam("phone") String phone, @RequestParam("email") String email,
			@RequestParam("address") String address) {
		

		return "/user/nguoidung";
	}

}
