package com.store.dev.repository.entity;


import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

/**
 * 这个实体类需要进行网络传输
 * @Author: MQ
 * @Date: 2018/11/15 17:40
 */

public class OrderInfo extends TbOrder implements Serializable {

    private List<TbOrderItem> orderItems;

    private TbOrderShipping orderShipping;


    public List<TbOrderItem> getOrderItemList() {
        return orderItems;
    }

    public void setOrderItemList(List<TbOrderItem> orderItemList) {
        orderItems = orderItemList;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
