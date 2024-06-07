package com.fpoly.sd18306.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "accounts")
public class UseAdEntity implements Serializable{
@Id
@Column(name = "id", length = 255)
private String id;
@Column(name = "fullname", length = 255)
private String fullname;
@Column(name = "email", length = 255)
private String email;
@Column(name = "phone", length = 20)
private String phone;
@Column(name = "address", length = 255)
private String address;
@Column(name = "image", length = 255)
private String image;
@Column(name = "role")
private boolean role;
}

