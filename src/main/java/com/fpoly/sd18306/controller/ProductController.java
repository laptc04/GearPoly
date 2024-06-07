package com.fpoly.sd18306.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpoly.sd18306.entities.CategoryEntity;
import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.interfaces.ProductService;
import com.fpoly.sd18306.jpa.CategoryJPA;
import com.fpoly.sd18306.jpa.ImageJPA;
import com.fpoly.sd18306.jpa.ProductJPA;

@Controller
public class ProductController {
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CategoryJPA categoryJPA; 
	
	@Autowired
	ImageJPA imageJPA;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/productsManager")
	public String product(Model model) {
		List<ProductEntity> products = productService.findAll();
        model.addAttribute("products", products);
		return "/admin/qlsanpham";
	}
	
	@GetMapping("/add-productsManager")
	public String AddProduct() {
		
		return "/admin/qlsanpham";
	}
//	
//	@GetMapping("/add-productsManager")
//	public String AddProductSave(Product product, RedirectAttributes redirectAttributes) {
//		try {
//			ProductEntity productEntity;
//			if (product.getId() != 0 && product.getId() > 0) {
//				Optional<ProductEntity> existingProduct = productJPA.findById(product.getId());
//				if (existingProduct.isPresent()) {
//					productEntity = existingProduct.get();
//				} else {
//					productEntity = new ProductEntity();
//				}
//			} else {
//				productEntity = new ProductEntity();
//			}
//
//			productEntity.setProductName(product.getProduct_name());
//			productEntity.setPrice(product.getPrice());
//			productEntity.setDescription(product.getDescription());
//			productEntity.setSoluong(product.getSoluong());
//
//			Optional<CategoryEntity> catOptional = categoryJPA.findById(String.valueOf(product.getCatID()));
//			if (catOptional.isPresent()) {
//				productEntity.setCategory(catOptional.get());
//			}
//
//			// Save product
//			ProductEntity prodSaveEntity = productJPA.save(productEntity);
//
//			// Save images
//			for (MultipartFile file : product.getFile()) {
//				String fileName = UploadService.uploadFile(file);
//				if (fileName != null) {
//					ImageEntity imageEntity = new ImageEntity();
//					imageEntity.setImageUrl(fileName);
//					imageEntity.setProduct(prodSaveEntity);
//					imageJPA.save(imageEntity);
//				}
//			}
//			// Thêm thông báo thành công
//			redirectAttributes.addFlashAttribute("message", "Lưu sản phẩm thành công!");
//			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
//		} catch (Exception e) {
//			// Thêm thông báo thất bại
//			redirectAttributes.addFlashAttribute("message", "Lưu sản phẩm thất bại. Vui lòng thử lại.");
//			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
//		}
//		return "/admin/qlsanpham";
//	}
	
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategoriesEntities() {
		return categoryJPA.findAll();
	}
}
