package com.fpoly.sd18306.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpoly.sd18306.entities.ProductEntity;
import com.fpoly.sd18306.jpa.ProductJPA;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductJPA productJPA;
    
}
