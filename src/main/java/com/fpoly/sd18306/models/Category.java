package com.fpoly.sd18306.models;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	
	private int id;
	
	@NotEmpty(message = "Tên danh mục không được bỏ trống")
	@NotBlank(message = "Tên danh mục không được bỏ trống")
	private String categories_name;
	
	@NotEmpty(message = "Hình ảnh không được bỏ trống")
	@NotBlank(message = "Hình ảnh không được bỏ trống")
	@NotNull
	private String image;
}
