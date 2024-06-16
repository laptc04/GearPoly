package com.fpoly.sd18306.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String chiTiet(Model model, @RequestParam("id") Optional<String> idOptional) {
		if (idOptional.isPresent()) {
			model.addAttribute("hoadon", detailsbillJPA.findBybillId(idOptional.get()));
			model.addAttribute("total", billsJPA.findTotalById(idOptional.get()));
		} else {
			
		}
		return "client/chitiethoadon";
	}
}
