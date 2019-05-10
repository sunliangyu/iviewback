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


    @GetMapping("/inquiryOrder")
    public Map<String,Object> inquiryOrder (@RequestParam Map<String,Object> map) {
        return orderService.inquiryOrder(map);
    }

    @PostMapping("/pay")
    public void pay (@RequestBody Map<String,Object> map ) {
         orderService.pay(map);
    }
}
