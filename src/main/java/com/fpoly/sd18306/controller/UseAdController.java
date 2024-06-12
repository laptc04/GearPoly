package com.fpoly.sd18306.controller;

import java.awt.print.Pageable;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.BillEntity;
import com.fpoly.sd18306.entities.UseAdEntity;

import com.fpoly.sd18306.jpa.UseAdJPA;
import com.fpoly.sd18306.services.UploadService;

@Controller
public class UseAdController {

	@Autowired
	UseAdJPA useadJPA;

	@Autowired
	UploadService uploadService;

	@GetMapping("/manauser")
	public String showProdGet(Model model) {
		return showProducts(0, 5, model);
	}

	@PostMapping("/manauser")
	public String showProdPost(Model model) {
		return showProducts(0, 5, model);
	}

	@GetMapping("/manauser/page")
	public String showProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			Model model) {
		Page<AccountEntity> productsPage = useadJPA.findAll(PageRequest.of(page, size));

		model.addAttribute("account", productsPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", productsPage.getTotalPages());
		model.addAttribute("pageSize", size);
		return "/admin/qlnguoidung";
	}

	@GetMapping("/searchByName")
	public String searchProductsByName(@RequestParam(value = "name", required = false) String name, Model model) {
		if (name == null || name.isBlank()) {
			model.addAttribute("errorMessage", "Không tìm được kết quả, vui lòng nhập tên người dùng.");
			return "/admin/qlnguoidung";
		} 
//		else if (!name.equalsIgnoreCase()) {
//	        model.addAttribute("errorMessage", "Người dùng có tên '" + name + "' không tồn tại.");
//	        return "/admin/qlnguoidung";
//		}
		else {
			List<AccountEntity> productEntities = useadJPA.findByNameContainingIgnoreCase(name);
			model.addAttribute("account", productEntities);
			model.addAttribute( name);
			return "/admin/qlnguoidung";
		}	
	}

	@GetMapping("/searchByPhone")
	public String searchProductsByPhone(@RequestParam(value = "phone", required = false) String phone, Model model) {
		if (phone == null || phone.isBlank()) {
			model.addAttribute("errorMessage", "Vui lòng nhập tên người dùng.");
			return "/admin/qlnguoidung";
		} else {
			List<AccountEntity> productEntities = useadJPA.findByPhoneContainingIgnoreCase(phone);
			model.addAttribute("account", productEntities);
			model.addAttribute("phone", phone);
			return "/admin/qlnguoidung";
		}
	}

	@GetMapping("/searchByYear")
	public String searchTotalByYear() {
		return "redirect:/timtheonam";
	}

	@GetMapping("/timtheonam")
	public String timtktheomam(Model model, @RequestParam(value = "bill_date", required = false) String bill_date) {
		if (bill_date == null || bill_date.isBlank()) {
			model.addAttribute("errorMessage", "Vui lòng nhập số năm để xem doanh thu.");
			return "/admin/qlnamtotal";
		}
		List<UseAdEntity> productEntities = useadJPA.findAccountBillsByTotal(bill_date);
		model.addAttribute("account", productEntities);
		model.addAttribute("billDate", bill_date);
		return "/admin/qlnamtotal";
	}

//	@GetMapping("/timtheonam")
//	public String showProdGet1(Model model) {
//		return showProducts(0, 5, model); // Gọi lại hàm showProducts với trang đầu tiên và kích thước trang mặc định
//	}
//
//	@PostMapping("/timtheonam")
//	public String showProdPost1(Model model) {
//		return showProducts(0, 5, model); // Gọi lại hàm showProducts với trang đầu tiên và kích thước trang mặc định
//	}
//
//	@GetMapping("/timtheonam/pages")
//	public String showProducts1(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
//			Model model) {
//		Page<AccountEntity> productsPage = useadJPA.findAll(PageRequest.of(page, size));
//
//		model.addAttribute("account", productsPage.getContent());
//		model.addAttribute("currentPage", page);
//		model.addAttribute("totalPages", productsPage.getTotalPages());
//		model.addAttribute("pageSize", size);
//		return "/admin/qlnamtotal";
//	}
}
