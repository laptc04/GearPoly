package com.fpoly.sd18306.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.BillEntity;
import com.fpoly.sd18306.entities.DetailBillEntity;
import com.fpoly.sd18306.entities.ImageEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.jpa.BillsJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

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
				AccountEntity account = accountJPA.findById(accountId).orElse(null);
				if (account != null) {
                    Pageable pageable = PageRequest.of(page, size);
                    Page<BillEntity> billPage = billsJPA.findByAccount(account, pageable);
                    List<BillEntity> billEntity = new ArrayList<>(billPage.getContent());
                    Collections.sort(billEntity, (b1, b2) -> Integer.compare(b2.getId(), b1.getId()));
                    List<List<String>> imageNamesList = new ArrayList<>();
                    for (BillEntity bill : billEntity) {
                        List<String> imageNames = new ArrayList<>();
                        for (DetailBillEntity details : bill.getDetailsBill()) {
                            List<ImageEntity> images = details.getProduct().getImageEntities();
                            if (!images.isEmpty()) {
                                imageNames.add(images.get(0).getName());
                            }
                        }
                        imageNamesList.add(imageNames);
                    }
                    
                    model.addAttribute("bills", billEntity);
                    model.addAttribute("imageNamesList", imageNamesList);
                    model.addAttribute("currentPage", page);
                    model.addAttribute("totalPages", billPage.getTotalPages());
                }
			}
		}
		return "client/qlTTngdung";
	}
	
	
	
}
