package com.fpoly.sd18306.models;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	private int id;

	@NotEmpty(message = "Tên không được bỏ trống")
	@Length(min = 3, message = "Tên không được dưới 3 kí tự")
	private String product_name;
	
	@NotEmpty(message = "Giá không được bỏ trống")
	private int price;
	
	@NotEmpty(message = "Số lượng trong kho không được bỏ trống")
	private int in_stock;
	
	@NotEmpty(message = "Mô tả không được bỏ trống")
	private String description;
	
	private List<MultipartFile> file;
}