package com.fpoly.sd18306.controller;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.UseAdEntity;
import com.fpoly.sd18306.jpa.UseAdJPA;
import com.fpoly.sd18306.services.UploadService;

@Controller
public class UseAdController {

	@Autowired
	UseAdJPA useadJPA;
	
	@GetMapping("/manauser")
	public String showProdGet(Model model) {
	    return showProducts(0, 5, model); // Gọi lại hàm showProducts với trang đầu tiên và kích thước trang mặc định
	}

	@PostMapping("/manauser")
	public String showProdPost(Model model) {
	    return showProducts(0, 5, model); // Gọi lại hàm showProducts với trang đầu tiên và kích thước trang mặc định
	}

	@GetMapping("/manauser/page")
	public String showProducts(
	        @RequestParam(defaultValue = "0") int page, 
	        @RequestParam(defaultValue = "5") int size,
	        Model model) {
	    Page<UseAdEntity> productsPage = useadJPA.findAll(PageRequest.of(page, size));

	    model.addAttribute("account", productsPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", productsPage.getTotalPages());
	    model.addAttribute("pageSize", size);
	    return "/admin/qlnguoidung";
	}
}
