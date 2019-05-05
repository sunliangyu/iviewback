package com.qust.dao;

import com.qust.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FoodMenuDao extends JpaRepository<FoodMenu,Long> {

    @Query(value = "SELECT id,name  from food_menu where restaurant = ?1  and  foodclass = ?2", nativeQuery = true)
    List<Object[]>  getFoodByClass ( Long restaurant, Long type);

    @Transactional
    @Modifying
    @Query(value = "delete from food_menu where restaurant = ?2 and id = ?1",nativeQuery = true)
    void deleteById(Long id , Long restaurant);

    @Query(value = "SELECT *   from food_menu where restaurant = ?2  and  id = ?1", nativeQuery = true)
    FoodMenu getByID (Long id , Long restaurant);

    @Transactional
    @Modifying
    @Query(value = "update  food_menu set  name= ?3 , description= ?4 ,price = ?5 ,time_cost = ?6  where restaurant = ?1 and id = ?2",nativeQuery = true)
    void updateFood(Long restaurant , Long id , String name, String desc,int price , int cost);

}
