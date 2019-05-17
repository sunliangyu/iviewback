package com.qust.dao;

import com.qust.entity.CodeOrder;
import com.qust.entity.CodeOrderPk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface CodeOrderDao extends JpaRepository<CodeOrder, CodeOrderPk> {
    @Query(value = "SELECT count(*) from code_order where restaurant = ?1  and  state = ?2 ", nativeQuery = true)
    int getCountR (Long restaurant , String state);

    @Query(value = "SELECT sum(count) from code_order where restaurant = ?1  and  state = ?2 ", nativeQuery = true)
    int getSumR (Long restaurant ,String state );

    @Query(value = "SELECT count(*) from code_order where restaurant = ?1  and  state = ?3 and food = ?2 ", nativeQuery = true)
    int getCountR (Long restaurant,Long id,String state);

    @Query(value = "SELECT sum(count) from code_order where restaurant = ?1  and  state = ?3 and food = ?2 ", nativeQuery = true)
    int getSumR (Long restaurant,Long id,String state);

    @Query(value = "SELECT food,count,create_time,flag,remark,name,indent from code_order  where restaurant = ?1  and  state = ?4 and food = ?2  order BY create_time desc  limit ?3, 10 ", nativeQuery = true)
    List<Object[]> getPage (Long restaurant, Long id, int page,String state);

    @Query(value = "SELECT food,count,create_time,flag,remark,name,indent  from code_order  where restaurant = ?1  and  state = ?3  order BY create_time desc  limit ?2, 10 ", nativeQuery = true)
    List<Object[]> getPage (Long restaurant,int page,String state);

    @Transactional
    @Modifying
    @Query(value = "update code_order set state=?4 , update_time = ?5 where restaurant = ?2 and indent = ?3 and food=?1",nativeQuery = true)
    void updatestate(Long id , Long restaurant , Long order , char state, Timestamp time);


    @Query(value = "SELECT count,state,name  from code_order  where restaurant = ?1  and indent =?2 ", nativeQuery = true)
    List<Object[]> getProgress (Long restaurant,Long id);
}
