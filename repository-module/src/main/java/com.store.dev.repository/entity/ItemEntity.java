package com.store.dev.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "tb_item")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class ItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String title;
    private String sellPoint;
    private Long price;
    private Long num;
    private String barcode;
    private String image;
    private Long cid;
    private Long status;
    private java.sql.Timestamp created;
    private java.sql.Timestamp updated;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemId", referencedColumnName = "itemDescId", insertable = false, updatable = false)
    private ItemDescEntity itemDescEntity;

}
