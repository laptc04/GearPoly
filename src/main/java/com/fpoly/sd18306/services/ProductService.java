package com.fpoly.sd18306.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.jpa.ProductJPA;
import com.fpoly.sd18306.models.Product;

@Service
public class ProductService {

	 @Autowired
	    private ProductJPA productJPA;

	    public Page<ProductEntity> getProducts(int page, int size) {
	        PageRequest pageable = PageRequest.of(page, size);
	        return productJPA.findAll(pageable);
	    }
	    
	    public List<ProductEntity> sortBy(String sortBy) {
	        if (sortBy.equals("product_name")) {
	            return productJPA.findAll(Sort.by(Sort.Direction.ASC, "product_name"));
	        } else if (sortBy.equals("soluong")) {
	            return productJPA.findAll(Sort.by(Sort.Direction.ASC, "soluong"));
	        } else if (sortBy.equals("price")) {
	            return productJPA.findAll(Sort.by(Sort.Direction.ASC, "price"));
	        } else {
	            return productJPA.findAll();
	        }
	    }

}
