package com.fpoly.sd18306.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class AccountEntity implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "image")
    private String image;

    @Column(name = "role")
    private boolean role;

    @OneToMany(mappedBy = "account")
    private List<BillEntity> bills;

    @OneToMany(mappedBy = "accountEntity")
    private List<CartEntity> carts;
}
