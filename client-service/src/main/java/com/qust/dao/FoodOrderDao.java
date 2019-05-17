package com.qust.dao;

import com.qust.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface FoodOrderDao extends JpaRepository<FoodOrder, Long> {

    @Query(value = "SELECT count(*) from food_order where restaurant = ?1 and  flow = ?2 ", nativeQuery = true)
    int getCount (Long pkid ,char state);

    @Query(value = "SELECT indent,create_time,flag from food_order where restaurant = ?1 and  flow = 'a' order BY create_time desc ", nativeQuery = true)
    List<Object[]> findUnread(Long restaurant );

    @Query(value = "SELECT indent,create_time,flag from food_order where restaurant = ?1 and  flow = ?2   and  access_time >= ?3 and access_time < ?4  order BY access_time desc ", nativeQuery = true)
    List<Object[]>findToday(Long restaurant , char flow , String start ,String end);

    @Query(value = "SELECT * from food_order where restaurant = ?1 and indent = ?2", nativeQuery = true)
    FoodOrder getSimpleOrder ( Long restaurant , Long order);

    @Query(value = "SELECT * from food_order where restaurant = ?1 and  client = ?2 and create_time >= ?3 and create_time <= ?4 order BY create_time desc limit 0, 1", nativeQuery = true)
    FoodOrder orderByCl (Long restaurant , Long client ,String start, String stop);

    @Query(value = "SELECT * from food_order where restaurant = ?1 and  flag = false  and location = ?2 and create_time >= ?3 and create_time <= ?4 order BY create_time desc limit 0, 1", nativeQuery = true)
    FoodOrder orderByLo(Long restaurant,String location , String start, String stop);

    @Transactional
    @Modifying
    @Query(value = "update  food_order set  flow = ?3 , access_time = ?4 , reason = ?5 where restaurant = ?1 and indent = ?2", nativeQuery = true)
    void updateFlow (Long restaurant , Long order , char flow , Timestamp date, String reason);


    @Transactional
    @Modifying
    @Query(value = "update  food_order set pay_flag = 1 ,pay_method = ?3 where restaurant = ?1 and indent = ?2", nativeQuery = true)
    void pay (Long restaurant , Long order , boolean pay);

    @Query(value = "SELECT  flag from food_order where restaurant = ?1 and indent = ?2", nativeQuery = true)
    Boolean getFlag (Long restaurant , Long order);

    @Query(value = "SELECT  count(*) from food_order where restaurant = ?1 and flow = ?2  and  create_time  >= ?3  and create_time <?4   ", nativeQuery = true)
    int getOrderCountByCreateTime(Long restaurant ,  char flow  , String start ,String end );

    @Query(value = "SELECT  count(*) from food_order where restaurant = ?1 and flow = ?2  and  access_time   >= ?3  and access_time < ?4   ", nativeQuery = true)
    int getOrderCountByAccessTime(Long restaurant ,  char flow  , String start  ,String end );

    @Query(value = "SELECT  count(*) from food_order where restaurant = ?1 AND  create_time  >= ?2 and create_time <?3     ", nativeQuery = true)
    int getAllByDate (Long restaurant , String start ,String end);

    @Query(value = "SELECT  sum(price) from food_order where restaurant = ?1 AND  flow = 'b' AND  create_time  >= ?2 and create_time <?3    ", nativeQuery = true)
    int getPriceCount(Long restaurant , String start ,String end);

    @Query(value = "SELECT  count(*) from food_order where restaurant = ?1 AND  flag = ?2  ", nativeQuery = true)
    int getCountOrder (Long restaurant , Boolean flag);

    @Query(value = "SELECT count(*) from food_order where restaurant = ?1 and  create_time >= ?2  and create_time <= ?3  and flow in ?4  and flag in ?5 order BY create_time desc ", nativeQuery = true)
    int findCount(Long restaurant, String start , String stop , List condition , List quality);

    @Query(value = "SELECT count(*) from food_order where restaurant = ?1   and flow in ?2  and flag in ?3 order BY create_time desc ", nativeQuery = true)
    int findCount(Long restaurant, List condition , List quality);


    @Query(value = "SELECT count(*) from food_order where restaurant = ?1 and  create_time >= ?2  and create_time <= ?3  and flow in ?4  and flag in ?5 and client = ?6 order BY create_time desc ", nativeQuery = true)
    int findCount(Long restaurant, String start , String stop , List condition , List quality ,Long userid);

    @Query(value = "SELECT count(*) from food_order where restaurant = ?1   and flow in ?2  and flag in ?3  and  client = ?4 order BY create_time desc ", nativeQuery = true)
    int findCount(Long restaurant, List condition , List quality,Long userid);


    @Query(value = "SELECT indent,create_time,flag,flow from food_order where restaurant = ?1 and  create_time >= ?2  and create_time <= ?3  and flow in ?4  and flag in ?5 order BY create_time desc  limit ?6, 10", nativeQuery = true)
    List<Object[]> findPage(Long restaurant, String start , String stop , List condition , List quality , int a );

    @Query(value = "SELECT indent,create_time,flag,flow from food_order where restaurant = ?1   and flow in ?2  and flag in ?3 order BY create_time desc limit ?4, 10 ", nativeQuery = true)
    List<Object[]> findPage(Long restaurant, List condition , List quality ,int  a );


    @Query(value = "SELECT indent,create_time,flag,flow from food_order where restaurant = ?1 and  create_time >= ?2  and create_time <= ?3  and flow in ?4  and flag in ?5 and client = ?7 order BY create_time desc  limit ?6, 10", nativeQuery = true)
    List<Object[]> findPage(Long restaurant, String start , String stop , List condition , List quality , int a ,Long userid);

    @Query(value = "SELECT indent,create_time,flag,flow from food_order where restaurant = ?1   and flow in ?2  and flag in ?3 and client = ?5 order BY create_time desc limit ?4, 10 ", nativeQuery = true)
    List<Object[]> findPage(Long restaurant, List condition , List quality ,int  a ,Long userid);

    @Query(value = "SELECT sum(price) from food_order where restaurant = ?1   and flow = 'd' and  create_time >= ?2  and create_time <= ?3  and flag in ?4  ", nativeQuery = true)
    Object getinput (Long restaurant, String start, String stop , List quality );

    @Query(value = "SELECT sum(price)  from food_order where restaurant = ?1   and flow = 'd'  and flag in ?2 ", nativeQuery = true)
    Object getinput (Long restaurant, List quality );

    @Query(value = "SELECT sum(price) from food_order where restaurant = ?1   and flow = 'd' and  create_time >= ?2  and create_time <= ?3  and flag in ?4   and  client = ?5", nativeQuery = true)
    Object getinput (Long restaurant, String start, String stop , List quality ,Long userid);

    @Query(value = "SELECT sum(price)  from food_order where restaurant = ?1   and flow = 'd'  and flag in ?2  and  client = ?3", nativeQuery = true)
    Object getinput (Long restaurant, List quality , Long userid);



    @Query(value = "SELECT  indent,create_time from food_order where restaurant = ?1   and flow = ?3   and  client = ?2", nativeQuery = true)
    List<Object[]> getOrderByCif (Long restaurant, Long cif , char flow );


    @Query(value = "SELECT  indent,create_time from food_order where restaurant = ?1   and flow = ?3   and  client = ?2 and create_time >= ?4 ", nativeQuery = true)
    List<Object[]> getOrderByCif (Long restaurant, Long cif , char flow ,String start  );


    @Query(value = "SELECT  count(*) from food_order where restaurant = ?1   and flow = ?3   and  client = ?2", nativeQuery = true)
    int getOrderCount (Long restaurant,Long cif, char flow);

    @Query(value = "SELECT  count(*) from food_order where restaurant = ?1   and flow = ?3   and  client = ?2 and create_time >= ?4", nativeQuery = true)
    int getOrderCount (Long restaurant,Long cif, char flow,String today);

    @Transactional
    @Modifying
    @Query(value = "update  food_order set  flow = ?3 , access_time = ?4 , reason = ?5 where restaurant = ?1 and indent = ?2 and client = ?6", nativeQuery = true)
    void updateFlow (Long restaurant , Long order , char flow , Timestamp date, String reason ,Long cif);

    @Query(value = "SELECT  finish from food_order where restaurant = ?1  and  indent= ?2", nativeQuery = true)
    int getFinished (Long restaurant , Long order);

    @Transactional
    @Modifying
    @Query(value = "update  food_order set finish = finish - ?3 where restaurant = ?1 and indent = ?2 ", nativeQuery = true)
    void updateFinish (Long restaurant , Long order, int count );

    @Transactional
    @Modifying
    @Query(value = "update  food_order set finish = 0,flow = ?3 where restaurant = ?1 and indent = ?2 ", nativeQuery = true)
    void updateFinish (Long restaurant , Long order, char flow );

}
