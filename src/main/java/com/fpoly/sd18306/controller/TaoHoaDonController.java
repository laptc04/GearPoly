package com.fpoly.sd18306.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.CartEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.jpa.BillsJPA;
import com.fpoly.sd18306.jpa.CartJpa;
import com.fpoly.sd18306.jpa.ProductJPA;
import com.fpoly.sd18306.models.Account;
import com.fpoly.sd18306.services.CartService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class TaoHoaDonController {
	
	@Autowired
	CartService cartService;

	@Autowired
	BillsJPA billsJPA;

	@Autowired
	AccountJPA accountJPA;

	@Autowired
	CartJpa cartJPA;

	@Autowired
	ProductJPA productJPA;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/taohoadon")
	public String getTaoHoaDon(Model model) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				String name = cookie.getValue();
				System.out.println(name);
				Optional<AccountEntity> accountEntity = accountJPA.findById(name);
				List<CartEntity> cartList = cartJPA.findByAccountID(name);
				if (accountEntity.isPresent()) {
					model.addAttribute("account", accountEntity.get());
					model.addAttribute("sanpham", cartList);
					model.addAttribute("total", cartService.getAmount());
					break;
				}
			}
		}
		return "client/laphoadon";
	}

	@PostMapping("/taohoadon")
	public String postTaoHoaDon(@Valid Account account, BindingResult error, Model model, @RequestParam("id") String id,
			@RequestParam("fullname") String fullname, @RequestParam("phone") String phone,
			@RequestParam("address") String address) {
		if (!fullname.equals("") && !phone.equals("") && !address.equals("")) {
			if (phone.matches("^0\\d{9}$")) {
				String sdt = billsJPA.findPhoneById(id);
				List<AccountEntity> accEntity = accountJPA.findAll();
				for (AccountEntity ac : accEntity) {
					if (sdt.equals(phone)) {
						System.out.println(sdt);
						int accountEntity = billsJPA.updateByAccountId(fullname, phone, address, id);
						return "redirect:/index";
					} else {
						if (phone.equals(ac.getPhone())) {
							model.addAttribute("message2", "Số điện thoại này đã tồn tại!");
							return "client/laphoadon";
						}
					}
				}
			} else {
				model.addAttribute("message2", "Số điện thoại không hợp lệ!");
				return "client/laphoadon";
			}
			int accountEntity = billsJPA.updateByAccountId(fullname, phone, address, id);
			return "redirect:/index";

		} else {
			if (fullname.equals("")) {
				model.addAttribute("message1", "Vui lòng nhập tên");
			} else if (phone.equals("")) {
				model.addAttribute("message2", "Vui lòng nhập số điện thoại");
			} else if (address.equals("")) {
				model.addAttribute("message3", "Vui lòng nhập địa chỉ");
			}
			return "client/laphoadon";
		}
	}

}
