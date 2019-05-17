package com.qust.dao;

import com.qust.entity.FoodImage;
import com.qust.entity.FoodImagePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodImageDao extends JpaRepository<FoodImage, FoodImagePk> {

    @Query(value = "SELECT  url  from food_image where restaurant = ?1 and food = ?2", nativeQuery = true)
    List<Object> findUrl (Long restaurant,Long id);

}
