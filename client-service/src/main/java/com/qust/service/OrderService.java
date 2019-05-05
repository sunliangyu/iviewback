package com.qust.service;


import com.qust.dao.FoodOrderDao;
import com.qust.dao.OrderDetailDao;
import com.qust.entity.FoodOrder;
import com.qust.entity.OrderDetail;
import com.qust.feignclient.KitchenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *
     * @Param [restaurant]
     * @Return java.lang.Integer
     * @Author 孙良玉
     */
    public int getUnreadCount(Long restaurant) {
        int count = foodOrderDao.getCount(restaurant, 'a');
        return count;
    }

    /**
     * 获取新订单通知以及当天的接拒订单
     *
     * @Param [restaurant]
     * @Return java.util.Map
     * @Author 孙良玉
     */
    public Map getOrder(Long restaurant) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Object[]> re;
        List<Map<String, Object>> unread;
        List<Map<String, Object>> access;
        List<Map<String, Object>> refuse;
        re = foodOrderDao.findUnread(restaurant);
        unread = createRes(re);
        result.put("unread", unread);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String start = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String end = df.format(calendar.getTime());
        re = foodOrderDao.findToday(restaurant, 'b', start, end);
        access = createRes(re);
        result.put("receive", access);
        re = foodOrderDao.findToday(restaurant, 'c', start ,end);
        refuse = createRes(re);
        result.put("refuse", refuse);
        return result;
    }


    /**
     * 生成map返回到前端
     *
     * @Param [re]
     * @Return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @Author 孙良玉
     */
    public List<Map<String, Object>> createRes(List<Object[]> re) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] arg : re) {
            Map<String, Object> map = new HashMap();
            map.put("msg_id", arg[0]);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)arg[1]);
            map.put("create_time", time);
            if ((Boolean) arg[2]) {
                map.put("title", "外卖");
            } else {
                map.put("title", "到店");
            }
            result.add(map);
        }
        return result;
    }

    public Map<String, Object> getOrderById(Long resturant, Long order) {
        FoodOrder foodOrder = foodOrderDao.getSimpleOrder(resturant, order);
        StringBuilder message = new StringBuilder();
        Map<String, Object> result = new HashMap<>();
        if (foodOrder.isFlag()) {
            message.append("外卖送至").append(foodOrder.getLocation());
        } else {
            message.append("店内送至桌位：").append(foodOrder.getLocation());
        }
        if (foodOrder.getFlow() == 'c') {
            String reason = foodOrder.getReason();
            if (reason != null) {
                result.put("reason", reason);
            }
        }
        result.put("location", message.toString());
        result.put("people", foodOrder.getPeople());
        result.put("price", foodOrder.getPrice());
        List<Object[]> detail = orderDetailDao.getOrderById(resturant, order);
        List<String> foods = createFood(detail);
        result.put("foods", foods);
        return result;
    }

    /**
     * 生成food 表示
     *
     * @Param [detail]
     * @Return java.util.List<java.lang.String>
     * @Author 孙良玉
     */
    public List<String> createFood(List<Object[]> detail) {
        List<String> result = new ArrayList<>();
        StringBuilder message = new StringBuilder();
        for (Object[] food : detail) {
            message.append(food[0].toString()).append("  共计").append(food[1].toString()).append("份");
            if (food[2] != null) {
                message.append("  备注").append(food[2].toString());
            }
            result.add(message.toString());
            message.delete(0, message.length());
        }
        return result;
    }

    /**
     * 订单处理
     *
     * @Param [restaurant, order, state]
     * @Return void
     * @Author 孙良玉
     */
    public void alterOrder(Long restaurant, Long order, char state, String reason) {
        if (state == 'b') {
            this.receiveOrder(restaurant, order);
        } else if (state == 'c') {
            this.refuseOrder(restaurant, order, reason);
        }
    }

    /**
     * 接受订单
     *
     * @Param [restaurant, order]
     * @Return void
     * @Author 孙良玉
     */
    public void receiveOrder(Long restaurant, Long order) {
        foodOrderDao.updateFlow(restaurant, order, 'b', new java.sql.Timestamp(System.currentTimeMillis()), null);
        Map<String, Object> result = new HashMap<>();
        Boolean flag = foodOrderDao.getFlag(restaurant, order);
        result.put("flag", flag);
        result.put("restaurant", restaurant);
        result.put("order", order);
        List<OrderDetail> arg = orderDetailDao.findByOrderDetailPkIndent(restaurant, order);
        List<Map<String, Object>> foods = new ArrayList<>();
        for (OrderDetail food : arg) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", food.getOrderDetailPk().getFood());
            map.put("remark", food.getRemark());
            map.put("counts", food.getcounts());
            foods.add(map);
        }
        result.put("foods", foods);
        kitchenClient.addOrder(result);
    }

    /**
     * 拒绝订单
     *
     * @Param [restaurant, order]
     * @Return void
     * @Author 孙良玉
     */
    public void refuseOrder(Long restaurant, Long order, String reason) {
        foodOrderDao.updateFlow(restaurant, order, 'c', new java.sql.Timestamp(System.currentTimeMillis()), reason);
    }


    /**
     * 获取某天的所有订单数量  以及营业额度
     *
     * @Param [restaurant, date]
     * @Return void
     * @Author 孙良玉
     */
    public Map getOrder(Long restaurant, java.sql.Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String start = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String stop = df.format(calendar.getTime());
        int receive = foodOrderDao.getOrderCountByAccessTime(restaurant, 'b', start, stop);
        int refuse = foodOrderDao.getOrderCountByAccessTime(restaurant, 'c', start, stop);
        int nodo = foodOrderDao.getOrderCountByCreateTime(restaurant, 'a', start, stop);
        int count = foodOrderDao.getPriceCount(restaurant, start, stop);
        int all = foodOrderDao.getAllByDate(restaurant, start, stop);
        Map<String, Object> result = new HashMap<>();
        result.put("all", all);
        result.put("nodo", nodo);
        result.put("receive", receive);
        result.put("refuse", refuse);
        result.put("count", count);
        return result;
    }


    /**
     * 获取订单总数量
     *
     * @Param [restaurant]
     * @Return java.util.Map<java.lang.String , java.lang.Object>
     * @Author 孙良玉
     */
    public Map<String, Object> getCountOrder(Long restaurant) {
        int out = foodOrderDao.getCountOrder(restaurant, true);
        int in = foodOrderDao.getCountOrder(restaurant, false);
        Map<String, Object> map = new HashMap<>();
        map.put("out", out);
        map.put("in", in);
        return map;
    }


    /**
    *获取本某周内的各天订单量
    *@Param [restaurant, date]
    *@Return java.util.Map<java.lang.String,java.lang.Object>
    *@Author 孙良玉
    */
    public List<Integer>  getOldCount(Long restaurant , java.sql.Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        List<Integer> list = new ArrayList<>();
        int w = calendar.get(Calendar.DAY_OF_WEEK)-1 ;
        if(w == 0){
            w = 7;
        }
        calendar.add(Calendar.DATE, 1-w);
        String start = df.format(calendar.getTime());
        String stop ;
        for(int i = 0; i< w ; i++){
            calendar.add(Calendar.DATE, 1);
            stop = df.format(calendar.getTime());
            int all = foodOrderDao.getAllByDate(restaurant, start,stop);
            list.add(all);
            start = stop;
        }
        return list;
    }

}

