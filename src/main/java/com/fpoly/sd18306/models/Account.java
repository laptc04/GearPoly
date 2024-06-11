package com.fpoly.sd18306.models;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
	@NotBlank(message = "Tên đăng nhập không bỏ trống.")
	@NotEmpty(message = "Tên đăng nhập không bỏ trống.")
	@Length(min = 3, max = 7, message = "Tên đăng nhập phải có 3 - 7 kí tự.")
	private String id;
	@NotBlank(message = "Họ và tên không bỏ trống.")
	@NotEmpty(message = "Họ và tên không bỏ trống.")
	private String fullname;
	@NotBlank(message = "Password không bỏ trống.")
	@NotEmpty(message = "Password không bỏ trống.")
	private String password;
	@NotBlank(message = "Email không bỏ trống.")
	@NotEmpty(message = "Email không bỏ trống.")
	@Email
	private String email;
	@NotBlank(message = "Số điện thoại không bỏ trống.")
	@NotEmpty(message = "Số điện thoại không bỏ trống.")
	private String phone;
	@NotBlank(message = "Địa chỉ không bỏ trống.")
	@NotEmpty(message = "Địa chỉ không bỏ trống.")
	private String address;
	private String image;
	boolean role;
	
}
