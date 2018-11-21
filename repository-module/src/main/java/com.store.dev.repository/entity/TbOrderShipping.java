package com.store.dev.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: MQ
 * @Date: 2018/11/7 20:31
 * 订单配送信息表的实体类
 */
@Data
@Entity
@Table(name = "tb_order_shipping")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TbOrderShipping  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderShippingId;     // 订单Id
    private String receiverName;    // 收货人全名
    private String receiverPhone;   // 固定电话
    private String receiverMobile;  // 移动电话
    private String receiverState;   // 省份
    private String receiverCity;    // 城市
    private String receiverDistrict;    // 区/县
    private String receiverAddress;     // 收货地址
    private String receiverZip;         // 邮编
    private String orderId;         // 订单id
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date created;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updated;
}
