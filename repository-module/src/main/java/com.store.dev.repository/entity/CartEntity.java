package com.store.dev.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author 铭
 * 2018/11/8 8:00
 */
@Data
@Table(name = "tb_cart")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class CartEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private Long userId;
    private Long itemId;
    private Long goodsNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId", insertable = false, updatable = false)
    private ItemEntity itemEntity;

}
