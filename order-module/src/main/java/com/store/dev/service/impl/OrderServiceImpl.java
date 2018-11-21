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
    private TbOrderItemRepository tbItemRepository;

    @Resource
    private TbOrderShippingRepository tbOrderShippingRepository;


    @Resource
    private JedisClient jedisClient;

    @Value("${ORDER_GEN_KEY}")
    private String ORDER_GEN_KEY;
    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;
    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;


    @Override
    public MallResult createOrder(OrderInfo orderInfo) {
        // 生成订单号,使用redis生成订单
        if (!jedisClient.exists(ORDER_GEN_KEY)) {
            // 判断这个key存在不,设置初始值
            jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
        }
        Long orderId = jedisClient.incr(ORDER_GEN_KEY);
        // 向订单插入数据,补全前端传过来的数据
        orderInfo.setOrderId(orderId);
        // 免邮费
        orderInfo.setPostFee("0");
        // //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        // 订单创建时间
        orderInfo.setCloseTime(new Date());
        orderInfo.setUpdateTime(new Date());
        // 向订单表插入数据
        tbOrderRepository.save(orderInfo);

        // 向订单表插入数据,补全订单表order的entity
        List<TbOrderItem> itemList = orderInfo.getOrderItemList();
        for (TbOrderItem tbOrderItem : itemList) {
            Long oid = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            tbOrderItem.setOrderItemId(oid);
            tbOrderItem.setOrderId(orderId);

            // 向商品明细表插入数据
            tbItemRepository.save(tbOrderItem);
        }

        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderShippingId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());

        // 向订单物流表插入数据
        tbOrderShippingRepository.save(orderShipping);
        // 插入完毕后返回订单号
        return MallResult.ok();
    }

    /**
     * 查询所有订单数量
     * @return
     */
    @Override
    public int totalCount() {
        return tbOrderRepository.totalCount();
    }

    /**
     * 分页以及模糊查询
     * @param page 当前页
     * @param count 一页显示的数量
     * @param searchMsg
     * @return
     */
    @Override
    public List<TbOrder> QueryAll(Integer page,Integer count,String searchMsg) {
        List<TbOrder> orderList = tbOrderRepository.findAll();
        return orderList;
    }






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
    public List<UserEntity> findAll() {
        List<UserEntity> UserOrderInfo = userEntityRepository.findAll();
        return UserOrderInfo;
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


//    /**
//     * 查询所有的订单
//     *
//     * @param pageable 这个类可以帮助实现分页的功能
//     * @return
//     */
//    @Override
//    public Page<TbOrder> findAll(Pageable pageable) throws InvocationTargetException, IllegalAccessException {
//        Page<TbOrder> orderPage = tbOrderRepository.findAll(pageable);
//        List<TbOrder> orderList = TbOrderConverter.convert(orderPage.getContent());
//
//        Page<TbOrder> orderDaoPage = new PageImpl<>(orderList, pageable, orderPage.getTotalElements());
//
//        return orderDaoPage;
//    }
//
//    // 根据用户需求进行模糊查询
//    @Override
//    public Page<TbOrder> findSearch(String query, Pageable pageable) throws InvocationTargetException, IllegalAccessException {
//        Page<TbOrder> search = tbOrderRepository.findSearch(query, pageable);
//        List<TbOrder> orderList = TbOrderConverter.convert(search.getContent());
//
//        Page<TbOrder> orderPage = new PageImpl<>(orderList, pageable, search.getTotalElements());
//        return orderPage;
//    }


}
