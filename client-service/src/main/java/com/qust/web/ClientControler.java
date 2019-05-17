package com.qust.web;


import com.qust.entity.Client;
import com.qust.service.ClientService;
import com.qust.util.BPwdEncoderUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/client")
public class ClientControler {
    @Autowired
    ClientService clientService;

    @PostMapping(value = "/login")
    public Map  login (@RequestBody Map<String,Object> map) {
        return clientService.login(map);
    }


    @PostMapping(value = "/getUserInfo")
    public Map  getUserInfo (@RequestBody Map<String,Object> map) {
        return clientService.getUserInfo(map);
    }

    @ApiOperation(value = "注册", notes = "username和password为必选项")
    @PostMapping("/registry")
    public Map createUser(@RequestBody Client user){
        //参数判读省略,判读该用户在数据库是否已经存在省略
        return clientService.createUser(user);
    }


}
