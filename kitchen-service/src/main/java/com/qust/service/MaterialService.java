package com.qust.service;

import com.netflix.discovery.converters.Auto;
import com.qust.dao.RawMarerialOrderDao;
import com.qust.dao.RawMaterialDao;
import com.qust.entity.RawMarerialOrder;
import com.qust.entity.RawMaterial;
import javafx.scene.paint.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MaterialService {

    @Autowired
    RawMaterialDao rawMaterialDao;
    @Autowired
    RawMarerialOrderDao rawMarerialOrderDao;
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


    public void addoutflow (Map<String,Object> map) {
        Long id =  Long.valueOf(String.valueOf(map.get("id")));
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        int count = Integer.valueOf(String.valueOf(map.get("count")));
        Timestamp time = new Timestamp(System.currentTimeMillis());
        rawMaterialDao.add(id,restaurant,count,time);
        int price = Integer.valueOf(String.valueOf(map.get("price")));
        String name = String.valueOf(map.get("name"));
        Object object = map.get("des");
        String remark = null;
        if(object != null) {
            remark = String.valueOf(object);
        }
        RawMarerialOrder   rawMarerialOrder = new RawMarerialOrder();
        rawMarerialOrder.setCount(count);
        rawMarerialOrder.setRestaurant(restaurant);
        rawMarerialOrder.setRemark(remark);
        rawMarerialOrder.setMaterial(id);
        rawMarerialOrder.setName(name);
        rawMarerialOrder.setCreateTime(time);
        rawMarerialOrder.setOutput(price);
        StringBuilder builder = new StringBuilder();
        builder.append(restaurant).append(System.currentTimeMillis());
        rawMarerialOrder.setId(Long.valueOf(builder.toString()));
        rawMarerialOrderDao.save(rawMarerialOrder);
    }

    public Map<String,Object> getMateFlow (Map<String,Object> arg)  {
        Long restaurant = Long.valueOf(String.valueOf(arg.get("restaurant")));
        Object object = arg.get("time");
        Object id = arg.get("id");
        if(object != null) {
            List time = (List)object;
            String start = (String)time.get(0);
            String stop = (String)time.get(1);
            object = arg.get("page");
            return this.getMateFlow(restaurant,start,stop,object,id);
        } else {
            object = arg.get("page");
            return this.getMateFlow(restaurant,object,id);
        }
    }


    private Map<String,Object>  getMateFlow (Long restaurant, String start,String stop, Object page ,Object id ) {
       Map<String,Object>  result =  new HashMap<>();
        List<Object[]> list;
        if(page == null ){
            if(id == null) {
                int count = rawMarerialOrderDao.getOrdeCount(restaurant,start,stop);
                result.put("count",count);
                list = rawMarerialOrderDao.getOrder(restaurant,start,stop,0);
                if (count != 0) {
                    count = rawMarerialOrderDao.getOrdeSum(restaurant,start,stop);
                }
                result.put("price",count);
            } else {
                int count = rawMarerialOrderDao.getOrdeCount(restaurant,start,stop,Long.valueOf(String.valueOf(id)));
                result.put("count",count);
                list = rawMarerialOrderDao.getOrder(restaurant,start,stop,Long.valueOf(String.valueOf(id)),0);
                if (count != 0) {
                    count = rawMarerialOrderDao.getOrdeSum(restaurant,start,stop,Long.valueOf(String.valueOf(id)));
                }
                result.put("price",count);
            }
        }else {
            if(id == null) {
                list = rawMarerialOrderDao.getOrder(restaurant,start,stop,Integer.valueOf(String.valueOf(page))*10);
            } else {
                list = rawMarerialOrderDao.getOrder(restaurant,start,stop,Long.valueOf(String.valueOf(id)),Integer.valueOf(String.valueOf(page))*10);
            }
        }
        List<Map<String,Object>> arg = new LinkedList<>();
        for( Object[] objects : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",objects[0]);
            map.put("count",objects[1]);
            map.put("price",objects[2]);
            map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)objects[3]));
            arg.add(map);
        }
        result.put("page",arg);
        return result;
    }


    private Map<String,Object>  getMateFlow (Long restaurant, Object page ,Object id ) {
        Map<String,Object>  result =  new HashMap<>();
        List<Object[]> list;
        if(page == null ){
            if(id == null) {
                int count = rawMarerialOrderDao.getOrdeCount(restaurant);
                result.put("count",count);
                list = rawMarerialOrderDao.getOrder(restaurant,0);
                if (count != 0 ) {
                    count = rawMarerialOrderDao.getOrdeSum(restaurant);
                }
                result.put("price",count);
            } else {
                int count = rawMarerialOrderDao.getOrdeCount(restaurant,Long.valueOf(String.valueOf(id)));
                result.put("count",count);
                list = rawMarerialOrderDao.getOrder(restaurant,Long.valueOf(String.valueOf(id)),0);
                if (count != 0) {
                    count = rawMarerialOrderDao.getOrdeSum(restaurant,Long.valueOf(String.valueOf(id)));
                }
                result.put("price",count);
            }
        }else {
            if(id == null) {
                list = rawMarerialOrderDao.getOrder(restaurant,Integer.valueOf(String.valueOf(page))*10);
            } else {
                list = rawMarerialOrderDao.getOrder(restaurant,Long.valueOf(String.valueOf(id)),Integer.valueOf(String.valueOf(page))*10);
            }
        }
        List<Map<String,Object>> arg = new LinkedList<>();
        for( Object[] objects : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",objects[0]);
            map.put("count",objects[1]);
            map.put("price",objects[2]);
            map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)objects[3]));
            arg.add(map);
        }
        result.put("page",arg);
        return result;
    }
}
