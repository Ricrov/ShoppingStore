package com.store.dev.order.service.impl;

import com.store.dev.repository.dao.order.OrderRepository;
import com.store.dev.order.service.OrderService;
import com.store.dev.repository.entity.order.OrderEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: MQ
 * @Date: 2018/11/7 11:50
 */
@Service
public class OrderServiceImpl implements OrderService {

   @Resource
   private OrderRepository orderRepository;


    @Override
    public int totalCount() {
        return 0;
    }

    @Override
    public List<OrderEntity> QueryOrder() {
      return orderRepository.QueryAllOrders();


    }
}
