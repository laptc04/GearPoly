package com.fpoly.sd18306.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.CartEntity;
import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.jpa.CartJpa;
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
	
	

//	@GetMapping("/index")
//	public String cartList(Model model) {
//		model.addAttribute("cart", cartJpa.findAll());
//		return "client/index";
//		
//	}
	@GetMapping("user/index")
	public String getsanpham(Model model) {
		model.addAttribute("products", productJPA.findAll());
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

		return "client/index";
	}

	@PostMapping("/searchProIndex")
	public String searchProducts(Model model, @RequestParam("product_name2") String product_name2) {
		List<ProductEntity> productList = productJPA.findByName(product_name2);
		model.addAttribute("products", productList);
		return "client/index";
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

	        // Tạo đối tượng CartEntity
	        CartEntity cartEntity = new CartEntity();
	        cartEntity.setProductEntity(product);
	        cartEntity.setAccountEntity(account);
	        cartEntity.setPrice( cartService.getAmount()); // Hoặc sử dụng cartService.getAmount() nếu cần thiết
	        cartEntity.setQuantity(quantity);

	        // Lưu đối tượng CartEntity vào cơ sở dữ liệu
	        cartJpa.save(cartEntity);

	        // Chuyển hướng đến trang giỏ hàng hoặc trang trước đó
	        return "redirect:/cart" ;
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
