package com.qust.feignclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "client-service")
public interface ClientClient  {


    @GetMapping(value = "/order/getUnreadCount")
    Map getUnreadCount(@RequestParam("restaurant")Long  restaurant);
}
