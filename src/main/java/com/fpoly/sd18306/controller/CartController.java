package com.fpoly.sd18306.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.CartEntity;
import com.fpoly.sd18306.entities.CategoryEntity;
import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.jpa.CartJpa;
import com.fpoly.sd18306.jpa.CategoryJPA;
import com.fpoly.sd18306.jpa.ProductJPA;
import com.fpoly.sd18306.services.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	@Autowired
	CartJpa cartJpa;

	@Autowired
	ProductJPA productJPA;

	@Autowired
	CartService cartService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	AccountJPA accountJPA;
	
	@Autowired
	HttpSession httpSession;

	@Autowired
	CategoryJPA categoryJPA;
	
	

//	@GetMapping("/index")
//	public String cartList(Model model) {
//		model.addAttribute("cart", cartJpa.findAll());
//		return "client/index";
//		
//	}

	
	@GetMapping("user/index")
	public String getsanpham(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size, Model model) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
	    Page<ProductEntity> productPage = productJPA.findAll(pageable);

	    model.addAttribute("products", productPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", productPage.getTotalPages());
	    return "client/sanpham";
	}

	@GetMapping("user/chitiet")
	public String getchitietsanpham(Model model, @RequestParam("id") int id) {
		model.addAttribute("productID", productJPA.findById(id).orElse(null));
		model.addAttribute("cart", cartService);
		return "client/chitietsanpham";
	}

	@GetMapping("/searchProIndex")
	public String searchProducts() {

		return "client/sanpham";
	}

//	@PostMapping("/searchProIndex")
//	public String searchProducts(Model model, @RequestParam("product_name") String product_name) {
//		List<ProductEntity> productList = productJPA.findByName(product_name);
//		model.addAttribute("products", productList);
//		return "client/sanpham";
//	}
	
	@PostMapping("/searchProIndex")
	public String searchProducts(Model model, @RequestParam("product_name") String productName) {
	    List<ProductEntity> productList = productJPA.findByName(productName);
	    model.addAttribute("products", productList);

	    // Khởi tạo currentPage và totalPages cho việc phân trang khi tìm kiếm
	    model.addAttribute("currentPage", 0); // hoặc giá trị mặc định khác
	    model.addAttribute("totalPages", 1); // Giả sử có ít nhất một trang

	    return "client/sanpham";
	}
	@GetMapping("/searchMinMaxIndex")
	public String searchByPriceRange(Model model, @RequestParam(value = "minPrice", required = false) Double minPrice,
			@RequestParam(value = "maxPrice", required = false) Double maxPrice) {
		List<ProductEntity> productList;

		if (minPrice != null && maxPrice != null) {
			productList = productJPA.findByPriceBetween(minPrice, maxPrice);
		} else {
			productList = productJPA.findAll();
		}
		model.addAttribute("currentPage", 0); // hoặc giá trị mặc định khác
		model.addAttribute("totalPages", 1);
		model.addAttribute("products", productList);

		return "client/sanpham";
	}
	@GetMapping("/search-by-categoryIndex")
    public String searchByCategory(@RequestParam("categories_id") int categoryId,
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size, Model model) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
	    Page<ProductEntity> productPage;

	    if (categoryId == -1) {
	        productPage = productJPA.findAll(pageable);  // Hiển thị tất cả sản phẩm nếu không chọn danh mục
	    } else {
	        productPage = productJPA.findByCategoryEntityId(categoryId, pageable);
	    }
	    model.addAttribute("products", productPage.getContent());
	    model.addAttribute("currentPage", productPage.getNumber());
	    model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("categories", categoryJPA.findAll());  // Thêm danh sách danh mục vào model
        model.addAttribute("selectedCategory", categoryId);  // Lưu giá trị danh mục được chọn vào model

        return "client/sanpham";  // Trả về trang hiển thị sản phẩm
    }
	
	@ModelAttribute("categories")
	public List<CategoryEntity> getCategoriesEntities() {
		return categoryJPA.findAll();
	}

	@GetMapping("/cart")
	public String cartList(Model model) {
//		model.addAttribute("cart", cartJpa.findAll());
		
	    AccountEntity account = (AccountEntity) httpSession.getAttribute("account");
	    if (account ==null) {
	    	return "redirect:/login";
		}
	    List<CartEntity> cartList =cartJpa.findByAccountID(account.getId());
	    if (cartList != null) {
			model.addAttribute("cart", cartList);
			httpSession.setAttribute("cart", cartList);
			model.addAttribute("total", cartService.getAmount());
		}

		return "client/giohang";
	}

	@PostMapping("/add-to-cart")
	public String updateCart(Model model, @RequestParam("id") int id, @RequestParam("path") String path,
	                         @RequestParam(name = "quantity", defaultValue = "1") int quantity) {

	    // Lấy tài khoản từ session
	    AccountEntity account = (AccountEntity) httpSession.getAttribute("account");
	    if (account == null) {
	        // Nếu tài khoản không tồn tại trong session, chuyển hướng đến trang đăng nhập
	        return "redirect:/login";
	    }

	    // Tìm kiếm sản phẩm theo id
	    Optional<ProductEntity> productOptional = productJPA.findById(String.valueOf(id));
	    if (productOptional.isPresent()) {
	        ProductEntity product = productOptional.get();

	        // Tìm kiếm sản phẩm trong giỏ hàng của tài khoản
	        Optional<CartEntity> existingCartOptional = cartJpa.findByAccountEntityAndProductEntity(account, product);
	        if (existingCartOptional.isPresent()) {
	            // Nếu sản phẩm đã tồn tại trong giỏ hàng, tăng số lượng
	            CartEntity existingCart = existingCartOptional.get();
	            existingCart.setQuantity(existingCart.getQuantity() + quantity);
	            cartJpa.save(existingCart);
	        } else {
	            // Nếu sản phẩm chưa tồn tại trong giỏ hàng, tạo mới một bản ghi
	            CartEntity cartEntity = new CartEntity();
	            cartEntity.setProductEntity(product);
	            cartEntity.setAccountEntity(account);
	            cartEntity.setPrice(product.getPrice()); // Hoặc sử dụng cartService.getAmount() nếu cần thiết
	            cartEntity.setQuantity(quantity);
	            cartJpa.save(cartEntity);
	        }

	        // Chuyển hướng đến trang giỏ hàng hoặc trang trước đó
	        return "redirect:/cart";
	    }

	    // Nếu sản phẩm không tồn tại, chuyển hướng đến trang lỗi
	    return "redirect:/error";
	}



		


	@GetMapping("/remove-to-cart")
	public String removeCar(@RequestParam("id") int id) {
		Optional<CartEntity> cart =cartJpa.findById(String.valueOf(id));
		if (cart.isPresent()) {
			cartJpa.delete(cart.get());
		}
		
		return "redirect:/cart";
	}

	//
	@GetMapping("/update-to-cart")
	public String updateCart(@RequestParam("id") int id, @RequestParam("quantity") int quantity) {
		if (quantity >= 1) {
			cartService.update(id, quantity);
		} else if (quantity < 1) {
			cartService.remove(id);
		}
		return "redirect:/cart";
	}
}
