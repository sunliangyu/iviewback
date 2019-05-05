package com.qust.service;

import com.qust.dao.FoodClassDao;
import com.qust.dao.FoodMenuDao;
import com.qust.dao.FoodNeedDao;
import com.qust.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class FoodService {
    @Autowired
    FoodClassDao foodClassDao;
    @Autowired
    FoodNeedDao foodNeedDao;
    @Autowired
    FoodMenuDao foodMenuDao;
    /**
    *获取该餐厅的所有菜单类型
    *@Param [restaurant]
    *@Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    *@Author 孙良玉
    */
    public List<Map<String,Object>>  getFoodClass (Long restaurant) {
        List<Object[]> re = foodClassDao.findByRestaurant(restaurant);
        List<Map<String,Object>> result = new ArrayList<>();
        for(Object[] arg : re){
            Map<String,Object> map = new HashMap<>();
            map.put("id",Integer.valueOf(String.valueOf(arg[0])));
            map.put("label",String.valueOf(arg[1]));
            result.add(map);
        }
        return result;
    }

    /**
    *增加类
    *@Param [map]
    *@Return void
    *@Author 孙良玉
    */
    public  void addfoodClass (Map<String,Object> map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        StringBuilder builder = new StringBuilder();
        builder.append(restaurant).append(System.currentTimeMillis());
        Long id = Long.valueOf(builder.toString());
        FoodClass foodClass = new FoodClass();
        FoodClassPk pk = new FoodClassPk();
        pk.setFoodclass(id);
        pk.setRestaurant(restaurant);
        foodClass.setId(pk);
        foodClass.setDescription(String.valueOf(map.get("dexcription")));
        foodClassDao.save(foodClass);
    }

    /**
    *增加菜单
    *@Param [map]
    *@Return void
    *@Author 孙良玉
    */
    public void  addfood (Map<String, Object> map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        StringBuilder builder = new StringBuilder();
        builder.append(restaurant).append(System.currentTimeMillis());
        Long id = Long.valueOf(builder.toString());
        Long type = Long.valueOf(String.valueOf(map.get("type")));
        Map<String,Object> food = (Map<String,Object>)map.get("addFood");
        List<Object> list = (List<Object>)food.get("need");
        this.saveFoodNeed(id,restaurant,list);
        String name = String.valueOf(food.get("name"));
        int price = Integer.valueOf(String.valueOf(food.get("price")));
        int cost = Integer.valueOf(String.valueOf(food.get("cost")));
        String desc = String.valueOf(food.get("desc"));
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setCreateTime(new Timestamp(System.currentTimeMillis()));
        foodMenu.setId(id);
        foodMenu.setName(name);
        foodMenu.setPrice(price);
        foodMenu.setTimeCost(cost);
        foodMenu.setDesciption(desc);
        foodMenu.setRestaurant(restaurant);
        foodMenu.setFlag(true);
        foodMenu.setSellNumber(0);
        foodMenu.setFoodclass(type);
        foodMenuDao.save(foodMenu);
    }

    /**
    *存储菜单制作原料
    *@Param [id, restaurant, list]
    *@Return void
    *@Author 孙良玉
    */
    public void  saveFoodNeed (Long id , Long restaurant ,  List<Object> list){
        FoodNeed need = new FoodNeed();
        FoodNeedPk   pk = new FoodNeedPk();
        pk.setFood(id);
        need.setRestaurant(restaurant);
        need.setId(pk);
        for(Object object : list){
            Map map = (Map)object;
            pk.setMaterial(Long.valueOf(String.valueOf(map.get("id"))));
            need.setNname(String.valueOf(map.get("name")));
            need.setCount(Integer.valueOf(String.valueOf(map.get("count"))));
            foodNeedDao.save(need);
        }
    }

    /**
    *根据类型获取菜单
    *@Param [map]
    *@Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    *@Author 孙良玉
    */
    public List<Map<String,Object>> getfoodByclass (Map<String,Object> map){
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long type = Long.valueOf(String.valueOf(map.get("type")));
        List<Object[]>  list =  foodMenuDao.getFoodByClass(restaurant,type);
        List<Map<String,Object>> result = new LinkedList<>();
        for(Object[] objects : list){
            Map<String,Object> arg = new HashMap<>();
            Long id = Long.valueOf(String.valueOf(objects[0]));
            arg.put("id",id);
            String name = String.valueOf(objects[1]);
            arg.put("name",name);
            result.add(arg);
        }
        return result;
    }

    /**
    *删除某个菜单
    *@Param [map]
    *@Return void
    *@Author 孙良玉
    */
    public void deletefood (Map<String,Object> map){
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        foodMenuDao.deleteById(id,restaurant);
        foodNeedDao.deleteById(id,restaurant);
    }

    /**
    *获取某个菜单的详细信息
    *@Param [map]
    *@Return java.util.Map<java.lang.String,java.lang.Object>
    *@Author 孙良玉
    */
    public Map<String,Object> getFoodById (Map<String,Object> map){
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        FoodMenu foodMenu = foodMenuDao.getByID(id,restaurant);
        Map<String,Object> result = new HashMap<>();
        result.put("des",foodMenu.getDesciption());
        result.put("price",foodMenu.getPrice());
        result.put("cost",foodMenu.getTimeCost());
        List<Map<String,Object>>  need = new LinkedList<>();
        result.put("need",need);
        List<Object[]> list = foodNeedDao.getNeed(id,restaurant);
        for(Object[] objects : list){
            Map<String,Object>  arg = new HashMap<>();
            arg.put("id",objects[0]);
            arg.put("count",objects[1]);
            arg.put("name",objects[2]);
            need.add(arg);
        }
        return result;
    }


    /**
    * 修改更新某个菜单
    *@Param [map]
    *@Return void
    *@Author 孙良玉
    */
    public void updateFood (Map<String, Object> map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long foodid = Long.valueOf(String.valueOf(map.get("id")));
        List<Object>   arg = (ArrayList<Object>)map.get("dfood");
        if(arg != null){
            this.analyList(arg,true,foodid,restaurant);
        }
        arg = (ArrayList<Object>)map.get("add");
        if (arg != null) {
            this.analyList(arg,false,foodid,restaurant);
        }
        String name = String.valueOf(map.get("name"));
        String desc = String.valueOf(map.get("des"));
        Integer price = Integer.valueOf(String.valueOf(map.get("price")));
        Integer cost = Integer.valueOf(String.valueOf(map.get("cost")));
        foodMenuDao.updateFood(restaurant,foodid,name,desc,price,cost);

    }

    public void analyList (List<Object> list,boolean boo,Long food ,Long restaurant) {
        // 删除
        if(boo) {
            for(Object arg : list){
                Long id = Long.valueOf(String.valueOf(arg));
                foodNeedDao.deleteByIdFood(food,restaurant,id);
            }
        }else{ // 增加
            FoodNeed foodNeed = new FoodNeed();
            FoodNeedPk pk = new FoodNeedPk();
            foodNeed.setId(pk);
            pk.setFood(food);
            foodNeed.setRestaurant(restaurant);
            for(Object arg : list) {
                Map map = (Map)arg;
                foodNeed.setCount(Integer.valueOf(String.valueOf(map.get("count"))));
                foodNeed.setNname(String.valueOf(map.get("name")));
                pk.setMaterial(Long.valueOf(String.valueOf(map.get("id"))));
                foodNeedDao.save(foodNeed);
            }
        }
    }
}
