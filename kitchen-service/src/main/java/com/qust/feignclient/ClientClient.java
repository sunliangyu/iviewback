package com.qust.feignclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "client-service")
public interface ClientClient {
    @PostMapping(value = "/order/finish")
    Map finish(@RequestBody Map<String,Object> map);
}
