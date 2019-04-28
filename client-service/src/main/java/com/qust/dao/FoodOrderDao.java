package com.qust.dao;

import com.qust.entity.FoodOrder;
import org.hibernate.mapping.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodOrderDao extends JpaRepository<FoodOrder, Long> {

    @Query(value = "SELECT count(*) from food_order where restaurant = ?1 and  flow = ?2 ", nativeQuery = true)
    int getCount (Long pkid ,char state);

    @Query(value = "SELECT indent,create_time,flag from food_order where restaurant = ?1 and  flow = 'a'", nativeQuery = true)
    List<Object[]> findUnread(Long restaurant );

    @Query(value = "SELECT indent,create_time,flag from food_order where restaurant = ?1 and  flow = ?2   and  create_time between ?3 and ?4", nativeQuery = true)
    List<Object[]>findToday(Long restaurant , char flow , String today , String tommorow);

}
