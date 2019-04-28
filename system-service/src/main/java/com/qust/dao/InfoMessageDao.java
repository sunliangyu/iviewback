package com.qust.dao;

import com.qust.entity.InfoMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface InfoMessageDao extends JpaRepository<InfoMessage,Long> {

    List<InfoMessage> findByState(char state );

    @Query(value = "SELECT count(*) from info_message where restaurant = ?1 and  state = ?2 ", nativeQuery = true)
    int getCount (Long pkid , char state);

    @Query(value = "SELECT state,id,create_time,title from info_message where restaurant = ?1 ", nativeQuery = true)
    List<Object[]> findMessages(Long restaurant );

    @Query(value = "SELECT message from info_message where restaurant = ?1  and  id = ?2", nativeQuery = true)
    String findMessageBuId (Long restaurant,Long id);
}
