package com.fpoly.sd18306.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fpoly.sd18306.entities.CategoryEntity;
import com.fpoly.sd18306.jpa.CategoryJPA;
import com.fpoly.sd18306.services.UploadService;

@Controller
public class CategoryController {
	@Autowired
	CategoryJPA categoryJPA;
	
	@Autowired
	UploadService uploadService;
	
	@GetMapping("/categoriesPage")
	public String categoriesPage(Model model) {
		return "/admin/qldanhmuc";
	}
	
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategoriesEntities(){
		return categoryJPA.findAll();
	}
}
