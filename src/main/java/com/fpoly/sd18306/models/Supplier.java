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
public class Supplier {

	private int id;

	@NotEmpty(message = "Tên không được bỏ trống")
	@Length(min = 3, message = "Tên không được dưới 3 kí tự")
	private String supplier_name;
	
	@NotEmpty(message = "Giá không được bỏ trống")
	private int price;
	
	@NotEmpty(message = "Mô tả không được bỏ trống")
	private String contact_info;
		
	private List<MultipartFile> images;
}
