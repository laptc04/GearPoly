package com.fpoly.sd18306.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BillController {

	@GetMapping("/user/nguoidung")
	public String getMethodName() {
		return "client/qlTTngdung";
	}
	
}
