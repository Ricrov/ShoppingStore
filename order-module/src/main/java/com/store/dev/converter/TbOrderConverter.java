package com.store.dev.converter;

import com.store.dev.repository.entity.TbOrder;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MQ
 * @Date: 2018/11/15 17:28
 */
public class TbOrderConverter {
//    public static TbOrder convert(TbOrder order) throws InvocationTargetException, IllegalAccessException {
//        TbOrder orderDao = new TbOrder();
//        BeanUtils.copyProperties(order,orderDao);
//        return orderDao;
//    }
//
//    public static List<TbOrder> convert(List<TbOrder> orderList) throws InvocationTargetException, IllegalAccessException {
//        //lambda表达式，需要 jdk1.8支持
////        return userList.stream().map(e ->
////            convert(e)
////        ).collect(Collectors.toList());
//        List<TbOrder> orderDTOList = new ArrayList<>();
//        for(int i=0;i<orderList.size();i++) {
//            TbOrder orderDTO = new TbOrder();
//            TbOrder tbOrder = orderList.get(i);
//
//            BeanUtils.copyProperties(tbOrder,orderDTO);
//            orderDTOList.add(orderDTO);
//        }
//        return orderDTOList;
//    }
}
