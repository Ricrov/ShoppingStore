package com.store.dev.order.service;

import com.store.dev.repository.entity.order.OrderEntity;

import java.util.List;

/**
 * @Author: MQ
 * @Date: 2018/11/7 11:48
 */
public interface OrderService {

    int totalCount();

    List<OrderEntity> QueryOrder();
}
