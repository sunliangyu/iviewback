package com.qust.dao;

import com.qust.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientDao extends JpaRepository<Client,Long> {
    @Query(value = "SELECT  password,id  from client where username = ?1 ", nativeQuery = true)
    List<Object[]> getClient (String name) ;

    @Query(value = "SELECT  *  from client where id = ?1 ", nativeQuery = true)
    Client findById  (Long id);

    @Query(value = "SELECT  count(*)  from client where username = ?1 ", nativeQuery = true)
    int  findByUsername (String name);
}
