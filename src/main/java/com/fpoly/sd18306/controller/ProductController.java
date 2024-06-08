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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fpoly.sd18306.entities.CategoryEntity;
import com.fpoly.sd18306.entities.ImageEntity;
import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.jpa.CategoryJPA;
import com.fpoly.sd18306.jpa.ImageJPA;
import com.fpoly.sd18306.jpa.ProductJPA;
import com.fpoly.sd18306.models.Product;
import com.fpoly.sd18306.services.ProductService;
import com.fpoly.sd18306.services.UploadService;

@Controller
public class ProductController {
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CategoryJPA categoryJPA; 
	
	@Autowired
	ImageJPA imageJPA;
	
	@Autowired
	UploadService uploadService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/productsManager")
	public String product(Model model) {
		model.addAttribute("products", productJPA.findAll());
		return "admin/qlsanpham";
	}
	
	@GetMapping("/add-productsManager")
	public String AddProduct() {
		return "admin/qlsanpham";
	}
	
	@GetMapping("/searchProd")
	public String searchProducts(Model model, @RequestParam("product_name") String product_name) {
	    List<ProductEntity> productList = productJPA.findByName(product_name);
	    model.addAttribute("products", productList);
	    return "admin/qlsanpham";
	}
	
	@GetMapping("/sort-products")
	public String sortProducts(@RequestParam String sortBy, Model model) {
	    List<ProductEntity> sortedProducts = productService.sortBy(sortBy);
	    model.addAttribute("products", sortedProducts);
	    return "admin/qlsanpham";
	}

	
	@PostMapping("/add-productsManager")
	public String addProductSave(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
	    try {
	        ProductEntity productEntity;
	        if (product.getId() > 0) {
	            Optional<ProductEntity> existingProduct = productJPA.findById(product.getId());
	            productEntity = existingProduct.orElseGet(ProductEntity::new);
	        } else {
	            productEntity = new ProductEntity();
	        }

	        productEntity.setProduct_name(product.getProduct_name());
	        productEntity.setPrice(product.getPrice());
	        productEntity.setSoluong(product.getSoluong());
	        productEntity.setDescription(product.getDescription());
	        productEntity.setHien(product.isHien());

	        // Fetch and set category entity
	        Optional<CategoryEntity> catOptional = categoryJPA.findById(String.valueOf(product.getCategories_id()));
	        catOptional.ifPresent(productEntity::setCategoryEntity);

	        // Save product
	        ProductEntity savedProduct = productJPA.save(productEntity);

	        // Save images
	        for (MultipartFile file : product.getFile()) {
	            String fileName = uploadService.uploadFile(file);
	            if (fileName != null) {
	                ImageEntity imageEntity = new ImageEntity();
	                imageEntity.setName(fileName);
	                imageEntity.setProductEntity(savedProduct);
	                imageJPA.save(imageEntity);
	            }
	        }

	        // Add success message
	        redirectAttributes.addFlashAttribute("message", "Lưu sản phẩm thành công!");
	        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
	    } catch (Exception e) {
	        // Add failure message
	        redirectAttributes.addFlashAttribute("message", "Lưu sản phẩm thất bại. Vui lòng thử lại.");
	        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
	    }
	    return "redirect:/productsManager";
	}

	@GetMapping("/update-productsManager")
	public String updateProduct(@RequestParam("id") int id, Model model) {
		Optional<ProductEntity> productEntity = productJPA.findById(String.valueOf(id));
		if (productEntity.isPresent()) {
			model.addAttribute("product", productEntity.get());
		}
		 model.addAttribute("products", productJPA.findAll());
		 model.addAttribute("categories", categoryJPA.findAll());
		return "admin/qlsanpham";
	}

	@PostMapping("/update-productsManager")
	public String updateProductSave(@RequestParam("id") int id, Product product, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			// KHONG CO ID
			ProductEntity productEntity = new ProductEntity();
			productEntity.setId(id);
			productEntity.setProduct_name(product.getProduct_name());
			productEntity.setPrice(product.getPrice());
			productEntity.setSoluong(product.getSoluong());
			productEntity.setDescription(product.getDescription());

			Optional<CategoryEntity> catOptional = categoryJPA.findById(String.valueOf(product.getCategories_id()));
			if (catOptional.isPresent()) {
				productEntity.setCategoryEntity(catOptional.get());
			}

			// CO ID
			ProductEntity prodSaveEntity = productJPA.save(productEntity);

			for (MultipartFile file : product.getFile()) {
				String fileName = uploadService.uploadFile(file);
				if (fileName != null) {
					ImageEntity imageEntity = new ImageEntity();
					imageEntity.setName(fileName);
					imageEntity.setProductEntity(prodSaveEntity);
					imageJPA.save(imageEntity);
				}
			}
			// thông báo thành công
			redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm thành công!");
			redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		} catch (Exception e) {
			// thông báo thất bại
			redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm thất bại. Vui lòng thử lại.");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		}
		return String.format("redirect:/update-productsManager?id=%s", id);
	}
	
	@GetMapping("/refreshForm")
    public String refreshForm() {
        return "redirect:/productsManager";
    }
	
	@PostMapping("/update-status")
    public String ChangesStatusSave(@RequestParam("id") int id, @RequestParam("status") boolean status,
                               RedirectAttributes redirectAttributes) {
        try {
            // Tìm sản phẩm theo id
            ProductEntity product = productJPA.findById(id).orElse(null);
            
            // Nếu sản phẩm tồn tại
            if (product != null) {
                // Cập nhật trạng thái mới
                product.setHien(status);
                
                // Lưu sản phẩm sau khi cập nhật
                productJPA.save(product);
                
                // Thông báo thành công
                redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái sản phẩm thành công!");
                redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            } else {
                // Thông báo sản phẩm không tồn tại
                redirectAttributes.addFlashAttribute("message", "Sản phẩm không tồn tại!");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }
        } catch (Exception e) {
            // Thông báo lỗi nếu có lỗi xảy ra trong quá trình cập nhật
            redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái sản phẩm thất bại. Vui lòng thử lại.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        
        // Chuyển hướng về trang quản lý sản phẩm
        return "redirect:/productsManager";
    }
	
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategoriesEntities() {
	    return categoryJPA.findAll();
	}

}
