package com.fpoly.sd18306.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Bills")
public class BillEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnoreProperties(value = "accountEntity")
    private AccountEntity account;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bill_date")
    private Date billDate;

    @Column(name = "total")
    private int total;

    @OneToMany(mappedBy = "bill")
    private Set<DetailBillEntity> detailsBill;
}
