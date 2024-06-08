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
	
	@GetMapping("/categoriesManager")
	public String categoriesPage(Model model) {
		return "admin/qldanhmuc";
	}
	
	@GetMapping("/add-categoriesManager")
	public String addCategories() {
		
		return "admin/qldanhmuc";
	}
	
	@GetMapping("/searchCate")
	public String searchProducts(Model model, @RequestParam("categories_name") String categories_name) {
	    List<CategoryEntity> categoriesList = categoryJPA.findByName(categories_name);
	    model.addAttribute("categories", categoriesList);
	    return "admin/qldanhmuc";
	}
	
	@PostMapping("/add-categoriesManager")
    public String addCategoriesSave(@RequestParam("categories_name") String categories_name
            , @RequestParam("file") MultipartFile file) {
        String fileName = uploadService.uploadFile(file);
        CategoryEntity cateEntity = new CategoryEntity();
        cateEntity.setCategories_name(categories_name);
        cateEntity.setImage(fileName);
        categoryJPA.save(cateEntity);
        return "redirect:/categoriesManager";
    }
	
	@GetMapping("/update-categoriesManager")
    public String updateCate(@RequestParam("id") int id, Model model) {
        Optional<CategoryEntity> categoryEntity = categoryJPA.findById(String.valueOf(id));
        if(categoryEntity.isPresent()) {
            model.addAttribute("categories", categoryEntity.get());
        }
        return "admin/qldanhmuc";
    }
    
    @PostMapping("/update-categoriesManager")
    public String updateCateSave(@RequestParam("id") int id, 
					             @RequestParam("categories_name") String categories_name, 
					             @RequestParam("file") MultipartFile file) {
        Optional<CategoryEntity> categoryOptional = categoryJPA.findById(String.valueOf(id));
        if(categoryOptional.isPresent()) {
            String imageName = uploadService.uploadFile(file);
            CategoryEntity cateEntity = categoryOptional.get();
            cateEntity.setCategories_name(categories_name);
            if(!imageName.isEmpty()) {
                uploadService.removeFile(cateEntity.getImage());
                cateEntity.setImage(imageName);
            }
            categoryJPA.save(cateEntity);
        }
        return "redirect:/update-categoriesManager";
    }
	
    @PostMapping("/delete-categoriesManager")
	public String deleteCategory(@RequestParam("id") String id) {
		Optional<CategoryEntity> categoryOptional = categoryJPA.findById(String.valueOf(id));
		if(categoryOptional.isPresent()) {
			CategoryEntity categoryEntity = categoryOptional.get();
			uploadService.removeFile(categoryEntity.getImage());
			categoryJPA.delete(categoryEntity);
		}
		return "redirect:/categoriesManager";
	}
    
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategoriesEntities(){
		return categoryJPA.findAll();
	}
}
