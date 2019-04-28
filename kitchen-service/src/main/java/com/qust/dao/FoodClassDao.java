package com.qust.dao;

import com.qust.entity.FoodClass;
import com.qust.entity.FoodClassPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodClassDao extends JpaRepository<FoodClass, FoodClassPk> {
    List<FoodClass> findByIdRestaurant (Long restaurant);
}
