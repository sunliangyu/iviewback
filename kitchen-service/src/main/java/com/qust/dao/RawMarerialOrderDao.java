package com.qust.dao;

import com.qust.entity.RawMarerialOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawMarerialOrderDao extends JpaRepository<RawMarerialOrder,Long> {

    @Query(value = "SELECT name,count,output,create_time from raw_marerial_order where restaurant = ?1 order BY create_time desc  limit ?2, 10 ", nativeQuery = true)
    List<Object[]> getOrder (Long restaurant, int page);

    @Query(value = "SELECT name,count,output,create_time from raw_marerial_order where restaurant = ?1 and create_time >= ?2 and create_time <= ?3  order BY create_time desc  limit ?4, 10 ", nativeQuery = true)
    List<Object[]> getOrder (Long restaurant , String start , String stop, int page);

    @Query(value = "SELECT name,count,output,create_time from raw_marerial_order where restaurant = ?1  and  material = ?2  order BY create_time desc  limit ?3, 10", nativeQuery = true)
    List<Object[]> getOrder (Long restaurant ,Long id,int page);

    @Query(value = "SELECT name,count,output,create_time from raw_marerial_order where restaurant = ?1 and create_time >= ?2 and create_time <= ?3  and  material = ?4  order BY create_time desc  limit ?5, 10", nativeQuery = true)
    List<Object[]> getOrder (Long restaurant , String start , String stop,Long id, int page);

    @Query(value = "SELECT count(*) from raw_marerial_order where restaurant = ?1 and create_time >= ?2 and create_time <= ?3  ", nativeQuery = true)
    int getOrdeCount (Long restaurant , String start , String stop);

    @Query(value = "SELECT count(*) from raw_marerial_order where restaurant = ?1  ", nativeQuery = true)
    int  getOrdeCount (Long restaurant);


    @Query(value = "SELECT count(*) from raw_marerial_order where restaurant = ?1 and create_time >= ?2 and create_time <= ?3  and  material = ?4 ", nativeQuery = true)
    int getOrdeCount (Long restaurant , String start , String stop ,Long id);

    @Query(value = "SELECT count(*) from raw_marerial_order where restaurant = ?1  and material = ?2", nativeQuery = true)
    int  getOrdeCount (Long restaurant,Long id);

    @Query(value = "SELECT sum(output) from raw_marerial_order where restaurant = ?1 and create_time >= ?2 and create_time <= ?3  ", nativeQuery = true)
    int getOrdeSum (Long restaurant , String start , String stop);

    @Query(value = "SELECT sum(output)  from raw_marerial_order where restaurant = ?1  ", nativeQuery = true)
    int  getOrdeSum (Long restaurant);


    @Query(value = "SELECT sum(output) from raw_marerial_order where restaurant = ?1 and create_time >= ?2 and create_time <= ?3  and  material = ?4 ", nativeQuery = true)
    int getOrdeSum (Long restaurant , String start , String stop ,Long id);

    @Query(value = "SELECT sum(output)  from raw_marerial_order where restaurant = ?1  and material = ?2", nativeQuery = true)
    int  getOrdeSum (Long restaurant,Long id);


}
