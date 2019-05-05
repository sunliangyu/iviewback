package com.qust.dao;

import com.qust.entity.FoodClass;
import com.qust.entity.FoodClassPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodClassDao extends JpaRepository<FoodClass, FoodClassPk> {

    @Query(value = "SELECT foodclass,description from food_class where restaurant = ?1 ", nativeQuery = true)
    List<Object[]> findByRestaurant (Long restaurant);

}
