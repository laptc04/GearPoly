package com.fpoly.sd18306.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fpoly.sd18306.entities.DetailBillEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.jpa.BillsJPA;
import com.fpoly.sd18306.jpa.DetailsBillJPA;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ChiTietHoaDonController {
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	BillsJPA billsJPA;
	
	@Autowired
	DetailsBillJPA detailsbillJPA;

	@Autowired
	AccountJPA accountJPA;
	

	@GetMapping("/chitiet")
	public String chiTiet(Model model, 
	                      @RequestParam("id") Optional<Integer> idOptional,
	                      @RequestParam("page") Optional<Integer> page,
	                      @RequestParam("size") Optional<Integer> size) {
	    if (idOptional.isPresent()) {
	        int currentPage = page.orElse(1);
	        int pageSize = size.orElse(5); // Kích thước trang mặc định là 5

	        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
	        Page<DetailBillEntity> detailsPage = detailsbillJPA.findByBillId(idOptional.get(), pageable);

	        model.addAttribute("hoadon", detailsPage.getContent());
	        model.addAttribute("total", billsJPA.findTotalById(idOptional.get()));
	        model.addAttribute("billdate", billsJPA.findBillDateById(idOptional.get()));
	        model.addAttribute("currentPage", currentPage);
	        model.addAttribute("totalPages", detailsPage.getTotalPages());
	        model.addAttribute("totalItems", detailsPage.getTotalElements());
	        model.addAttribute("id", idOptional.get()); // Thêm id vào model để sử dụng trong Thymeleaf

	        if (detailsPage.getTotalPages() > 1) {
	            model.addAttribute("showPagination", true);
	        } else {
	            model.addAttribute("showPagination", false);
	        }
	    }
	    return "client/chitiethoadon";
	}



}
