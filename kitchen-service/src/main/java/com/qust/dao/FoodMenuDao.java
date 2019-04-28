package com.qust.dao;

import com.qust.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodMenuDao extends JpaRepository<FoodMenu,Long> {
}
