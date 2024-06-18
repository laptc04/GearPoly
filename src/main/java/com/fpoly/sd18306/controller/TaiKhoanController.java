package com.fpoly.sd18306.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpoly.sd18306.entities.AccountEntity;
import com.fpoly.sd18306.entities.BillEntity;
import com.fpoly.sd18306.jpa.AccountJPA;
import com.fpoly.sd18306.models.Account;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
public class TaiKhoanController {
	@Autowired
	AccountJPA accountJPA;
	@Autowired
	HttpServletResponse response;
	
	@Autowired 
	HttpSession httpSession;
	
	@Autowired
	HttpServletRequest request;

	
	@GetMapping("/index")
	public String index( Model model) {
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				String accountId = cookie.getValue();
				Optional<AccountEntity> accountEntity = accountJPA.findById(accountId);
				AccountEntity account = accountJPA.findById(accountId).orElse(null);
				if (account != null) {
					model.addAttribute("account", accountEntity.get());
				}
			}
		}
		return "client/index";
	}
	@GetMapping("/admin/index")
	public String adminIndex() {
		
		return "admin/index";
	}
	 @GetMapping("/register")
	    public String registerForm(Model model) {
	        model.addAttribute("account", new Account());
	        return "client/register";
	    }

	@PostMapping("/register")
	public String registerSave(@Valid Account account, BindingResult error,
	                           @RequestParam("id") String id,
	                           @RequestParam("fullname") String fullname,
	                           @RequestParam("email") String email,
	                           @RequestParam("password") String password,
	                           Model model) {
		//kiểm tra ID
	    Optional<AccountEntity> existingAccountById = accountJPA.findById(account.getId());
	    //kiểm tra email
	    Optional<AccountEntity> existingAccountByEmail = accountJPA.findByEmail(account.getEmail());
	    if (error.hasErrors()) {
	        model.addAttribute("error", error);
	    }
	    if(existingAccountById.isPresent()) {
	        model.addAttribute("messregerr1", "*Tên đăng nhập đã tồn tại");

	    } else if (existingAccountByEmail.isPresent()) {
	        model.addAttribute("messregerr2", "*Email đã tồn tại");

	    } else {
	        AccountEntity accountEntity = new AccountEntity();
	        accountEntity.setId(id);
	        accountEntity.setFullname(fullname);
	        accountEntity.setEmail(email);
	        accountEntity.setPassword(password);
	        accountEntity.setPhone("");
	        accountEntity.setAddress("");
	        accountEntity.setImage("avttrang.jpg");
	        accountEntity.setRole(false);
	        accountJPA.save(accountEntity);
	        model.addAttribute("messreg", "Đăng ký thành công");
	    }

	    model.addAttribute("account", account);
	    return "client/register";
	}

	@GetMapping("/login")
	public String login(@RequestParam(name="path", defaultValue= "") String path, Model model) {
		System.out.println("Login path: " + path);
		model.addAttribute("path", path);
		return "client/login";
	}
	
	@PostMapping("/login")
	public String loginSave(@RequestParam("id") String id,
				            @RequestParam("password") String password,
				            HttpServletResponse response,
				            HttpServletRequest request,
				            Model model) {
		String path = request.getParameter("path");
		System.out.println("Login save path: " + path);
		AccountEntity accountEntity = accountJPA.findByIdAndPassword(id, password);
        if (accountEntity != null) {
        	Cookie cookie = new Cookie("id", id);
			response.addCookie(cookie);
	     	httpSession.setAttribute("account", accountEntity);
	     	//kiểm tra role
	        if (accountEntity.isRole() == true) {
	            return "redirect:/admin/index";
	        } else if (accountEntity.isRole() == false) {
	            return "redirect:/index";
	        }
	        //
			if(path == null || path.isEmpty()) {
				return "redirect:/index";
			}else {
				return String.format("redirect:%s", path);
			}
        } else {
            model.addAttribute("message", "*Mật khẩu hoặc tên đăng nhập không chính xác");
            return "client/login";
        }
	}
	@GetMapping("/changePassword")
    public String changePasswordPage(HttpServletRequest request, Model model) {
        String userId = getUserIdFromCookies(request);
        if (userId != null) {
            model.addAttribute("userId", userId);
            return "client/changepassword";
        } else {
            return "client/login";
        }
    }

    @PostMapping("/changePassword")
    @Transactional
    public String changePassword(@RequestParam("id") String id,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        model.addAttribute("userId", id);

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("message2", "*Mật khẩu mới và xác nhận mật khẩu không khớp");
            return "client/changepassword";
        }

        AccountEntity accountEntity = accountJPA.findByIdAndPassword(id, oldPassword);
        if (accountEntity != null) {
            accountEntity.setPassword(newPassword);
            accountJPA.save(accountEntity);
            model.addAttribute("message3", "Đổi mật khẩu thành công. Vui lòng đăng nhập lại.");
            return "redirect:/login";
        } else {
            model.addAttribute("message2", "*Mật khẩu cũ không chính xác");
            return "client/changepassword";
        }
    }
    private String getUserIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("id", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }
}