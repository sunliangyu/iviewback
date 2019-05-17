package com.qust.dao;

import com.qust.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

public interface RawMaterialDao extends JpaRepository<RawMaterial,Long> {

    @Query(value = "SELECT id,mname from raw_material where restaurant = ?1 and flag = ?2  ", nativeQuery = true)
    List<Object[]> getSimpleBytype ( Long restaurant, char flag);

    @Query(value = "SELECT * from raw_material where restaurant = ?1 and id = ?2  ", nativeQuery = true)
    RawMaterial getById (Long restaurant, Long id);


    @Transactional
    @Modifying
    @Query(value = "update raw_material set count = ?3 ,mname = ?4 ,save_time = ?5 where restaurant = ?1 and id = ?2  ", nativeQuery = true)
    void alterById (Long restaurant, Long id, int count , String name, int save);

    @Query(value = "SELECT count(*) from raw_material where restaurant = ?1 and mname = ?2  ", nativeQuery = true)
    int checkName (Long restaurant, String name);

    @Transactional
    @Modifying
    @Query(value = "delete from raw_material where restaurant = ?2 and id = ?1",nativeQuery = true)
    void deleteById(Long id , Long restaurant);

    @Transactional
    @Modifying
    @Query(value = "update raw_material  set count = count + ?3 , update_time = ?4  where restaurant = ?2 and id = ?1",nativeQuery = true)
    void add(Long id , Long restaurant, int count , Timestamp time );


    @Transactional
    @Modifying
    @Query(value = "update raw_material  set count = count + ?3   where restaurant = ?2 and id = ?1",nativeQuery = true)
    void delete(Long id , Long restaurant, int count );

}
