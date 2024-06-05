package com.fpoly.sd18306.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/")
public class IndexController {

	@GetMapping("/index")
	public String getUserIndex() {
		return "client/index";
	}

	@GetMapping("/dangky")
	public String getDangky() {
		return "client/register";
	}

	@GetMapping("/dangnhap")
	public String getDangnhap() {
		return "client/login";
	}

	@GetMapping("/chitiet")
	public String getchitiet() {
		return "client/chitietsanpham";
	}
	
	@GetMapping("/doimatkhau")
	public String getDoimatkhau() {
	    return "client/changepassword";
	}
	
	@RequestMapping("/giohang")
	public String getGiohang() {
		return "client/giohang";
	}
	
	@RequestMapping("/nguoidung")
	public String getNguoidung() {
		return "client/qlTTngdung";
	}
	
	@RequestMapping("/quenmatkhau")
	public String getQuenmatkhau() {
	    return "client/Forgotpassword";
	}
	
	@RequestMapping("/sanpham")
	public String getSanpham() {
		return "client/sanpham";
	}
	
	@RequestMapping("/taohoadon")
	public String getTaohoadon() {
	    return "client/laphoadon";
	}

}
