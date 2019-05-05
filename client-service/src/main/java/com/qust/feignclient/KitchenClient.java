package com.qust.feignclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "kitchen-service")
public interface KitchenClient {

    @PostMapping(value = "/kitchen/addOrder")
    Map addOrder(@RequestBody Map<String,Object> map);
}
