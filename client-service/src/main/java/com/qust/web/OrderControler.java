package com.qust.web;

import com.qust.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderControler {
    @Autowired
    OrderService orderService;

    @ApiOperation(value = "获取未读取订单数量", notes = "获取未读取订单数量")
    @GetMapping("/getUnreadCount")
    public Map getUnreadCount(@RequestParam long restaurant){
        Map<String,Integer> result  = new HashMap<>();
        int  count  = orderService.getUnreadCount(restaurant);
        result.put("unread",count);
        return result;
    }

    @ApiOperation(value = "获取当日订单以及未读取订单", notes = "获取当日订单以及未读取订单")
    @GetMapping("/getOrder")
    public Map getOrder(@RequestParam  Map<String,Object> map) {
        Long restaurant = Long.valueOf(map.get("restaurant").toString());
        return  orderService.getOrder(restaurant);
    }


    @GetMapping("/test")
    public Map test (){
        orderService.getOrder(new Long(1),null);
        return null;
    }


    @ApiOperation(value = "获取某个订单的简略信息", notes = "获取某个订单的简略信息")
    @GetMapping("/getOrderById")
    public Map getOrderById(@RequestParam Map<String,String> map) {
        Long restaurant = Long.valueOf(map.get("restaurant"));
        Long order  = Long.valueOf(map.get("order"));
        return  orderService.getOrderById(restaurant,order);
    }

    @ApiOperation(value = "订单处理", notes = "订单处理")
    @PostMapping("/alertOrder")
    public  void alertOrder (@RequestBody Map<String ,String> map){
        Long restaurant = Long.valueOf(map.get("restaurant"));
        Long order  = Long.valueOf(map.get("order"));
        char state = map.get("operate").charAt(0);
        String reason = map.get("reason");
        orderService.alterOrder(restaurant,order,state,reason);
    }


    @ApiOperation(value = "获取餐厅的的订单数量信息", notes = "获取餐厅的订单数量")
    @GetMapping("/getOrderCount")
    public Map getOrderCount(@RequestParam Map<String,String> map) {
        Long restaurant = Long.valueOf(map.get("restaurant"));
        Date date =  new Date(System.currentTimeMillis());
        return  orderService.getOrder(restaurant,date);
    }

    @ApiOperation(value = "获取餐厅的订单总数", notes = "获取餐厅的订单总数")
    @GetMapping("/getOrderall")
    public Map getOrderall(@RequestParam Long restaurant){
        return orderService.getCountOrder(restaurant);
    }

    @ApiOperation(value = "获取bar页的数据", notes = "获取bar页的数据")
    @GetMapping("/getBar")
    public List<Integer> getBar(@RequestParam Long restaurant){
        return orderService.getOldCount(restaurant,new Date(System.currentTimeMillis()));
    }

    /**
    *订单分页查询
    *@Param [map]
    *@Return java.util.Map<java.lang.String,java.lang.Object>
    *@Author 孙良玉
    */
    @PostMapping("/orderpage")
    public Map<String,Object> orderPage (@RequestBody Map<String,Object> map){
        return orderService.orderPage(map);
    }

    /**
    *订单详情查询
    *@Param [map]
    *@Return java.util.Map<java.lang.String,java.lang.Object>
    *@Author 孙良玉
    */
    @GetMapping("/inquiryOrder")
    public Map<String,Object> inquiryOrder (@RequestParam Map<String,Object> map) {
        return orderService.inquiryOrder(map);
    }

    /**
    *支付
    *@Param [map]
    *@Return void
    *@Author 孙良玉
    */
    @PostMapping("/pay")
    public void pay (@RequestBody Map<String,Object> map ) {
         orderService.pay(map);
    }

    /**
    * 用户查询自己的订单
    *@Param [map]
    *@Return java.util.Map<java.lang.String,java.lang.Object>
    *@Author 孙良玉
    */
    @PostMapping("/cifQuiryOrder")
    public Map<String,Object> cifQuiryOrder  (@RequestBody Map<String,Object> map ) {
        return orderService.cifQuiryOrder(map);
    }

    
    /**
    *客户home页的查询今天已拒单以及今天已完成的简略信息
    *@Param [map]
    *@Return java.util.List<java.util.Map>
    *@Author 孙良玉
    */
    @GetMapping("/todayOrder")
    public List<Map> todatyOrder (@RequestParam Map<String,Object> map ) {
        return orderService.todatyOrder(map);
    }

    /**
    *数量
    *@Param [map]
    *@Return java.util.Map<java.lang.String,java.lang.Object>
    *@Author 孙良玉
    */
    @GetMapping("/orderCount")
    public Map<String,Object>  orderCount (@RequestParam Map<String,Object> map){
        return orderService.orderCount(map);
    }

    /**
    *取消订单 逻辑没写清晰
    *@Param [map]
    *@Return void
    *@Author 孙良玉
    */
    @PutMapping("/cancelOrder")
    public void cancelOrder (@RequestParam Map<String,Object> map) {
        orderService.cancelOrder(map);
    }

    /**
    *下单
    *@Param [map]
    *@Return java.util.Map
    *@Author 孙良玉
    */
    @PostMapping("/purchase")
    public Map purchase (@RequestBody Map<String,Object> map ){
        return orderService.purchase(map);
    }


    /**
    *完成某些菜
    *@Param [map]
    *@Return void
    *@Author 孙良玉
    */
    @PostMapping("/finish")
    public void finish (@RequestBody Map<String,Object> map ){
        orderService.finish(map);
    }

}
