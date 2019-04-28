package com.qust.web;

import com.qust.entity.User;
import com.qust.feignclient.ClientClient;
import com.qust.service.InfoMessageService;
import com.qust.service.UserService;
import com.qust.util.BPwdEncoderUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    UserService userService;

    @Autowired
    ClientClient clientClient;

    @Autowired
    InfoMessageService infoMessageService;


    @ApiOperation(value = "注册", notes = "username和password为必选项")
    @PostMapping("/registry")
    public Map createUser(@RequestBody User user){
        //参数判读省略,判读该用户在数据库是否已经存在省略
        String entryPassword= BPwdEncoderUtils.BCryptPassword(user.getPassword());
        user.setPassword(entryPassword);
        return userService.createUser(user);
    }

    @ApiOperation(value = "登录", notes = "username和password为必选项")
    @PostMapping("/login")
    public Map logins(@RequestBody User user){
        Map result = userService.userLogin(user);
        return result;
    }

    @ApiOperation(value = "根据token获取用户", notes = "根据token获取用户")
    @PostMapping("/getUserInfo")
    public Map getUserInfo(@RequestBody Map<String,String> map ){
        String token = map.get("token");
        return   userService.getUserInfo(token);
    }

    @ApiOperation(value = "根据餐厅获取未读消息数量", notes = "根据餐厅获取未读消息数量")
    @GetMapping ("/messageCount")
    public Map getMessageCount (@RequestParam  Long restaurant) {
        // 订单
        Map<String,Integer> result =  clientClient.getUnreadCount(restaurant);
        int count = result.get("unread");
        result =  new HashMap<>();
        result.put("order",count);
        //通知
        count = infoMessageService.getUnreadCount(restaurant);
        result.put("message",count);
        return result;
    }

    @ApiOperation(value = "根据餐厅获取未读消息简要信息", notes = "根据餐厅获取未读消息简要信息")
    @GetMapping ("/getMessage")
    public Map getMessage (@RequestParam  Long restaurant) {
        return infoMessageService.getMessages(restaurant);
    }

    @ApiOperation(value = "根据消息id获取消息住内容", notes = "根据消息id获取消息主内容")
    @GetMapping ("/getmessageid")
    public Map getMessageByid (@RequestParam  Long restaurant , Long  msg_id) {
        String message = infoMessageService.getMessagesByid(restaurant,msg_id);
        Map result =new HashMap();
        result.put("message",result);
        return result;
    }

}
