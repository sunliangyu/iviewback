package com.qust.service;


import com.qust.dao.FoodOrderDao;
import com.qust.dao.OrderDetailDao;
import com.qust.entity.FoodOrder;
import com.qust.entity.OrderDetail;
import com.qust.feignclient.KitchenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class OrderService {
    @Autowired
    FoodOrderDao foodOrderDao;
    @Autowired
    OrderDetailDao orderDetailDao;
    @Autowired
    KitchenClient kitchenClient;
    /**
    * 获取未读取的订单
    *@Param [restaurant]
    *@Return java.lang.Integer
    *@Author 孙良玉
    */
    public int getUnreadCount (Long restaurant){
        int count = foodOrderDao.getCount(restaurant,'a');
        return count;
    }

    /**
    * 获取新订单通知以及当天的接拒订单
    *@Param [restaurant]
    *@Return java.util.Map
    *@Author 孙良玉
    */
    public Map getOrder (Long restaurant){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String today = df.format(calendar.getTime());
        calendar.add(Calendar.DATE,1);
        String tomorrow= df.format(calendar.getTime());
        Map<String, List<Map<String,Object>>> result  = new HashMap<>();
        List<Object[]> re ;
        List<Map<String,Object>> unread ;
        List<Map<String,Object>> access   ;
        List<Map<String,Object>> refuse ;
        re = foodOrderDao.findUnread(restaurant);
        unread = createRes(re);
        result.put("unread",unread);
        re = foodOrderDao.findToday(restaurant,'b',today,tomorrow);
        access = createRes(re);
        result.put("receive",access);
        re = foodOrderDao.findToday(restaurant,'c',today,tomorrow);
        refuse = createRes(re);
        result.put("refuse",refuse);
        return result;
    }



    /**
    *生成map返回到前端
    *@Param [re]
    *@Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    *@Author 孙良玉
    */
    public List<Map<String,Object>> createRes(List<Object[]> re){
        List<Map<String,Object>> result = new ArrayList<>();
        for(Object[] arg: re){
            Map<String,Object> map = new HashMap();
            map.put("msg_id",arg[0]);
            map.put("create_time",arg[1]);
            if((Boolean)arg[2]){
                map.put("title","外卖");
            }else {
                map.put("title","到店");
            }
            result.add(map);
        }
        return result;
    }

    public Map<String,Object>  getOrderById(Long resturant , Long order){
        FoodOrder foodOrder = foodOrderDao.getSimpleOrder(resturant,order);
        StringBuilder  message = new StringBuilder();
        Map<String,Object> result = new HashMap<>();
        if( foodOrder.isFlag() ) {
            message.append("外卖送至").append(foodOrder.getLocation());
        } else {
            message.append("店内送至桌位：").append(foodOrder.getLocation());
        }
        if(foodOrder.getFlow() == 'c'){
            String reason = foodOrder.getReason();
            if( reason != null){
                result.put("reason",reason);
            }
        }
        result.put("location",message.toString());
        result.put("people",foodOrder.getPeople());
        result.put("price",foodOrder.getPrice());
        List<Object[]> detail = orderDetailDao.getOrderById(resturant,order);
        List<String> foods = createFood(detail);
        result.put("foods",foods);
        return result;
    }

    /**
    * 生成food 表示
    *@Param [detail]
    *@Return java.util.List<java.lang.String>
    *@Author 孙良玉
    */
    public List<String> createFood(List<Object[]> detail){
        List<String> result  = new ArrayList<>();
        StringBuilder message =  new StringBuilder();
        for(Object[] food : detail){
            message.append(food[0].toString()).append("  共计").append(food[1].toString()).append("份");
            if(food[2] != null ){
                message.append("  备注").append(food[2].toString());
            }
            result.add(message.toString());
            message.delete(0,message.length());
        }
        return result;
    }

    /**
    *订单处理
    *@Param [restaurant, order, state]
    *@Return void
    *@Author 孙良玉
    */
    public void alterOrder(Long restaurant, Long order , char state ,String reason){
        if(state == 'b'){
            this.receiveOrder(restaurant,order);
        }else if(state == 'c'){
            this.refuseOrder(restaurant,order,reason);
        }
    }

    /**
    *接受订单
    *@Param [restaurant, order]
    *@Return void
    *@Author 孙良玉
    */
    public void receiveOrder (Long restaurant, Long order){
        foodOrderDao.updateFlow(restaurant,order,'b',new java.sql.Date(System.currentTimeMillis()),null);
        Map<String,Object> result = new HashMap<>();
        Boolean flag = foodOrderDao.getFlag(restaurant,order);
        result.put("flag",flag);
        result.put("restaurant",restaurant);
        result.put("order",order);
        List<OrderDetail> arg= orderDetailDao.findByOrderDetailPkIndent(restaurant,order);
        List<Map<String,Object>>  foods = new ArrayList<>();
        for (OrderDetail food : arg){
            Map<String,Object>  map = new HashMap<>();
            map.put("id",food.getOrderDetailPk().getFood());
            map.put("remark",food.getRemark());
            map.put("counts",food.getcounts());
            foods.add(map);
        }
        result.put("foods",foods);
        kitchenClient.addOrder(result);
    }

    /**
    *拒绝订单
    *@Param [restaurant, order]
    *@Return void
    *@Author 孙良玉
    */
    public void refuseOrder (Long restaurant, Long order , String reason){
        foodOrderDao.updateFlow(restaurant,order,'c',new java.sql.Date(System.currentTimeMillis()),reason);
    }
}
