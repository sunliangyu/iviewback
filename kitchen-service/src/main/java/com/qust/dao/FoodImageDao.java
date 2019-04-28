package com.qust.dao;

import com.qust.entity.FoodImage;
import com.qust.entity.FoodImagePk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodImageDao extends JpaRepository<FoodImage, FoodImagePk> {
}
