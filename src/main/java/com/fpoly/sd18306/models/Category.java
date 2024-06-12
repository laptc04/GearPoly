package com.fpoly.sd18306.models;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
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
	
	private String categories_name;
	
	private String image;
}
