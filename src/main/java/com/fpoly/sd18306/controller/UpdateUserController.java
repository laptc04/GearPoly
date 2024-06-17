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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.BillEntity;
import com.fpoly.sd18306.entities.CartEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.jpa.BillsJPA;
import com.fpoly.sd18306.models.Account;

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
	public String updateUser(Account account, Model model, @RequestParam("id") String id,
			RedirectAttributes redirectAttributes) {
		String hoTen = account.getFullname();
		String sdt = account.getPhone();
		String email = account.getEmail();
		String diaChi = account.getAddress();
		String sdtac = billsJPA.findPhoneById(account.getId());
		System.out.println(sdtac);
		if (hoTen.equals("")) {
			model.addAttribute("hoten", "Vui lòng không bỏ trống họ và tên!");
			return "client/qlTTngdung";
		}
		if (sdt.equals("")) {
			model.addAttribute("hoten", "Vui lòng không bỏ trống số điện thoại!");
			return "client/qlTTngdung";
		} else {
			if(sdt.matches("^0[3|8|7|5|9]\\d{8}$")) {
				
				List<AccountEntity> accEntity = accountJPA.findAll();
				for (AccountEntity ac : accEntity) {
					if (sdtac.equals(sdt)) {
						
					}else {
						if (sdt.equals(ac.getPhone())) {
							model.addAttribute("sdt", "Số điện thoại này đã tồn tại!");
							return "client/qlTTngdung";
						}
					}
					
				}
			} else {
				model.addAttribute("sdt", "Số điện thoại không hợp lệ!");
				return "client/qlTTngdung";
			}
		}
		if (!(hoTen.equals("") && sdt.equals("") && email.equals("") && diaChi.equals(""))) {

		}

		redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
		return String.format("redirect:/user/nguoidung");
	}

}
