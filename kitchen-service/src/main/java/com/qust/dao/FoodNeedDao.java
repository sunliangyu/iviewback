package com.qust.dao;

import com.qust.entity.FoodNeed;
import com.qust.entity.FoodNeedPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FoodNeedDao extends JpaRepository<FoodNeed, FoodNeedPk> {

     List<FoodNeed> findByIdFood(Long food);

    @Transactional
    @Modifying
    @Query(value = "delete from food_need where restaurant = ?2 and food= ?1",nativeQuery = true)
    void deleteById(Long id , Long restaurant);

    @Query(value = "select material, count, nname  from food_need where restaurant = ?2 and food= ?1",nativeQuery = true)
    List<Object[]> getNeed(Long id , Long restaurant);

    @Transactional
    @Modifying
    @Query(value = "delete from food_need where restaurant = ?2 and food= ?1 and  material = ?3",nativeQuery = true)
    void deleteByIdFood(Long id , Long restaurant , Long need);

    @Query(value = "select  nname,count   from food_need where restaurant = ?2 and food= ?1",nativeQuery = true)
    List<Object[]> getNeedS(Long id , Long restaurant);

}
