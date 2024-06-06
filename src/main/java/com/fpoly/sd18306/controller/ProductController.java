package com.fpoly.sd18306.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.jpa.ProductJPA;
import com.fpoly.sd18306.models.Product;

@Controller
public class ProductController {
	@Autowired
	ProductJPA productJPA;
	
	@GetMapping("/productsManager")
	public String product() {
		
		return "/admin/qlsanpham";
	}
	
	@GetMapping("/add-productsManager")
	public String AddProduct() {
		return "/admin/qlsanpham";
	}
	
	@GetMapping("/add-productsManager")
	public String AddProductSave(Product product, RedirectAttributes redirectAttributes) {
		try {
			ProductEntity productEntity;
			if (product.getId() != 0 && product.getId() > 0) {
				Optional<ProductEntity> existingProduct = productJPA.findById(product.getId());
				if (existingProduct.isPresent()) {
					productEntity = existingProduct.get();
				} else {
					productEntity = new ProductEntity();
				}
			} else {
				productEntity = new ProductEntity();
			}

			productEntity.setProduct_name(product.getProduct_name());
			productEntity.setPrice(product.getPrice());
			productEntity.setIn_stock(product.getIn_stock());
			productEntity.setDescription(product.getDescription());

//			Optional<CategoryEntity> catOptional = categoryJPA.findById(String.valueOf(product.getCatID()));
//			if (catOptional.isPresent()) {
//				productEntity.setCategoryEntity(catOptional.get());
//			}

			// Save product
//			ProductEntity prodSaveEntity = productJPA.save(productEntity);

			// Save images
			for (MultipartFile file : product.getFile()) {
				String fileName = UploadService.UploadFile(file);
				if (fileName != null) {
//					ImageEntity imageEntity = new ImageEntity();
//					imageEntity.setName(fileName);
//					imageEntity.setProductEntity(prodSaveEntity);
//					imageJPA.save(imageEntity);
				}
			}
			// Thêm thông báo thành công
			redirectAttributes.addFlashAttribute("message", "Lưu sản phẩm thành công!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} catch (Exception e) {
			// Thêm thông báo thất bại
			redirectAttributes.addFlashAttribute("message", "Lưu sản phẩm thất bại. Vui lòng thử lại.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		return "/admin/qlsanpham";
	}
}
