package com.qust.service;

import com.qust.dao.RestaurantMemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantService {
    @Autowired
    RestaurantMemberDao rmDao;


    public List<Map>  getRestaurants() {
        List<Object[]> list = rmDao.getReS();
        List<Map> result = new LinkedList<>();
        for(Object[] objects : list ) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",Long.valueOf(String.valueOf(objects[0])));
            map.put("label",String.valueOf(objects[1]));
            result.add(map);
        }
        return result;
    }
}
