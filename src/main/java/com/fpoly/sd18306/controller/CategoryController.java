package com.fpoly.sd18306.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.sd18306.entities.CategoryEntity;
import com.fpoly.sd18306.jpa.CategoryJPA;
import com.fpoly.sd18306.models.Category;
import com.fpoly.sd18306.services.UploadService;

import jakarta.validation.Valid;

@Controller
public class CategoryController {
	@Autowired
	CategoryJPA categoryJPA;

	@Autowired
	UploadService uploadService;

	@GetMapping("/categoriesManager")
    public String categoriesPage(Model model, @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryEntity> categoryPage = categoryJPA.findAll(pageable);
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        return "admin/qldanhmuc";
    }

    @GetMapping("/searchCate")
    public String searchCategory(Model model, @RequestParam("categories_name") String categories_name,
                                 @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryEntity> categoryPage = categoryJPA.findByName(categories_name, pageable);
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("categories_name", categories_name); // To preserve the search term in the view
        return "admin/qldanhmuc";
    }
	
	@GetMapping("/add-categoriesManager")
	public String addCategories() {

		return "admin/qldanhmuc";
	}
	
	@PostMapping("/add-categoriesManager")
	public String addCategoriesSave(@Valid Category category, BindingResult error,
			@RequestParam("categories_name") String categories_name,
			@RequestParam("file") MultipartFile file, 
			RedirectAttributes redirectAttributes, Model model,
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
//		if (file == null || file.isEmpty()) {
//		    error.rejectValue("image", "error.image", "Hình ảnh không được bỏ trống");
//		}
//		if (error.hasErrors()) {
//			Pageable pageable = PageRequest.of(page, size);
//	        Page<CategoryEntity> categoryPage = categoryJPA.findAll(pageable);
//	        model.addAttribute("categories", categoryPage.getContent());
//	        model.addAttribute("currentPage", page);
//	        model.addAttribute("totalPages", categoryPage.getTotalPages());
//	        
//			model.addAttribute("category", category);
//	    	model.addAttribute("error", error);
//	    	return "admin/qldanhmuc";
//	    } else{
	    	try {
				String fileName = uploadService.uploadFile(file);
				CategoryEntity cateEntity = new CategoryEntity();
				cateEntity.setCategories_name(categories_name);
				cateEntity.setImage(fileName);
				categoryJPA.save(cateEntity);
				// Thêm thông báo thành công
				redirectAttributes.addFlashAttribute("message", "Lưu danh mục thành công!");
				redirectAttributes.addFlashAttribute("alertClass", "alert-success");
			} catch (Exception e) {
				// Thêm thông báo thất bại
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", "Lưu danh mục thất bại. Vui lòng thử lại.");
				redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			}
//	    }
		return "redirect:/categoriesManager";
	}
	
	@GetMapping("/update-categoriesManager")
    public String updateCategoryPage(Model model, @RequestParam("id") String id, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
        Optional<CategoryEntity> categoryOptional = categoryJPA.findById(Integer.valueOf(id) );
        if (categoryOptional.isPresent()) {
            model.addAttribute("category", categoryOptional.get());
            Pageable pageable = PageRequest.of(page, size);
    		Page<CategoryEntity> categoryPage = categoryJPA.findAll(pageable);
    		model.addAttribute("categories", categoryPage.getContent());
    		model.addAttribute("currentPage", page);
    		model.addAttribute("totalPages", categoryPage.getTotalPages());
            return "admin/qldanhmuc";
        } else {
            return "redirect:/categoriesManager";
        }
    }

    @PostMapping("/update-categoriesManager")
    public String updateCategorySave(@Valid Category category, BindingResult error, 
    								@RequestParam("id") int id,
                                    @RequestParam("categories_name") String categories_name,
                                    @RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes, Model model,
                         			@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
//    	if (file == null || file.isEmpty()) {
//    	    error.rejectValue("image", "error.image", "Hình ảnh không được bỏ trống");
//    	}
//		if (error.hasErrors()) {
//			Pageable pageable = PageRequest.of(page, size);
//	        Page<CategoryEntity> categoryPage = categoryJPA.findAll(pageable);
//	        model.addAttribute("categories", categoryPage.getContent());
//	        model.addAttribute("currentPage", page);
//	        model.addAttribute("totalPages", categoryPage.getTotalPages());
//	        
//			model.addAttribute("category", category);
//	    	model.addAttribute("error", error);
//	    	return "admin/qldanhmuc";
//	    } else{
	    	try {
	            Optional<CategoryEntity> categoryOptional = categoryJPA.findById(id);
	            if (categoryOptional.isPresent()) {
	                CategoryEntity cateEntity = categoryOptional.get();
	                cateEntity.setCategories_name(categories_name);
	                if (!file.isEmpty()) {
	                    String fileName = uploadService.uploadFile(file);
	                    cateEntity.setImage(fileName);
	                }
	                categoryJPA.save(cateEntity);
	                redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công!");
	                redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thất bại. Vui lòng thử lại.");
	            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
	        }
//    	}
        return "redirect:/categoriesManager";
    }

//    @PostMapping("/delete-categoriesManager")
//	public String deleteCategory(@RequestParam("id") String id) {
//		Optional<CategoryEntity> categoryOptional = categoryJPA.findById(String.valueOf(id));
//		if(categoryOptional.isPresent()) {
//			CategoryEntity categoryEntity = categoryOptional.get();
//			uploadService.removeFile(categoryEntity.getImage());
//			categoryJPA.delete(categoryEntity);
//		}
//		return "redirect:/categoriesManager";
//	}

	@ModelAttribute("categories")
	public List<CategoryEntity> getCategoriesEntities() {
		return categoryJPA.findAll();
	}
}
