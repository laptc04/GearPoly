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
@Table(name = "DetailsBill")
public class DetailBillEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    @JsonIgnoreProperties(value = "productEntity")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonIgnoreProperties(value = "billEntity")
    private BillEntity bill;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;
}