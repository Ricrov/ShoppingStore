package com.store.dev.service.impl;

import com.store.dev.Jedis.JedisClient;
import com.store.dev.repository.commons.MallResult;
import com.store.dev.repository.dao.TbOrderItemRepository;
import com.store.dev.repository.dao.TbOrderRepository;
import com.store.dev.repository.dao.TbOrderShippingRepository;
import com.store.dev.repository.dao.UserEntityRepository;
import com.store.dev.repository.entity.*;
import com.store.dev.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 订单处理Service
 *
 * @Author: MQ
 * @Date: 2018/11/7 19:38
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Resource
    private UserEntityRepository userEntityRepository;

    @Resource
    private TbOrderRepository tbOrderRepository;

    @Resource
    private TbOrderItemRepository tbOrderItemRepository;

    @Resource
    private TbOrderShippingRepository tbOrderShippingRepository;

    /**
     * 根据用户Id查询订单
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public UserEntity getOne(Long userId) {
        return userEntityRepository.getOne(userId);
    }

    /**
     * 查询所有用户的订单以及订单项,商品
     *
     * @return
     */
    @Override
    public List<TbOrder> findAll() {
        List<TbOrder> userOrderInfo = tbOrderRepository.findAll();
        return userOrderInfo;
    }

    /**
     * 根据订单编号删除订单
     *
     * @param Order
     */
    @Override
    public TbOrder deleteByID(Long Order) {
        tbOrderRepository.deleteById(Order);
        return null;
    }

    @Transactional
    @Override
    public TbOrder submitOrder(Map<String, Object> itemList) {
        TbOrder tbOrder = new TbOrder();
        TbOrderShipping tbOrderShipping = new TbOrderShipping();

        Float payment = 0F;

        Map<String, Object> list = (Map<String, Object>) itemList.get("itemList");
        String name = (String) list.get("name");
        String phone = (String) list.get("phone");
        String address = (String) list.get("address");
        String paymentType = (String) list.get("paymentType");
        List<Integer> itemIdList = (List<Integer>) list.get("itemIdList");
        List<Integer> numberList = (List<Integer>) list.get("numberList");
        List<Integer> itemPriceList = (List<Integer>) list.get("itemPriceList");
        List<Integer> itemTitleList = (List<Integer>) list.get("itemTitleList");

        for (int i = 0; i < itemIdList.size()
                && i < numberList.size()
                && i < itemPriceList.size()
                && i < itemTitleList.size(); i++) {
            payment += Long.valueOf(numberList.get(i)) * Long.valueOf(itemPriceList.get(i));
        }

        UUID randomUUID = UUID.randomUUID();
        String uuid = randomUUID.toString();
        tbOrder.setOrderUserId(7L);
        tbOrder.setPayment(payment - 10);
        tbOrder.setPaymentType(paymentType);
        tbOrder.setStatus(2);
        tbOrder.setCreateTime(new Date());
        tbOrder.setShippingName("shop快递");
        tbOrder.setShippingCode(uuid);

        TbOrder order = tbOrderRepository.save(tbOrder);

        for (int i = 0; i < itemIdList.size()
                && i < numberList.size()
                && i < itemPriceList.size()
                && i < itemTitleList.size(); i++) {
            TbOrderItem tbOrderItem = new TbOrderItem();
            tbOrderItem.setItemId(Long.valueOf(itemIdList.get(i)));
            tbOrderItem.setOrderId(order.getOrderId());
            tbOrderItem.setNum(Long.valueOf(numberList.get(i)));
            tbOrderItem.setTitle(String.valueOf(itemTitleList.get(i)));
            tbOrderItem.setPrice(Long.valueOf(itemPriceList.get(i)));
            TbOrderItem orderItem = tbOrderItemRepository.save(tbOrderItem);
        }


        tbOrderShipping.setReceiverName(name);
        tbOrderShipping.setReceiverPhone(phone);
        tbOrderShipping.setReceiverAddress(address);
        tbOrderShipping.setReceiverZip("321125");
        tbOrderShipping.setOrderId(order.getOrderId());

        TbOrderShipping orderShipping = tbOrderShippingRepository.save(tbOrderShipping);
        return order;
    }

}
