package com.fpoly.sd18306.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.BillEntity;
import com.fpoly.sd18306.jpa.BillsJPA;

@Controller
public class qldonhangController {
	@Autowired
	BillsJPA billsJPA;

	@GetMapping("admin/qldonhang")
	public String getQlDonHang(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size, Model model) {

		Pageable pageable = PageRequest.of(page, size);
		Page<BillEntity> billPage = billsJPA.findAll(pageable);

		model.addAttribute("donhangall", billPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", billPage.getTotalPages());
		model.addAttribute("size", size);

		return "admin/qldonhang";
	}
	@GetMapping("/refreshForm2")
	public String refreshForm2() {
		return "redirect:/admin/qldonhang";
	}
	
	 @PostMapping("/searchAdminCart")
	    public String searchProducts(Model model, @RequestParam("account_id") String account_id) {
	        // Perform search using the account_id
	        List<BillEntity> billList = billsJPA.findByacId(account_id);

	        if (billList.isEmpty()) {
	            model.addAttribute("notFoundMessage", "Không tìm thấy dữ liệu cho mã khách hàng: " + account_id);
	            model.addAttribute("donhangall", new ArrayList<BillEntity>()); // Empty list to clear any previous results
	        } else {
	            // Add search results to the model
	            model.addAttribute("donhangall", billList);
	        }

	        // Reset pagination to first page
	        model.addAttribute("currentPage", 0);
	        // Assuming only one page for search results
	        model.addAttribute("totalPages", 1);
	        // Default size, adjust as necessary
	        model.addAttribute("size", 10);

	        return "admin/qldonhang"; // Return the fragment for AJAX updating
	    }
}
