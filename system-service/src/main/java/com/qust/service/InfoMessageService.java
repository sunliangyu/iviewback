package com.qust.service;

import com.qust.dao.InfoMessageDao;
import com.qust.entity.InfoMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoMessageService {

    @Autowired
    InfoMessageDao infoMessageDao;

    public List<InfoMessage> getByState(char state) {
        return infoMessageDao.findByState(state);
    }

    public int getUnreadCount (Long restaurant ){
        int unread = infoMessageDao.getCount(restaurant,'a');
        return unread;
    }

    public Map getMessages(Long restaurant){
        List<Object[]> all = infoMessageDao.findMessages(restaurant);
        Map<String,List<Map>> result = new HashMap<>();
        List<Map> unread = new ArrayList<>();
        List<Map> reader = new ArrayList<>();
        List<Map> trash = new ArrayList<>();
        for(Object[] arg: all){
            Map res = new HashMap();
            res.put("msg_id",arg[1]);
            res.put("create_time",arg[2]);
            res.put("title",arg[3]);
            if((char)arg[0] == 'a' ){
                unread.add(res);
            }else if((char)arg[0]== 'b'){
                reader.add(res);
            }else{
                trash.add(res);
            }
        }
        result.put("unread",unread);
        result.put("readed",reader);
        result.put("trash",trash);
        return  result;
    }


    /**
    * 后期增加水平分库根据restaurant
    *@Param [restaurant, id]
    *@Return java.util.Map
    *@Author 孙良玉
    */
    public String getMessagesByid(Long restaurant,Long id){
        String message = infoMessageDao.findMessageBuId(restaurant,id);
        return message;
    }
}
