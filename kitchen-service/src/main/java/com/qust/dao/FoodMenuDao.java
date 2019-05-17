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

    @Query(value = "SELECT id,name  from food_menu where restaurant = ?1  ", nativeQuery = true)
    List<Object[]>  getFoos ( Long restaurant);

    @Query(value = "SELECT id,name,price,description,sell_number  from food_menu where restaurant = ?1 order BY create_time desc limit ?2, 10 ", nativeQuery = true)
    List<Object[]>  getFoosByC ( Long restaurant, int page);

    @Query(value = "SELECT  count(*) from food_menu where restaurant = ?1  ", nativeQuery = true)
    int  getFoosCou ( Long restaurant);

    @Query(value = "SELECT id,name,price,description,sell_number  from food_menu where restaurant = ?1 and name like  ?2 order BY create_time desc limit ?3, 10 ", nativeQuery = true)
    List<Object[]>  getFoosByC ( Long restaurant,String like,int page);

    @Query(value = "SELECT count(*) from food_menu where restaurant = ?1 and name like  ?2 ", nativeQuery = true)
    int  getFoosCou ( Long restaurant,String like);

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

    @Transactional
    @Modifying
    @Query(value = "update  food_menu set  sell_number = sell_number + 1  where restaurant = ?1 and id = ?2",nativeQuery = true)
    void updateFoodOut(Long restaurant , Long id );



}
