package com.store.dev.controller;

import com.store.dev.repository.commons.MallResult;
import com.store.dev.repository.entity.OrderInfo;
import com.store.dev.repository.entity.TbOrder;
import com.store.dev.repository.entity.UserEntity;
import com.store.dev.service.OrderService;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * 订单Controller
 *
 * @Author: MQ
 * @Date: 2018/11/7 14:05
 */
@RestController
public class OrderController {


    @Resource
    private OrderService orderService;


    /**
     * 生成订单处理
     * @param orderInfo
     * @param model
     * @return
     */
//    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
//    public String createOrder(OrderInfo orderInfo, Model model){
//        // 生成订单
//        MallResult order = orderService.createOrder(orderInfo);
//        model.addAttribute("orderId",order.getData().toString());
//        model.addAttribute("payment",orderInfo.getPayment());
//        // 预计送达时间,
//        DateTime dateTime = new DateTime();
//         dateTime = dateTime.plusDays(3);
//         model.addAttribute("data",dateTime.toString("yyyy-MM-dd"));
//
//         return "success";
//    }







    /**
     * 根据用户Id查询订单以及商品详情
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getUserOrder")
    @ResponseBody
    public UserEntity getUserOrder(@Param("userId") Long userId) {
        UserEntity one = orderService.getOne(userId);
        System.out.println(one);
        return one;
    }


    /**
     * 查询所有用户的订单对应的商品信息
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<UserEntity> findAll() {
        List<UserEntity> all = orderService.findAll();
        all.forEach(System.out::println);
        return all;
    }
    /**
     * 根据订单号删除订单信息
     *
     * @param orderId
     */

    @RequestMapping("/deleteById")
    @ResponseBody
    public void delete(@Param("orderId") Long orderId) {
        orderService.deleteByID(orderId);
    }










//        /**
//     * 使用Jpa分页显示订单号所有
//     * @param page
//     * @param size
//     * @return
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    @RequestMapping("/orderList")
//    @ResponseBody
//    public ModelAndView listOrder(@RequestParam(value = "page",defaultValue = "1") int page,
//                                  @RequestParam(value = "size",defaultValue = "5") int size) throws InvocationTargetException, IllegalAccessException {
//        ModelAndView modelAndView = new ModelAndView();
//        PageRequest request = new PageRequest(page-1,size);
//        Page<TbOrder> orderPage = orderService.findAll(request);
//        modelAndView.addObject(orderPage);
//        modelAndView.setViewName("orderList");
//
//        return modelAndView;
//    }
//
//    /**
//     * 使用Jpa分页显示模糊查询
//     * @param request
//     * @param page
//     * @param size
//     * @return
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    @RequestMapping("/search")
//    public ModelAndView search(HttpServletRequest request,
//                               @RequestParam(value = "page",defaultValue = "1") int page,
//                               @RequestParam(value = "size",defaultValue = "5")int size) throws InvocationTargetException, IllegalAccessException {
//        ModelAndView modelAndView = new ModelAndView();
//        String query = (request.getParameter("query")).trim();
//        PageRequest pageRequest = new PageRequest(page-1,size);
//        Page<TbOrder> search = orderService.findSearch(query, pageRequest);
//        modelAndView.addObject("search",search);
//        modelAndView.addObject("query",query);
//        modelAndView.setViewName("OrderList");
//        return modelAndView;
//    }

}
