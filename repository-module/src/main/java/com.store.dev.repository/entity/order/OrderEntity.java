package com.store.dev.repository.entity.order;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


/**
 * 订单 Entity
 */

@Entity
@Table(name = "tb_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;         // 订单Id
    private String payment;         // 付款金额
    private Integer paymentType;    // 支付类型
    private String postFee;         // 邮费(精确到2位小数)
    private Integer status;         // 状态
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;        // 订单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;        // 订单更新时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date paymentTime;       //  付款时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date consignTime;       //  发货时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;           // 交易完成时间
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date closeTime;         // 交易关闭
    private String shippingName;    // 物流名称
    private String shippingCode;    // 物流单号
    private int userId;             // 用户Id
    private String buyerMessage;    // 买家留言
    private String buyerNick;        //买家昵称
    private int buyerRate;          // 买家评价





}
