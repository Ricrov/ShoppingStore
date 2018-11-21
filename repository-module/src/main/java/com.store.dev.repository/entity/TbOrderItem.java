package com.store.dev.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "tb_order_item")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TbOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;  // 订单详情Id
    private Long itemId;      // 商品Id
    private Long orderId;     // 订单Id
    private Long num;            // 商品购买数量
    private String title;       // 商品标题
    private Long price;          // 商品单价
    private Long totalFee;       // 商品总金额
    private String picPath;     // 商品图片地址


    @OneToMany(targetEntity = TbItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId")
    private List<TbItem> tbItemList;

}
