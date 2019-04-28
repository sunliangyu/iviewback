package com.qust.service;


import com.qust.dao.FoodOrderDao;
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
        result.put("access",access);
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
}
