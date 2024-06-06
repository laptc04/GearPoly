package com.fpoly.sd18306.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	@GetMapping("/index")
	public String getAdminIndex2() {
		return "admin/index";
	}

	@GetMapping("/qlnguoidung")
	public String getQlnguoidung() {
		return "admin/qlnguoidung";
	}
	
	@GetMapping("/qlsanpham")
	public String getQlsanpham() {
		return "admin/qlsanpham";
	}
	
	@GetMapping("/qldanhmuc")
	public String getQldanhmuc() {
		return "admin/qldanhmuc";
	}
	
	@GetMapping("/qlnhasanxuat")
	public String getQlnhasanxuat() {
		return "admin/qlhangsx";
	}
	
	@GetMapping("/qlnhacungcap")
	public String getQlmhacungcap() {
		return "admin/qlnhacungcap";
	}
	
	@GetMapping("/thongke")
	public String getThongke() {
		return "admin/thongke";
	}
}
