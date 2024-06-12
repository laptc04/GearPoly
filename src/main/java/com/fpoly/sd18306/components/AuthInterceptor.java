package com.fpoly.sd18306.components;

import java.net.URLEncoder;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fpoly.sd18306.entities.AccountEntity;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getCookies() != null) {
            boolean check = false;

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("id")) {
                    check = true;
                    break;
                }
            }
            if (check) {
                HttpSession session = request.getSession();
                AccountEntity accountEntity = (AccountEntity) session.getAttribute("account");
                
                //kiểm tra tài khoản đăng nhập
                if (!accountEntity.isRole() && isAdminPath(request.getRequestURI())) {
                	//không phải tài khoản admin sẻ trả về index
                    response.sendRedirect("/index");
                    return false;
                }
                
                return true;
            }
        }
        
        String redirectUrl = String.format("/login?path=%s", URLEncoder.encode(request.getRequestURI(), "UTF-8"));
        System.out.println("Redirect URL: " + redirectUrl);
        response.sendRedirect(redirectUrl);
        return false;
    }

    private boolean isAdminPath(String path) {
        // kiểm tra đường dẫn có đúng của trang admin không
        return path.startsWith("/admin/");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.printf("Post handle: %s%n", request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.printf("After handle: %s%n", request.getRequestURI());
    }
}

