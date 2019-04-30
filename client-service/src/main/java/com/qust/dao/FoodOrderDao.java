package com.qust.dao;

import com.qust.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

public interface FoodOrderDao extends JpaRepository<FoodOrder, Long> {

    @Query(value = "SELECT count(*) from food_order where restaurant = ?1 and  flow = ?2 ", nativeQuery = true)
    int getCount (Long pkid ,char state);

    @Query(value = "SELECT indent,create_time,flag from food_order where restaurant = ?1 and  flow = 'a'", nativeQuery = true)
    List<Object[]> findUnread(Long restaurant );

    @Query(value = "SELECT indent,create_time,flag from food_order where restaurant = ?1 and  flow = ?2   and  access_time between ?3 and ?4", nativeQuery = true)
    List<Object[]>findToday(Long restaurant , char flow , String today , String tommorow);

    @Query(value = "SELECT * from food_order where restaurant = ?1 and indent = ?2", nativeQuery = true)
    FoodOrder getSimpleOrder ( Long restaurant , Long order);

    @Transactional
    @Modifying
    @Query(value = "update  food_order set  flow = ?3 , access_time = ?4 , reason = ?5 where restaurant = ?1 and indent = ?2", nativeQuery = true)
    void updateFlow (Long restaurant , Long order , char flow , Date date,String reason);


    @Query(value = "SELECT  flag from food_order where restaurant = ?1 and indent = ?2", nativeQuery = true)
    Boolean getFlag (Long restaurant , Long order);



}
