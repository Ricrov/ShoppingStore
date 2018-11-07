package com.store.dev.order.controller;
import com.store.dev.order.service.OrderService;
import com.store.dev.repository.entity.order.OrderEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: MQ
 * @Date: 2018/11/7 14:05
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;



    @RequestMapping("/orderList")
    public List<OrderEntity> orderList(){
      return orderService.QueryOrder();
    }

}
