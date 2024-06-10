package com.fpoly.sd18306.components;

import java.net.URLEncoder;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getCookies() != null) {
			boolean check = false;
			
			for(Cookie cookie : request.getCookies()) {
				if(cookie.getName().equals("id")) {
					check = true;
					break;
				}
			}
			if(check) {
				return true;
			}
		}
		 String redirectUrl = String.format("/login?path=%s", URLEncoder.encode(request.getRequestURI(), "UTF-8"));
		System.out.println("Redirect URL: " + redirectUrl);
        response.sendRedirect(redirectUrl);
		return false;
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
