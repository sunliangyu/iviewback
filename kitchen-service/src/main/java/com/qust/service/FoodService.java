package com.qust.service;

import com.qust.dao.*;
import com.qust.entity.*;
import com.qust.feignclient.ClientClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FoodService {
    @Autowired
    FoodClassDao foodClassDao;
    @Autowired
    FoodNeedDao foodNeedDao;
    @Autowired
    FoodMenuDao foodMenuDao;
    @Autowired
    CodeOrderDao codeOrderDao;
    @Autowired
    FoodImageDao foodImageDao;
    @Autowired
    ShoppingDao shoppingDao;
    @Autowired
    ClientClient clientClient;
    @Autowired
    RawMarerialOrderDao rawMarerialOrderDao;
    @Autowired
    RawMaterialDao rawMaterialDao;
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

    /**
    *获取所有菜单的简单信息
    *@Param [restaurant]
    *@Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    *@Author 孙良玉
    */
    public List<Map<String,Object>> getfoods (Long restaurant) {
        List<Object[]> list = foodMenuDao.getFoos(restaurant);
        List<Map<String,Object>> result = new ArrayList<>();
        for (Object[] objects : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",objects[0]);
            map.put("label",objects[1]);
            result.add(map);
        }
        return result;
    }




    public Map<String,Object> getcodepage (Map<String,Object> map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Object page = map.get("page");
        Object id = map.get("id");
        String state = String.valueOf(map.get("state"));
        if (id == null) {
            return this.getPages(restaurant,page,state);
        }else {
            return this.getPages(restaurant,Long.valueOf(String.valueOf(id)),page,state);
        }

    }


    public Map<String,Object> getPages(Long restaurant ,Long id, Object page,String state) {
        Map<String,Object> result = new HashMap<>();
        List<Object[]> foods  ;
        if(page == null) {
            int count =  codeOrderDao.getCountR(restaurant,id,state);
            result.put("count",count);
            if (count == 0) {
                result.put("sum",0);
                return result;
            }else {
                count = codeOrderDao.getSumR(restaurant,id,state);
                result.put("sum",count);
                foods = codeOrderDao.getPage(restaurant,id,0,state);
            }
        } else {
            foods = codeOrderDao.getPage(restaurant,id,Integer.valueOf(String.valueOf(page))*10,state);
        }
        result.put("code",this.createFood(foods));
        return result;
    }
    public Map<String,Object> getPages(Long restaurant , Object page,String state) {
        Map<String,Object> result = new HashMap<>();
        List<Object[]> foods  ;
        if(page == null) {
            int count =  codeOrderDao.getCountR(restaurant,state);
            result.put("count",count);
            if (count == 0) {
                result.put("sum",0);
                return result;
            }else {
                count = codeOrderDao.getSumR(restaurant,state);
                result.put("sum",count);
                foods = codeOrderDao.getPage(restaurant,0,state);
            }
        } else {
            foods = codeOrderDao.getPage(restaurant,Integer.valueOf(String.valueOf(page))*10,state);
        }
        result.put("code",this.createFood(foods));
        return result;
    }

    private List<Map<String,Object>>  createFood (List<Object[]> list) {
        List<Map<String,Object>> food = new LinkedList<>();
        for(Object[] objects : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("food",objects[0]);
            map.put("count",objects[1]);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)objects[2]);
            map.put("time",time);
            boolean flag = (Boolean)objects[3];
            if (flag) {
                map.put("flag","Y");
            } else {
                map.put("flag","N");
            }
            map.put("remark",objects[4]);
            map.put("name",objects[5]);
            map.put("id",objects[6]);
            food.add(map);
        }
        return food;
    }


    public List<Map<String,Object>> getNeed (Long restaurant , Long id){
        List<Object[]> list  = foodNeedDao.getNeedS(id,restaurant);
        List<Map<String,Object>> result = new LinkedList<>();
        for(Object[] objects : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",objects[0]);
            map.put("count",objects[1]);
            result.add(map);
        }
        return result;
    }

    public void updateorder (Map<String,Object> map) {
        Long restaurant  =  Long.valueOf(String.valueOf(map.get("restaurant")));
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        Long order = Long.valueOf(String.valueOf(map.get("order")));
        char state = String.valueOf(map.get("state")).charAt(0);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        codeOrderDao.updatestate(id,restaurant,order,state,time);
        if(state == 'c') {
            Map arg = new HashMap();
            arg.put("restaurant",restaurant);
            arg.put("id",order);
            arg.put("food",id);
            clientClient.finish(arg);
        } else if (state == 'b') {
            List<Object[]> list = foodNeedDao.getCost(restaurant,id);
            if(list != null ){
                this.costNeed(restaurant,order,list);
            }
        }
    }

    private void costNeed (Long restaurant,Long order,List<Object[]> list ) {
        RawMarerialOrder rawMarerialOrder = new RawMarerialOrder();
        rawMarerialOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
        rawMarerialOrder.setRemark("订单"+order);
        rawMarerialOrder.setRestaurant(restaurant);
        rawMarerialOrder.setOutput(0);
        StringBuilder builder = new StringBuilder();
        builder.append(restaurant);
        for(Object[] objects: list) {
            rawMarerialOrder.setName(String.valueOf(objects[2]));
            int count = (Integer)objects[1];
            count = - count;
            rawMarerialOrder.setCount(count);
            Long material = Long.valueOf(String.valueOf(objects[0]));
            builder.append(System.currentTimeMillis());
            Long id = Long.valueOf(builder.toString());
            rawMarerialOrder.setId(id);
            builder.delete(String.valueOf(restaurant).length(),30);
            rawMarerialOrder.setMaterial(material);
            rawMarerialOrderDao.save(rawMarerialOrder);
            rawMaterialDao.delete(material,restaurant,count);
        }
    }

    public List getImage ( Long restaurant, Long id ) {
        List<Object> list = foodImageDao.findUrl(restaurant,id);
        List<String> result = new LinkedList<>();
        for (Object objects : list) {
            result.add(String.valueOf(objects));
        }
        return result;
    }

    public void alertImage (Map<String,Object> map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long id  = Long.valueOf(String.valueOf(map.get("id")));
        FoodImagePk pk = new FoodImagePk();
        pk.setFood(id);
        pk.setRestaurant(restaurant);
        FoodImage foodImage = new FoodImage();
        foodImage.setId(pk);
        Object object = map.get("add");
        if(object != null) {
            List add = (ArrayList)object;
            int len = add.size();
            for (int i = 0; i<len ; i++ ) {
                String url = String.valueOf(add.get(i));
                pk.setUrl(url);
                foodImageDao.save(foodImage);
            }
        }
        object = map.get("deletes");
        if(object != null) {
            List delete = (ArrayList)object;
            int len = delete.size();
            for (int i = 0; i<len ; i++ ) {
                pk.setUrl(String.valueOf(delete.get(i)));
                foodImageDao.delete(foodImage);
            }
        }
    }


    /**
    *获取某个订单的详细详细进展
    *@Param [map]
    *@Return java.util.Map<java.lang.String,java.lang.Object>
    *@Author 孙良玉
    */
    public List getProgress (Map<String,Object> map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long id  = Long.valueOf(String.valueOf(map.get("id")));
        List<Object[]> list = codeOrderDao.getProgress(restaurant,id);
        List<Map<String,Object>> result = new LinkedList<>();
        for(Object[] objects: list) {
            Map<String,Object> arg = new HashMap<>();
            arg.put("name",objects[2]);
            arg.put("flow",objects[1]);
            arg.put("count",objects[0]);
            result.add(arg);
        }
        return result;
    }


    /**
    *获取购物车物品信息
    *@Param [arg]
    *@Return java.util.List<java.util.Map>
    *@Author 孙良玉
    */
    public List<Map> getCart (Map<String,Object> arg) {
        Long restaurant = Long.valueOf(String.valueOf(arg.get("restaurant")));
        Long cif = Long.valueOf(String.valueOf(arg.get("cif")));
        List<Object[]> list = shoppingDao.getCart(restaurant,cif);
        List<Map> result = new LinkedList<>();
        for(Object[] objects: list) {
            Map<String,Object> map = new HashMap<>();
            map.put("food",objects[0]);
            map.put("num",objects[1]);
            map.put("name",objects[2]);
            map.put("price",objects[3]);
            result.add(map);
        }
        return result;
    }

    public void alertCart (Map<String, Object> arg) {
        Long restaurant = Long.valueOf(String.valueOf(arg.get("restaurant")));
        Long cif = Long.valueOf(String.valueOf(arg.get("cif")));
        Long food = Long.valueOf(String.valueOf(arg.get("food")));
        char type = ((String)arg.get("type")).charAt(0);
        ShoppingCartPk pk = new ShoppingCartPk();
        pk.setClient(cif);
        pk.setRestaurant(restaurant);
        pk.setFood(food);
        ShoppingCart cart = new ShoppingCart();
        cart.setShoppingCardPk(pk);
        if(type == 'a') { // insert
            int num = Integer.valueOf(String.valueOf(arg.get("num")));
            cart.setCount(num);
            shoppingDao.save(cart);
        } else if (type == 'b' ){ //uupodate
            int num = Integer.valueOf(String.valueOf(arg.get("num")));
            shoppingDao.updateNum(restaurant,cif,food,num);
        } else {
            shoppingDao.deleteByfood(restaurant,cif,food);
        }
    }

    public Map getMenu (Map<String,Object> map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Object o = map.get("like");
        List<Object[]> list;
        Map result =new HashMap();
        if (o != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("%").append(String.valueOf(o)).append("%");
            String like = builder.toString();
            o = map.get("page");
            if(o == null) {
                int count = foodMenuDao.getFoosCou(restaurant,like);
                result.put("count",count);
                list = foodMenuDao.getFoosByC(restaurant,like,0);
            } else {
                int count = Integer.valueOf(String.valueOf(o));
                list = foodMenuDao.getFoosByC(restaurant,like,count*10);
            }
        }else {
            o = map.get("page");
            if(o == null) {
                int count = foodMenuDao.getFoosCou(restaurant);
                result.put("count",count);
                list = foodMenuDao.getFoosByC(restaurant,0);
            } else {
                int count = Integer.valueOf(String.valueOf(o));
                list = foodMenuDao.getFoosByC(restaurant,count*10);
            }
        }
        List<Map> foods = new LinkedList<>();
        for(Object[] objects: list) {
            Map arg = new HashMap();
            arg.put("id",objects[0]);
            arg.put("name",objects[1]);
            arg.put("price",objects[2]);
            arg.put("des",objects[3]);
            arg.put("out",objects[4]);
            foods.add(arg);
        }
        result.put("foods",foods);
        return result;
    }


    public Map addCart (Map map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long food = Long.valueOf(String.valueOf(map.get("food")));
        Long cif = Long.valueOf(String.valueOf(map.get("cif")));
        int i = shoppingDao.grtFoodC(restaurant,cif,food);
        if(i == 0) {
            ShoppingCartPk pk = new ShoppingCartPk();
            pk.setFood(food);
            pk.setRestaurant(restaurant);
            pk.setClient(cif);
            ShoppingCart cart = new ShoppingCart();
            cart.setShoppingCardPk(pk);
            cart.setCount(1);
            shoppingDao.save(cart);
        } else {
            shoppingDao.addCount(restaurant,cif,food);
        }
        Map result = new HashMap();
        result.put("state",true);
        return  result;
    }

    public Map getFoodInfo (Long restaurant,Long food) {
        List need = this.getNeed(restaurant,food);
        List image = this.getImage(restaurant,food);
        Map result = new HashMap();
        result.put("need",need);
        result.put("image",image);
        FoodMenu foodMenu = foodMenuDao.getByID(food,restaurant);
        result.put("cost",foodMenu.getTimeCost());
        result.put("sell",foodMenu.getSellNumber());
        return result;
    }
 }