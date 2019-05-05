package com.qust.service;

import com.qust.dao.RawMaterialDao;
import com.qust.entity.RawMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaterialService {

    @Autowired
    RawMaterialDao rawMaterialDao;

    /**
    *根据种类获取所有该类型的物品名称
    *@Param [restaurant, flag]
    *@Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    *@Author 孙良玉
    */
    public List<Map<String,Object>>  getAllByTy (Map<String,Object> map){
        Long restaurant = Long.valueOf(map.get("restaurant").toString());
        char flag = map.get("types").toString().charAt(0);
        List<Object[]> list = rawMaterialDao.getSimpleBytype(restaurant,flag);
        List<Map<String,Object>> result = new ArrayList<>();
        for(Object[] objects : list){
            Map<String,Object> arg = new HashMap<>();
            arg.put("id",objects[0]);
            arg.put("label",objects[1]);
            result.add(arg);
        }
        return result;
    }

    public Map<String,Object> getMateByI (Map<String,Object> map){
        Long restaurant = Long.valueOf(map.get("restaurant").toString());
        Long id = Long.valueOf(map.get("id").toString());
        RawMaterial arg =  rawMaterialDao.getById(restaurant,id);
        Map<String,Object> result = new HashMap<>();
        map.put("count",arg.getCount());
        map.put("save",arg.getSaveTime());
        return map;
    }

    public void alertMaterial (Map<String,Object> map){
        Long restaurant = Long.valueOf(map.get("restaurant").toString());
        Long id = Long.valueOf(map.get("id").toString());
        int count = Integer.valueOf(String.valueOf(map.get("count")));
        String name = String.valueOf(map.get("name"));
        int save = Integer.valueOf(String.valueOf(map.get("save")));
        rawMaterialDao.alterById(restaurant,id,count,name,save);
    }

    public boolean checkName (Map<String,Object> map){
        Long restaurant = Long.valueOf(map.get("restaurant").toString());
        String name = String.valueOf(map.get("name"));
        return rawMaterialDao.checkName(restaurant, name) == 0;
    }

    public void  saveMaterial (Map<String,Object> map){
        Long restaurant = Long.valueOf(map.get("restaurant").toString());
        Object object = map.get("count");
        int count;
        int save;
        if(object == null){
            count = 0;
        }else {
            count =  Integer.valueOf(String.valueOf(object));
        }
        object = map.get("save");
        if(object == null){
            save = 0;
        }else {
            save =  Integer.valueOf(String.valueOf(object));
        }
        String name = String.valueOf(map.get("name"));
        char type = String.valueOf(map.get("type")).charAt(0);
        StringBuilder builder = new StringBuilder();
        builder.append(restaurant);
        builder.append(System.currentTimeMillis());
        Long id = Long.valueOf(builder.toString());
        RawMaterial in = new RawMaterial();
        in.setSaveTime(save);
        in.setCount(count);
        in.setFlag(type);
        in.setRestaurant(restaurant);
        in.setId(id);
        in.setName(name);
        rawMaterialDao.save(in);
    }



    public void  deleteMaterial (Map<String,Object> map){
        Long restaurant = Long.valueOf(map.get("restaurant").toString());
        Long id = Long.valueOf(map.get("id").toString());
        rawMaterialDao.deleteById(id,restaurant);
    }
}
