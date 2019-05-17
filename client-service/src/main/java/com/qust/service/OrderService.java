package com.qust.service;


import com.qust.dao.FoodOrderDao;
import com.qust.dao.InputFlowDao;
import com.qust.dao.OrderDetailDao;
import com.qust.entity.FoodOrder;
import com.qust.entity.InputFlow;
import com.qust.entity.OrderDetail;
import com.qust.entity.OrderDetailPk;
import com.qust.feignclient.KitchenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class OrderService {
    @Autowired
    FoodOrderDao foodOrderDao;
    @Autowired
    OrderDetailDao orderDetailDao;
    @Autowired
    KitchenClient kitchenClient;
    @Autowired
    InputFlowDao inputFlowDao;

    /**
     * 获取未读取的订单
     *
     * @Param [restaurant]
     * @Return java.lang.Integer
     * @Author 孙良玉
     */
    public int getUnreadCount(Long restaurant) {
        int count = foodOrderDao.getCount(restaurant, 'a');
        return count;
    }

    /**
     * 获取新订单通知以及当天的接拒订单
     *
     * @Param [restaurant]
     * @Return java.util.Map
     * @Author 孙良玉
     */
    public Map getOrder(Long restaurant) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Object[]> re;
        List<Map<String, Object>> unread;
        List<Map<String, Object>> access;
        List<Map<String, Object>> refuse;
        re = foodOrderDao.findUnread(restaurant);
        unread = createRes(re);
        result.put("unread", unread);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String start = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String end = df.format(calendar.getTime());
        re = foodOrderDao.findToday(restaurant, 'b', start, end);
        access = createRes(re);
        re = foodOrderDao.findToday(restaurant, 'd', start, end);
        access.addAll(createRes(re));
        result.put("receive", access);
        re = foodOrderDao.findToday(restaurant, 'c', start ,end);
        refuse = createRes(re);
        result.put("refuse", refuse);
        return result;
    }


    /**
     * 生成map返回到前端
     *
     * @Param [re]
     * @Return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @Author 孙良玉
     */
    public List<Map<String, Object>> createRes(List<Object[]> re) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] arg : re) {
            Map<String, Object> map = new HashMap();
            map.put("msg_id", arg[0]);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)arg[1]);
            map.put("create_time", time);
            if ((Boolean) arg[2]) {
                map.put("title", "外卖");
            } else {
                map.put("title", "到店");
            }
            result.add(map);
        }
        return result;
    }

    public Map<String, Object> getOrderById(Long resturant, Long order) {
        FoodOrder foodOrder = foodOrderDao.getSimpleOrder(resturant, order);
        StringBuilder message = new StringBuilder();
        Map<String, Object> result = new HashMap<>();
        if (foodOrder.isFlag()) {
            message.append("外卖送至").append(foodOrder.getLocation());
        } else {
            message.append("店内送至桌位：").append(foodOrder.getLocation());
        }
        if (foodOrder.getFlow() == 'c') {
            String reason = foodOrder.getReason();
            if (reason != null) {
                result.put("reason", reason);
            }
        }
        result.put("location", message.toString());
        result.put("people", foodOrder.getPeople());
        result.put("price", foodOrder.getPrice());
        List<Object[]> detail = orderDetailDao.getOrderById(resturant, order);
        List<String> foods = createFood(detail);
        result.put("foods", foods);
        return result;
    }

    /**
     * 生成food 表示
     *
     * @Param [detail]
     * @Return java.util.List<java.lang.String>
     * @Author 孙良玉
     */
    public List<String> createFood(List<Object[]> detail) {
        List<String> result = new ArrayList<>();
        StringBuilder message = new StringBuilder();
        for (Object[] food : detail) {
            message.append(food[0].toString()).append("  共计").append(food[1].toString()).append("份");
            message.append(" 价格").append(food[3]).append("元");
            if (food[2] != null) {
                message.append("  备注").append(food[2].toString());
            }
            result.add(message.toString());
            message.delete(0, message.length());
        }
        return result;
    }

    /**
     * 订单处理
     *
     * @Param [restaurant, order, state]
     * @Return void
     * @Author 孙良玉
     */
    public void alterOrder(Long restaurant, Long order, char state, String reason) {
        if (state == 'b') {
            this.receiveOrder(restaurant, order);
        } else if (state == 'c') {
            this.refuseOrder(restaurant, order, reason);
        }
    }

    /**
     * 接受订单
     *
     * @Param [restaurant, order]
     * @Return void
     * @Author 孙良玉
     */
    public void receiveOrder(Long restaurant, Long order) {
        foodOrderDao.updateFlow(restaurant, order, 'b', new java.sql.Timestamp(System.currentTimeMillis()), null);
        Map<String, Object> result = new HashMap<>();
        Boolean flag = foodOrderDao.getFlag(restaurant, order);
        result.put("flag", flag);
        result.put("restaurant", restaurant);
        result.put("order", order);
        List<OrderDetail> arg = orderDetailDao.findByOrderDetailPkIndent(restaurant, order);
        List<Map<String, Object>> foods = new ArrayList<>();
        for (OrderDetail food : arg) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", food.getOrderDetailPk().getFood());
            map.put("remark", food.getRemark());
            map.put("counts", food.getCounts());
            map.put("name",food.getFoodName());
            foods.add(map);
        }
        result.put("foods", foods);
        kitchenClient.addOrder(result);
    }

    /**
     * 拒绝订单
     *
     * @Param [restaurant, order]
     * @Return void
     * @Author 孙良玉
     */
    public void refuseOrder(Long restaurant, Long order, String reason) {
        foodOrderDao.updateFlow(restaurant, order, 'c', new java.sql.Timestamp(System.currentTimeMillis()), reason);
    }


    /**
     * 获取某天的所有订单数量  以及营业额度
     *
     * @Param [restaurant, date]
     * @Return void
     * @Author 孙良玉
     */
    public Map getOrder(Long restaurant, java.sql.Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String start = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String stop = df.format(calendar.getTime());
        int receive ;
        int refuse ;
        int nodo ;
        int did ;
        receive = foodOrderDao.getOrderCountByAccessTime(restaurant, 'b', start, stop);
        refuse = foodOrderDao.getOrderCountByAccessTime(restaurant, 'c', start, stop);
        nodo = foodOrderDao.getOrderCountByCreateTime(restaurant, 'a', start, stop);
        did = foodOrderDao.getOrderCountByCreateTime(restaurant, 'd', start, stop);
        BigDecimal count = new BigDecimal(0);
        Object object = inputFlowDao.getTodayCount(restaurant,start,stop);
        if(object != null) {
            count = ((BigDecimal)object);
        }
        int all = foodOrderDao.getAllByDate(restaurant, start, stop);
        Map<String, Object> result = new HashMap<>();
        result.put("all", all);
        result.put("nodo", nodo);
        result.put("receive", receive);
        result.put("refuse", refuse);
        result.put("count", count);
        result.put("did", did);
        return result;
    }


    /**
     * 获取订单总数量
     *
     * @Param [restaurant]
     * @Return java.util.Map<java.lang.String , java.lang.Object>
     * @Author 孙良玉
     */
    public Map<String, Object> getCountOrder(Long restaurant) {
        int out = foodOrderDao.getCountOrder(restaurant, true);
        int in = foodOrderDao.getCountOrder(restaurant, false);
        Map<String, Object> map = new HashMap<>();
        map.put("out", out);
        map.put("in", in);
        return map;
    }


    /**
    *获取本某周内的各天订单量
    *@Param [restaurant, date]
    *@Return java.util.Map<java.lang.String,java.lang.Object>
    *@Author 孙良玉
    */
    public List<Integer>  getOldCount(Long restaurant , java.sql.Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        List<Integer> list = new ArrayList<>();
        int w = calendar.get(Calendar.DAY_OF_WEEK)-1 ;
        if(w == 0){
            w = 7;
        }
        calendar.add(Calendar.DATE, 1-w);
        String start = df.format(calendar.getTime());
        String stop ;
        for(int i = 0; i< w ; i++){
            calendar.add(Calendar.DATE, 1);
            stop = df.format(calendar.getTime());
            int all = foodOrderDao.getAllByDate(restaurant, start,stop);
            list.add(all);
            start = stop;
        }
        return list;
    }


    public Map<String,Object> orderPage (Map<String,Object> map) {
        List condition = (ArrayList)map.get("condition");
        List quality = (ArrayList)map.get("quality");
        if (quality.size() == 2) {
            quality.clear();
            quality.add(true);
            quality.add(false);
        } else {
            if(String.valueOf(quality.get(0)).equals("Y")){
                quality.clear();
                quality.add(true);
            }else{
                quality.clear();
                quality.add(false);
            }
        }
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Object st = map.get("start");
        Object page = map.get("page");
        if(st != null ) {
            List list  = (List)st;
            String start =  String.valueOf(list.get(0));
            String stop =  String.valueOf(list.get(1));
            return this.getorderpage(restaurant,start,stop,page,condition,quality);
        }
        return this.getorderpage(restaurant,condition,quality,page);
    }

    public Map<String,Object>  getorderpage (Long restaurant , String start , String stop ,Object page  ,List condation , List quality) {
        Map<String,Object> result = new HashMap<>();
        int count;
        if(page == null) {
            if(condation.contains("d")){
                Object price = foodOrderDao.getinput(restaurant,start,stop,quality);
                if (price != null) {
                    result.put("input",Integer.valueOf(String.valueOf(price)));
                } else {
                    result.put("input",0);
                }
            } else {
                result.put("input",0);
            }
            count = foodOrderDao.findCount(restaurant,start,stop,condation,quality);
            result.put("all",count);
            if (count == 0){
                return  result;
            }
            count = 0;
        } else {
            count = (Integer)page;
        }
        List<Object[]> list = foodOrderDao.findPage(restaurant,start,stop,condation,quality,count  * 10 );
        result.put("page",this.createorder(list));
        return result;
    }

    public Map<String,Object>  getorderpage (Long restaurant  ,List condation , List quality , Object page) {
        Map<String,Object> result = new HashMap<>();
        int count;
        if(page == null) {
            if(condation.contains("d")){
                Object price = foodOrderDao.getinput(restaurant,quality);
                if(price != null) {
                    result.put("input",Integer.valueOf(String.valueOf(price)));
                } else {
                    result.put("input",0);
                }
            } else {
                result.put("input",0);
            }
            count = foodOrderDao.findCount(restaurant,condation,quality);
            result.put("all",count);
            if (count == 0){
                return  result;
            }
            count = 0;
        } else {
            count = (Integer)page;
        }
        List<Object[]> list = foodOrderDao.findPage(restaurant,condation,quality,count  * 10 );
        result.put("page",this.createorder(list));
        return result;
    }


    public List<Map<String,Object>> createorder (List<Object[]> list ) {
        List<Map<String,Object>> result = new LinkedList<>();
        for( Object[] objects : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",Long.valueOf(String.valueOf(objects[0])));
            map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)objects[1]));
            map.put("flag",objects[2]);
            map.put("flow",objects[3]);
            result.add(map);
        }
        return result;
    }


    public  Map<String,Object> inquiryOrder (Map<String,Object> map){
        String type = String.valueOf(map.get("type"));
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        switch (type) {
            case "A":
                return this.orderByid(restaurant,id);
            case "B":
                return this.orderByCl(restaurant,id);
            case "C":
                return this.orderByLo(restaurant,id);
            default:
                return null;
        }
    }

    private  Map<String,Object> orderByid (Long restaurant , Long id) {
        FoodOrder foodOrder = foodOrderDao.getSimpleOrder(restaurant,id);
        if (foodOrder == null ) {
            Map<String,Object> map = new HashMap<>();
            map.put("status","订单不存在");
            return map;
        } else {
            return this.orderByid(foodOrder);
        }
    }
    // 获取客户今天最近的一个订单
    private Map<String,Object> orderByCl (Long restaurant , Long client) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String start = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String stop = df.format(calendar.getTime());
        FoodOrder foodOrder = foodOrderDao.orderByCl(restaurant,client,start,stop);
        if(foodOrder == null ){
            Map<String,Object> map = new HashMap<>();
            map.put("status","当前客户今天没有订单");
            return map;
        }else {
            return this.orderByid(foodOrder);
        }
    }

    // 获取客户今天最近的一个订单
    private Map<String,Object> orderByLo (Long restaurant,Long location) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String start = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String stop = df.format(calendar.getTime());
        FoodOrder foodOrder = foodOrderDao.orderByLo(restaurant,String.valueOf(location),start,stop);
        if(foodOrder == null ){
            Map<String,Object> map = new HashMap<>();
            map.put("status","当前桌号今天没有订单");
            return map;
        }else {
            return this.orderByid(foodOrder);
        }
    }
    private Map<String,Object> orderByid ( FoodOrder foodOrder ) {
        Map<String,Object> map = new HashMap<>();
        map.put("price",foodOrder.getPrice());
        map.put("location",foodOrder.getLocation());
        map.put("client",foodOrder.getClient());
        map.put("id",foodOrder.getIndent());
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(foodOrder.getCreateTime());
        map.put("time",time);
        if(foodOrder.isPayFlag()) {
            if(foodOrder.isPayMethod()) {
                map.put("paym","b");
            } else {
                map.put("paym","c");
            }
        } else {
            map.put("paym","a");
        }
        List<Object[]> detail = orderDetailDao.getOrderById(foodOrder.getRestaurant(), foodOrder.getIndent());
        List foods = covertMap(detail);
        map.put("food",foods);
        return map;
    }

    private List<Map<String,Object>>  covertMap (List<Object[]> list) {
        List<Map<String,Object>> result = new ArrayList<>();
        for(Object[] objects: list) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",objects[0]);
            map.put("count",objects[1]);
            map.put("price",objects[3]);
            map.put("de",objects[2]);
            result.add(map);
        }
        return result;
    }


    // 支付
    public void pay (Map<String,Object> map) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        boolean method =Boolean.valueOf(String.valueOf(map.get("pay")));
        int  price =  Integer.valueOf(String.valueOf(map.get("price")));
        foodOrderDao.pay(restaurant,id,method);
        InputFlow inputFlow = new InputFlow();
        inputFlow.setCreateTime(new Timestamp(System.currentTimeMillis()));
        inputFlow.setFlag(method);
        inputFlow.setIndent(id);
        inputFlow.setRestaurant(restaurant);
        inputFlow.setInput(price);
        inputFlowDao.save(inputFlow);
    }


    //  客户订单查询 和 上面的餐厅订单查询有很大代码冗余 后期注意修改
    public Map<String,Object> cifQuiryOrder (Map<String,Object> map ) {
        List condition = (ArrayList)map.get("condition");
        List quality = (ArrayList)map.get("quality");
        Long userid = Long.valueOf(String.valueOf(map.get("user")));
        if (quality.size() == 2) {
            quality.clear();
            quality.add(true);
            quality.add(false);
        } else {
            if(String.valueOf(quality.get(0)).equals("Y")){
                quality.clear();
                quality.add(true);
            }else{
                quality.clear();
                quality.add(false);
            }
        }
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Object st = map.get("start");
        Object page = map.get("page");
        if(st != null ) {
            List list  = (List)st;
            String start =  String.valueOf(list.get(0));
            String stop =  String.valueOf(list.get(1));
            return this.getorderpage(restaurant,start,stop,page,condition,quality,userid);
        }
        return this.getorderpage(restaurant,condition,quality,page,userid);
    }



    public Map<String,Object>  getorderpage (Long restaurant , String start , String stop ,Object page  ,List condation , List quality , Long userid) {
        Map<String,Object> result = new HashMap<>();
        int count;
        if(page == null) {
            if(condation.contains("d")){
                Object price = foodOrderDao.getinput(restaurant,start,stop,quality,userid);
                if (price != null) {
                    result.put("out",Integer.valueOf(String.valueOf(price)));
                } else {
                    result.put("out",0);
                }
            } else {
                result.put("out",0);
            }
            count = foodOrderDao.findCount(restaurant,start,stop,condation,quality,userid);
            result.put("all",count);
            if (count == 0){
                return  result;
            }
            count = 0;
        } else {
            count = (Integer)page;
        }
        List<Object[]> list = foodOrderDao.findPage(restaurant,start,stop,condation,quality,count  * 10 ,userid);
        result.put("page",this.createorder(list));
        return result;
    }

    public Map<String,Object>  getorderpage (Long restaurant  ,List condation , List quality , Object page ,Long userid) {
        Map<String,Object> result = new HashMap<>();
        int count;
        if(page == null) {
            if(condation.contains("d")){
                Object price = foodOrderDao.getinput(restaurant,quality,userid);
                if(price != null) {
                    result.put("out",Integer.valueOf(String.valueOf(price)));
                } else {
                    result.put("out",0);
                }
            } else {
                result.put("out",0);
            }
            count = foodOrderDao.findCount(restaurant,condation,quality,userid);
            result.put("all",count);
            if (count == 0){
                return  result;
            }
            count = 0;
        } else {
            count = (Integer)page;
        }
        List<Object[]> list = foodOrderDao.findPage(restaurant,condation,quality,count  * 10 ,userid);
        result.put("page",this.createorder(list));
        return result;
    }



    public List<Map> todatyOrder (Map<String,Object> map ) {
        Long restaurant = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long cif = Long.valueOf(String.valueOf(map.get("cif")));
        char type = String.valueOf(map.get("type")).charAt(0);
        List<Object[]> list ;
        if (type == 'a') {
            list = foodOrderDao.getOrderByCif(restaurant,cif,'a');
        } else if (type == 'b') {
            list = foodOrderDao.getOrderByCif(restaurant,cif,'b');
        } else if (type == 'c') {
            String today = this.getToday();
            list = foodOrderDao.getOrderByCif(restaurant,cif,'c',today);
        } else {
            String today = this.getToday();
            list = foodOrderDao.getOrderByCif(restaurant,cif,'d',today);
        }
        return this.createR(list);
    }


    private String getToday () {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String start = df.format(calendar.getTime());
        return start;
    }

    private List<Map>   createR (List<Object[]> list) {
        List<Map> result = new LinkedList<>();
        for (Object[] objects: list) {
            Map<String, Object> map  =  new HashMap<>();
            Long id = Long.valueOf(String.valueOf(objects[0]));
            map.put("id",id);
            map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Timestamp)objects[1]));
            result.add(map);
        }
        return result;
    }


    public Map<String,Object> orderCount (Map<String,Object> map ) {
        Long restaurant  = Long.valueOf(String.valueOf(map.get("restaurant")));
        Long cif = Long.valueOf(String.valueOf(map.get("cif")));
        Map<String,Object> result = new HashMap<>();
        int arg = foodOrderDao.getOrderCount(restaurant,cif,'a');
        result.put("a",arg);
        arg  = foodOrderDao.getOrderCount(restaurant,cif,'b');
        result.put("b",arg);
        String today = this.getToday();
        arg = foodOrderDao.getOrderCount(restaurant,cif,'c',today);
        result.put("c",arg);
        arg = foodOrderDao.getOrderCount(restaurant,cif,'d',today);
        result.put("d",arg);
        return result;
    }


    public void cancelOrder (Map<String,Object> map) {
        Long restaurant =Long.valueOf(String.valueOf(map.get("restaurant")));
        Long cif = Long.valueOf(String.valueOf(map.get("cif")));
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        String reason = String.valueOf(map.get("reason"));
        foodOrderDao.updateFlow(restaurant,id,'e',new Timestamp(System.currentTimeMillis()),reason,cif);
    }


    //  代码太粗糙 后期优化一下
    public Map purchase (Map<String,Object> arg) {
        FoodOrder order = new FoodOrder();
        Long restaurant =Long.valueOf(String.valueOf(arg.get("restaurant")));
        Long cif = Long.valueOf(String.valueOf(arg.get("cif")));
        List foods = (ArrayList)arg.get("food");
        int price = Integer.valueOf(String.valueOf(arg.get("price")));
        order.setPrice(price);
        price = Integer.valueOf(String.valueOf(arg.get("count")));
        order.setCountFoods(price);
        order.setFinish(price);
        price = Integer.valueOf(String.valueOf(arg.get("people")));
        order.setPeople(price);
        char type = String.valueOf(arg.get("flag")).charAt(0);
        if (type == 'A') {
            order.setFlag(true);
        } else {
            order.setFlag(false);
        }
        Object object = arg.get("remark");
        String remark = null;
        if( object != null) {
            remark = String.valueOf(object);
        }
        order.setRemark(remark);
        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis());
        builder.append(cif);
        Long id =  Long.valueOf(builder.toString());
        order.setClient(cif);
        order.setRestaurant(restaurant);
        order.setLocation(String.valueOf(arg.get("address")));
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setFlow('a');
        order.setIndent(id);
        foodOrderDao.save(order);
        OrderDetailPk pk = new OrderDetailPk();
        pk.setIndent(id);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setClient(cif);
        orderDetail.setRemark(remark);
        orderDetail.setRestaurant(restaurant);
        orderDetail.setOrderDetailPk(pk);
        for(Object f: foods) {
            Map map = (HashMap)f;
            Long food = Long.valueOf(String.valueOf(map.get("food")));
            String name = String.valueOf(map.get("name"));
            int num = Integer.valueOf(String.valueOf(map.get("num")));
            orderDetail.setCounts(num);
            num = Integer.valueOf(String.valueOf(map.get("price")));
            orderDetail.setPrice(num);
            pk.setFood(food);
            orderDetail.setFoodName(name);
            orderDetailDao.save(orderDetail);
        }

        Map<String,Object> result = new HashMap<>();
        result.put("id",id);
        result.put("state",true);
        return result;
    }

    public void finish (Map map) {
        Long restaurant =Long.valueOf(String.valueOf(map.get("restaurant")));
        Long id = Long.valueOf(String.valueOf(map.get("id")));
        int finish = foodOrderDao.getFinished(restaurant,id);
        Long food = Long.valueOf(String.valueOf(map.get("food")));
        int count = orderDetailDao.findCounts(restaurant,id,food);
        if(count == finish) {
            foodOrderDao.updateFinish(restaurant,id,'d');
        } else {
            foodOrderDao.updateFinish(restaurant,id,count);
        }
    }
}



