package com.qust.service;

import com.qust.dao.CodeOrderDao;
import com.qust.entity.CodeOrder;
import com.qust.entity.CodeOrderPk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CodeService {
    @Autowired
    CodeOrderDao codeOrderDao;

    public void addCodeOrder (Map<String,Object> map){
        Long order  = Long.valueOf( map.get("order").toString() );
        Boolean flag = (Boolean)map.get("flag");
        Long restaurant  = Long.valueOf( map.get("restaurant").toString() );
        List<Map<String,Object>> foods = (ArrayList<Map<String,Object>>)map.get("foods");
        CodeOrder codeOrder = new CodeOrder();
        codeOrder.setFlag(flag);
        codeOrder.setState('a');
        CodeOrderPk pk = new CodeOrderPk();
        codeOrder.setId(pk);
        pk.setRestayrant(restaurant);
        pk.setOrder(order);
        for(Map<String,Object> food : foods){
            Long foodid = Long.valueOf(food.get("id").toString());
            pk.setFood(foodid);
            Integer count = Integer.valueOf(food.get("counts").toString());
            codeOrder.setCount(count);
            Object object = food.get("remark");
            if(object == null){
                codeOrder.setRemark(null);
            }else {
                codeOrder.setRemark((String)object);
            }
            codeOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
            codeOrderDao.save(codeOrder);
        }
    }
}
