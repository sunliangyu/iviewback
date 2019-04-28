package com.qust.web;


import com.qust.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientControler {
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
    public Map getOrder(@RequestParam long restaurant) {
        return  orderService.getOrder(restaurant);
    }

    @GetMapping("/test")
    public Map test (){
        Map map =  orderService.getOrder(Long.valueOf(1));
        return map;
    }


}
