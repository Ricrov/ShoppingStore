package com.store.dev.controller;

import com.store.dev.repository.entity.TbOrder;
import com.store.dev.repository.entity.UserEntity;
import com.store.dev.service.OrderService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 订单Controller
 *
 * @Author: MQ
 * @Date: 2018/11/7 14:05
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;


    /**
     * 根据用户Id查询订单以及商品详情
     *
     * @param
     * @return
     */
    @RequestMapping("/getOrderId")
    public TbOrder getOrderId(@RequestBody TbOrder tbOrder) {
        String orderId = tbOrder.getOrderId();
        System.out.println(orderId);
        TbOrder one = orderService.getOrderOneId(orderId);
        System.out.println(one);
        return one;
    }


    /**
     * 查询所有用户的订单对应的商品信息
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbOrder> findAll() {
        List<TbOrder> all = orderService.findAll();
        all.forEach(System.out::println);
        return all;
    }

    /**
     * 根据订单号删除订单信息
     *
     * @param
     */

    @RequestMapping("/deleteById")
    public Integer delete(@RequestBody TbOrder tbOrder) {
        String orderId = tbOrder.getOrderId();
        System.out.println(orderId);
        Integer integer = orderService.delOrderId(orderId);
        return integer;
    }


    @PostMapping("/submitOrder")
    public TbOrder Test01(@RequestBody Map<String, Object> itemList) {
        Map<String, Object> list = (Map<String, Object>) itemList.get("itemList");
        TbOrder order = orderService.submitOrder(itemList);
        return order;
    }

}
