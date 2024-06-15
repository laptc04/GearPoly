package com.fpoly.sd18306.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "detailsBill")
public class DetailBillEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    @JsonIgnoreProperties(value = "productEntity")
    public ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonIgnoreProperties(value = "billEntity")
    public BillEntity bill;

    @Column(name = "price")
    public int price;

    @Column(name = "quantity")
    public int quantity;
    
    @Column(name = "total_price")
    public int total_price;
}